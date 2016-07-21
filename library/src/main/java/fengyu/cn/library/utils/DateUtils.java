package fengyu.cn.library.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateUtils {

    private static final String TAG = "DateUtils";
    public static String getTime(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = null;
        try {
            time = sdf.format(new Timestamp(timestamp));
        } catch (Exception e) {
            D.e(TAG,"DateUtils thransfrom timestamp with error", e);
        }
        return time;
    }

    public static long FormatDate(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        java.util.Date date = null;
        try {
            date = sdf.parse(dateString);
        } catch (ParseException e) {

            D.e(TAG,"DateUtils thransfrom timestamp with error", e);
        }
        long timestamp = date.getTime();
        return timestamp;
    }

    public static String FormatDate(int year, int month, int day) {
        String date = String.valueOf(year) + "-" + String.valueOf(month) + "-"
                + String.valueOf(day);
        return date;
    }

    public static String FormatTime(int hour, int minute) {
        String date = String.valueOf(hour) + ":" + String.valueOf(minute);
        return date;
    }

    public static String FormatLocalDatetime(int year, int month, int day) {
        // String date = String.valueOf(year) + "年" + String.valueOf(month) +
        // "月"
        // + String.valueOf(day) + "日";
        String date = String.valueOf(month) + "月" + String.valueOf(day) + "日";
        return date;
    }
}
