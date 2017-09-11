package com.rab.commons.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 时间工具
 */
public class DateUtils{

    public static final long MILLIS_PER_SECOND = 1000;

    public static final long MILLIS_PER_MINUTE = 60 * MILLIS_PER_SECOND;

    public static final long MILLIS_PER_HOUR = 60 * MILLIS_PER_MINUTE;

    public static final long MILLIS_PER_DAY = 24 * MILLIS_PER_HOUR;

    private DateUtils(){

    }

    /**
     * 获取两个时间点之间的毫秒数差
     * 时间点不分先后
     */
    public static int differenceByMillseconds(Date date1, Date date2){
        if(date1 == null || date2 == null){
            throw new IllegalArgumentException("时间点不能为空");
        }

        Long difference = Math.abs(date1.getTime() - date2.getTime());

        return Integer.valueOf(difference.toString());
    }

    /**
     * 获取两个时间点之间的秒数差
     * 时间点不分先后
     * 精确到秒
     */
    public static int differenceBySeconds(Date date1, Date date2){
        if(date1 == null || date2 == null){
            throw new IllegalArgumentException("时间点不能为空");
        }

        Long difference = Math.abs(date1.getTime() - date2.getTime()) / MILLIS_PER_SECOND;

        return Integer.valueOf(difference.toString());
    }

    /**
     * 获取两个时间点之间的分钟数差
     * 时间点不分先后
     * 精确到分钟
     */
    public static int differenceByMinutes(Date date1, Date date2){
        if(date1 == null || date2 == null){
            throw new IllegalArgumentException("时间点不能为空");
        }

        Long difference = Math.abs(date1.getTime() - date2.getTime()) / MILLIS_PER_MINUTE;

        return Integer.valueOf(difference.toString());
    }

    /**
     * 获取两个时间点之间的小时数差
     * 时间点不分先后
     * 精确到小时
     */
    public static int differenceByHours(Date date1, Date date2){
        if(date1 == null || date2 == null){
            throw new IllegalArgumentException("时间点不能为空");
        }

        Long difference = Math.abs(date1.getTime() - date2.getTime()) / MILLIS_PER_HOUR;

        return Integer.valueOf(difference.toString());
    }

    /**
     * 获取两个时间点之间的天数差
     * 时间点不分先后
     * 精确到天
     */
    public static int differenceByDays(Date date1, Date date2){
        if(date1 == null || date2 == null){
            throw new IllegalArgumentException("时间点不能为空");
        }

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date1);
        int day1 = calendar.get(Calendar.DAY_OF_YEAR);

        calendar.setTime(date2);
        int day2 = calendar.get(Calendar.DAY_OF_YEAR);

