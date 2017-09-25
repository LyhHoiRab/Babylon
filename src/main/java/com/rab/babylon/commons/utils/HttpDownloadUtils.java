package com.rab.babylon.commons.utils;

import com.rab.babylon.commons.security.exception.ResourceNotFoundException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HttpDownloadUtils{

    //缓冲区大小 - 20kb
    private final static int DEFAULT_BUFFER_SIZE = 20480;
    //有效时间 - 1 week
    private final static long DEFAULT_EXPIRE_TIME = 604800000L;
    private static final String MULTIPART_BOUNDARY = "MULTIPART_BYTERANGES";
    //请求对象
    private HttpServletRequest request;
    //响应对象
    private HttpServletResponse response;
    //下载资源路径
    private String path;

    public HttpDownloadUtils(HttpServletRequest request, HttpServletResponse response, String path){
        this.request = request;
        this.response = response;
        this.path = path;
    }

    public void download() throws Exception{
        File file = new File(path);
        if(file == null || !file.exists()){
            throw new ResourceNotFoundException("资源不存在");
        }

        //请求信息
        String contentType = request.getContentType();
        //设置响应信息
        String fileName = file.getName();
        long length = file.length();
        long lastModified = file.lastModified();
        String eTag = fileName + "_" + length + "_" + lastModified;
        long expires = System.currentTimeMillis() + DEFAULT_EXPIRE_TIME;

        //根据请求判断
        String ifNoneMatch = request.getHeader("If-None-Match");
        if(ifNoneMatch != null && matches(ifNoneMatch, fileName)){
            response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
            response.setHeader("ETag", eTag);
            response.setDateHeader("Expires", expires);
            return;
        }

        long ifModifiedSince = request.getDateHeader("If-Modified-Since");
        if(ifNoneMatch == null && ifModifiedSince != -1 && ifModifiedSince + 1000 > lastModified){
            response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
            response.setHeader("ETag", eTag);
            response.setDateHeader("Expires", expires);
            return;
        }

        String ifMatch = request.getHeader("If-Match");
        if(ifMatch != null && !matches(ifMatch, fileName)){
            response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED);
            return;
        }

        long ifUnmodifiedSince = request.getDateHeader("If-Unmodified-Since");
        if(ifUnmodifiedSince != -1 && ifUnmodifiedSince + 1000 <= lastModified){
            response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED);
            return;
        }

        Range full = new Range(0, length - 1, length);
        List<Range> ranges = new ArrayList<Range>();

        String range = request.getHeader("Range");
        if(range != null){
            if(!range.matches("^bytes=\\d*-\\d*(,\\d*-\\d*)*$")){
                response.setHeader("Content-Range", "bytes */" + length);
                response.sendError(HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE);
                return;
            }

            String ifRange = request.getHeader("If-Range");
            if(ifRange != null && !ifRange.equals(eTag)){
                try{
                    long ifRangeTime = request.getDateHeader("If-Range");

                    if(ifRangeTime != -1 && ifRangeTime + 1000 < lastModified){
                        ranges.add(full);
                    }
                }catch(IllegalArgumentException ignore){
                    ranges.add(full);
                }
            }

            if(ranges.isEmpty()){
                for(String part: range.substring(6).split(",")){
                    long start = sublong(part, 0, part.indexOf("-"));
                    long end = sublong(part, part.indexOf("-") + 1, part.length());

                    if(start == -1){
                        start = length - end;
                        end = length - 1;
                    }else if(end == -1 || end > length - 1){
                        end = length - 1;
                    }

                    if(start > end){
                        response.setHeader("Content-Range", "bytes */" + length);
                        response.sendError(HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE);
                        return;
                    }

                    ranges.add(new Range(start, end, length));
                }
            }
        }

        String disposition = "inline";
        if(contentType == null){
            contentType = "application/octet-stream";
        }else if(!contentType.startsWith("image")){
            String accept = request.getHeader("Accept");
            disposition = accept != null && accepts(accept, contentType) ? "inline" : "attachment";
        }

        response.reset();
        response.setBufferSize(DEFAULT_BUFFER_SIZE);
        response.setHeader("Content-Disposition", disposition + ";filename=\"" + fileName + "\"");
        response.setHeader("Accept-Ranges", "bytes");
        response.setHeader("ETag", eTag);
        response.setDateHeader("Last-Modified", lastModified);
        response.setDateHeader("Expires", expires);

        RandomAccessFile input = new RandomAccessFile(file, "r");
        OutputStream output = response.getOutputStream();

        if(ranges.isEmpty() || ranges.get(0) == full){
            Range r = full;
            response.setContentType(contentType);
            response.setHeader("Content-Range", "bytes " + r.start + "-" + r.end + "/" + r.total);
            response.setHeader("Content-Length", String.valueOf(r.length));

            copy(input, output, r.start, r.length);

        }else if(ranges.size() == 1){
            Range r = ranges.get(0);
            response.setContentType(contentType);
            response.setHeader("Content-Range", "bytes " + r.start + "-" + r.end + "/" + r.total);
            response.setHeader("Content-Length", String.valueOf(r.length));
            response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);

            copy(input, output, r.start, r.length);

        }else{
            response.setContentType("multipart/byteranges; boundary=" + MULTIPART_BOUNDARY);
            response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);

            ServletOutputStream sos = (ServletOutputStream) output;

            for(Range r : ranges){
                sos.println();
                sos.println("--" + MULTIPART_BOUNDARY);
                sos.println("Content-Type: " + contentType);
                sos.println("Content-Range: bytes " + r.start + "-" + r.end + "/" + r.total);

                copy(input, output, r.start, r.length);
            }

            sos.println();
            sos.println("--" + MULTIPART_BOUNDARY + "--");
        }

        close(output);
        close(input);
    }

    private long sublong(String value, int beginIndex, int endIndex){
        String substring = value.substring(beginIndex, endIndex);
        return (substring.length() > 0) ? Long.parseLong(substring) : -1;
    }

    private boolean accepts(String acceptHeader, String toAccept){
        String[] acceptValues = acceptHeader.split("\\s*(,|;)\\s*");
        Arrays.sort(acceptValues);

        return Arrays.binarySearch(acceptValues, toAccept) > -1
                || Arrays.binarySearch(acceptValues, toAccept.replaceAll("/.*$", "/*")) > -1
                || Arrays.binarySearch(acceptValues, "*/*") > -1;
    }

    private boolean matches(String matchHeader, String toMatch){
        String[] matchValues = matchHeader.split("\\s*,\\s*");
        Arrays.sort(matchValues);

        return Arrays.binarySearch(matchValues, toMatch) > -1 || Arrays.binarySearch(matchValues, "*") > -1;
    }

    private void copy(RandomAccessFile input, OutputStream output, long start, long length) throws IOException{
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        int read;

        if(input.length() == length){
            while((read = input.read(buffer)) > 0){
                output.write(buffer, 0 ,read);
            }
        }else{
            input.seek(start);
            long toRead = length;

            while((read = input.read(buffer)) > 0){
                if((toRead -= read) > 0){
                    output.write(buffer,0 , read);
                }else{
                    output.write(buffer, 0, (int) toRead + read);
                    break;
                }
            }
        }
    }

    public void close(Closeable resource){
        if(resource != null){
            try{
                resource.close();
            }catch(IOException e){
                //忽略
            }
        }
    }

    private class Range{
        long start;
        long end;
        long length;
        long total;

        public Range(long start, long end, long total){
            this.start = start;
            this.end = end;
            this.length = end - start + 1;
            this.total = total;
        }
    }
}
