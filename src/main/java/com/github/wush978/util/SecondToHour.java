package com.github.wush978.util;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by wush.wu on 2016/12/20.
 */
public class SecondToHour {

    public static int SecondToHour(long time) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.setTime(new Date(time));
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

}
