package com.hypq.converter;

import org.springframework.core.convert.converter.Converter;
import sun.rmi.runtime.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter implements Converter<String,Date> {
    @Override
    public Date convert(String date) {
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
        Date d= null;
        try {
            d = dateFormat.parse(date);
        } catch (ParseException e) {
            System.out.println("日期格式错误");
        }
        return d;
    }
}
