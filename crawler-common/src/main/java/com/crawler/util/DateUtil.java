package com.crawler.util;

import java.util.Calendar;
import java.util.Date;

/**
 * @program: aukey-customer-service
 * @author: wgf
 * @create: 2019-01-29 16:10
 * @description:
 **/
public class DateUtil {
    private DateUtil() {
    }

    /**
     * 日期运算
     *
     * @param date     日期
     * @param value    加减数值，减用负数表示
     * @param dateUnit 参考 {@link java.util.Calendar} 的常量
     * @return
     */
    public static Date dateOperation(Date date, int value, int dateUnit) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        rightNow.add(dateUnit, value);
        return rightNow.getTime();
    }
}
