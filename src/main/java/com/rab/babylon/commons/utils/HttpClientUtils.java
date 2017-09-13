package com.rab.babylon.commons.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

public class HttpClientUtils{

    private static final Charset CHARSET = Charset.forName("UTF-8");

    private HttpClientUtils(){

    }

    public static CloseableHttpClient createHttpsClient(String certificate, String password) throws Exception{
        if(StringUtils.isBlank(certificate)){
            throw new IllegalArgumentException("证书路径不能为空");
        }

        try(FileInputStream fis = new FileInputStream(new File(certificate))){
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(fis, password.toCharArray());

            SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(keyStore, new TrustSelfSignedStrategy()).loadKeyMaterial(keyStore, password.toCharArray()).build();

            SSLConnectionSocketFactory factory = new SSLConnectionSocketFactory(sslContext, new String[]{"TLSv1"}, null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);

            return HttpClients.custom().setSSLSocketFactory(factory).build();
        }
    }

    public static CloseableHttpClient createHttpsClient() throws Exception{
        TrustStrategy trust = new TrustStrategy(){
            @Override
            public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException{
                return true;
            }
        };

        SSLContext context = new SSLContextBuilder().loadTrustMaterial(null, trust).build();

        SSLConnectionSocketFactory factory = new SSLConnectionSocketFactory(context);

        return HttpClients.custom().setSSLSocketFactory(factory).build();
    }

    public static CloseableHttpClient createHttpClient(){
        return HttpClients.createDefault();
    }

    public static void close(CloseableHttpClient client) throws Exception{
        if(client != null){
            client.close();
        }
    }

    public static HttpPost getPost(String url, Map<String, Object> params){
        if(StringUtils.isBlank(url)){
            throw new IllegalArgumentException("请求的URL不能为空");
        }

        HttpPost post = new HttpPost(url);

        if(params != null && !params.isEmpty()){
            List<NameValuePair> list = new ArrayList<NameValuePair>();

            for(Map.Entry<String, Object> param : params.entrySet()){
                list.add(new BasicNameValuePair(param.getKey(), ObjectUtils.toString(param.getValue())));
            }

            post.setEntity(new UrlEncodedFormEntity(list, CHARSET));
        }

        return post;
    }

    public static HttpPost getPost(String url, Object value){
        if(StringUtils.isBlank(url)){
            throw new IllegalArgumentException("请求的URL不能为空");
        }

        HttpPost post = new HttpPost(url);

        if(value != null){
            String json = ObjectUtils.serialize(value);
            StringEntity entity = new StringEntity(json, CHARSET);
            post.setEntity(entity);
        }

        return post;
    }

    public static HttpGet getGet(String url, Map<String, Object> params){
        if(StringUtils.isBlank(url)){
            throw new IllegalArgumentException("请求的URL不能为空");
        }

        if(params != null && !params.isEmpty()){
            Set<String> keys = params.keySet();
            String[] keysArray = keys.toArray(new String[keys.size()]);
            Arrays.sort(keysArray);

            StringBuffer sb = new StringBuffer(url);

            if(url.lastIndexOf("?") == -1){
                sb.append("?");
            }else{
                sb.append("&");
            }

            for(String key : keys){
                sb.append(key).append("=").append(ObjectUtils.toString(params.get(key))).append("&");
            }

            url = sb.deleteCharAt(sb.lastIndexOf("&")).toString();
        }

        return new HttpGet(url);
    }
}
