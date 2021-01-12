package com.kingdee.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @ClassName CommonBizUtil
 * @Description TODO
 * @Autor Strong Li
 * @Date 12/1/2020 13:49
 * @Version V1.0
 **/
public class CommonBizUtil {

    public static String getLastPeriod(){
        Calendar current = Calendar.getInstance();
        current.add(Calendar.MONTH, -1);//上月
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        String lastPeriod = sdf.format(current.getTime());
        System.out.println(lastPeriod);
        return lastPeriod;
    }

    public static String getCurrentPeriod(){
        Calendar current = Calendar.getInstance();
        //current.add(Calendar.MONTH, -0);//上月
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        String lastPeriod = sdf.format(current.getTime());
        System.out.println(lastPeriod);
        return lastPeriod;
    }

}
