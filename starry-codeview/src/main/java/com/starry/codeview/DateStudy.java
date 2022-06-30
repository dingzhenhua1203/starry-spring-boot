package com.starry.codeview;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateStudy {
    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parse = sf.parse("20220702");
        Date parse2 = sf2.parse("2022-07-05 12:10:05");
        // 1656994205000 13位 毫秒级
        long time = parse2.getTime();
        String format = sf2.format(parse);
        // 122-6-2 2022-1900
        String year = parse2.getYear() + "-" + parse2.getMonth() + "-" + parse2.getDay();
        // 当前时间
        Date now = new Date();
        Date end = new Date(2011, 12, 3);
        String format1 = sf2.format(now);
        // 当前时间戳
        long l = System.currentTimeMillis();
        // 测量运行时间 常用于计算方法执行前后耗时 单位是纳秒 需要转毫秒或者秒
        long l1 = System.nanoTime();
        Calendar calendar = Calendar.getInstance();
        // 2022
        int i = calendar.get(Calendar.YEAR);
    }
}
