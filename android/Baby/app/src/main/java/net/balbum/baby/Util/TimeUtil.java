package net.balbum.baby.Util;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by hyes on 2015. 11. 11..
 */
public class TimeUtil {

    public static String getRecordedMoment() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        return df.format(new Date().getTime());
    }

    public static String getRecordedTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HHmmss", Locale.ENGLISH);
        return df.format(new Date().getTime());
    }
    //yyyy/MM/dd HH:mm:ss
    //"EEE, MMM d, yyyy"
    //dd-MMM-yyyy"
    //"yyyy. MM. dd. E"
    //"yyyy. MMM, d, EEE"
}