        return Math.abs(day1 - day2);
    }

    /**
     * 获取时间点所在周的第一天
     */
    public static Date firstDayOfWeek(Date date){
        if(date == null){
            throw new IllegalArgumentException("时间点不能为空");
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_WEEK, cal.getActualMinimum(Calendar.DAY_OF_WEEK));

        return cal.getTime();
    }

    /**
     * 获取时间点所在周的最后一天
     */
    public static Date lastDayOfWeek(Date date){
        if(date == null){
            throw new IllegalArgumentException("时间点不能为空");
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_WEEK, cal.getActualMaximum(Calendar.DAY_OF_WEEK));

        return cal.getTime();
    }

    /**
     * 获取时间点所在月的第一天
     */
    public static Date firstDayOfMonth(Date date){
        if(date == null){
            throw new IllegalArgumentException("时间点不能为空");
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));

        return cal.getTime();
    }

    /**
     * 获取时间点所在月的最后一天
     */
    public static Date lastDayOfMonth(Date date){
        if(date == null){
            throw new IllegalArgumentException("时间点不能为空");
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));

        return cal.getTime();
    }

    /**
     * 获取时间点所在年的第一天
     */
    public static Date firstDayOfYear(Date date){
        if(date == null){
            throw new IllegalArgumentException("时间点不能为空");
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_YEAR, cal.getActualMinimum(Calendar.DAY_OF_YEAR));

        return cal.getTime();
    }

    /**
     * 获取时间点所在年的最后一天
     */
    public static Date lastDayOfYear(Date date){
        if(date == null){
            throw new IllegalArgumentException("时间点不能为空");
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_YEAR, cal.getActualMaximum(Calendar.DAY_OF_YEAR));

        return cal.getTime();
    }

    /**
     * 设置时间点为所在天的凌晨
     * 即当天00:00:00.0
     */
    public static Date firstTimeOfDay(Date date){
        if(date == null){
            throw new IllegalArgumentException("时间点不能为空");
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTime();
    }

    /**
     * 设置时间点为所在天的深夜
     * 即当天23::59:59.999
     */
    public static Date lastTimeOfDay(Date date){
        if(date == null){
            throw new IllegalArgumentException("时间点不能为空");
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);

        return cal.getTime();
    }

    /**
     * 判断两个时间点是不是同一天
     * 精确到天
     */
    public static boolean isSameDay(Date date1, Date date2){
        if(date1 == null || date2 == null){
            throw new IllegalArgumentException("时间点不能为空");
        }

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA)
                && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
    }

    /**
     * 判断两个时间点是不是同一时刻
     * 精确到毫秒
     */
    public static boolean isSameInstant(Date date1, Date date2){
        if(date1 == null || date2 == null){
            throw new IllegalArgumentException("时间点不能为空");
        }

        return (date1.getTime() == date2.getTime());
    }

    /**
     * 解析时间字符串
     */
    public static Date parseDate(String content, Locale locale, String parsePattern, boolean lenient) throws ParseException{
        if(StringUtils.isBlank(content) || StringUtils.isBlank(parsePattern)){
            throw new IllegalArgumentException("时间或解析模式不能为空");
        }

        //创建时间解析器
        SimpleDateFormat parser = null;

        if(locale == null){
            parser = new SimpleDateFormat();
        }else{
            parser = new SimpleDateFormat("", locale);
        }

        parser.setLenient(lenient);

        final ParsePosition pos = new ParsePosition(0);

        String pattern = parsePattern;

        //需要确保 'ZZ' 的输出不能通过parser
        if(parsePattern.endsWith("ZZ")){
            pattern = pattern.substring(0, pattern.length() - 1);
        }

        parser.applyPattern(pattern);
        pos.setIndex(0);

        String $content = content;
        //需要确保 'ZZ' 的输出不会导致parser抛出异常
        if(parsePattern.endsWith("ZZ")){
            $content = content.replaceAll("([-+][0-9][0-9]):([0-9][0-9])$", "$1$2");
        }

        Date date = parser.parse($content, pos);

        if(date != null && pos.getIndex() == $content.length()){
            return date;
        }

        throw new ParseException("不能解析的时间格式 : " + content, -1);
    }

    /**
     * 解析时间对象
     */
    public static String formatDate(Date date, Locale locale, String parsePattern, boolean lenient){
        if(date == null || StringUtils.isBlank(parsePattern)){
            throw new IllegalArgumentException("时间或解析模式不能为空");
        }

        //创建时间解析器
        SimpleDateFormat parser = null;

        if(locale == null){
            parser = new SimpleDateFormat(parsePattern);
        }else{
            parser = new SimpleDateFormat(parsePattern, locale);
        }

        parser.setLenient(lenient);

        return parser.format(date);
    }

    /**
     * 向时间点增加毫秒数
     */
    public static Date addMilliseconds(Date date, int amount){
        return add(date, Calendar.MILLISECOND, amount);
    }

    /**
     * 向时间点增加秒数
     */
    public static Date addSeconds(Date date, int amount){
        return add(date, Calendar.SECOND, amount);
    }

    /**
     * 向时间点增加分钟数
     */
    public static Date addMinutes(Date date, int amount){
        return add(date, Calendar.MINUTE, amount);
    }

    /**
     * 向时间点增加小时数
     */
    public static Date addHours(Date date, int amount){
        return add(date, Calendar.HOUR_OF_DAY, amount);
    }

    /**
     * 向时间点增加天数
     */
    public static Date addDays(Date date, int amount){
        return add(date, Calendar.DAY_OF_MONTH, amount);
    }

    /**
     * 向时间点增加周数
     */
    public static Date addWeeks(Date date, int amount){
        return add(date, Calendar.WEEK_OF_YEAR, amount);
    }

    /**
     * 向时间点增加月数
     */
    public static Date addMonths(Date date, int amount){
        return add(date, Calendar.MONTH, amount);
    }

    /**
     * 向时间点增加年数
     */
    public static Date addYears(Date date, int amount){
        return add(date, Calendar.YEAR, amount);
    }

    /**
     * 向时间点添加指定增量
     */
    private static Date add(Date date, int calendarField, int amount){
        if(date == null){
            throw new IllegalArgumentException("时间点不能为空");
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(calendarField, amount);

        return cal.getTime();
    }
}
