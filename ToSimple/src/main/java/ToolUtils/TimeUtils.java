package ToolUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TimeUtils {
    public static String toNormalTime(String string) {
        char[] array = string.toCharArray();
        int size = array.length;
        char[] arrayPlace = new char[array.length - 1];
        for (int i = 0; i < size; i++) {
            if (i == size - 1) {
                break;
            }
            if (array[i] == 'T') {
                arrayPlace[i] = ' ';
            } else {
                arrayPlace[i] = array[i];
            }
        }
        return new String(arrayPlace);
    }

    public static String GTMToLocal(String GTMDate) {
        String convertString = toNormalTime(GTMDate);
        SimpleDateFormat format;
        format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        Date result_date;
        long result_time = 0;
        if (null == GTMDate) {
            return GTMDate;
        } else {
            try {
                format.setTimeZone(TimeZone.getTimeZone("GMT00:00"));
                result_date = format.parse(convertString);
                result_time = result_date.getTime();
                format.setTimeZone(TimeZone.getDefault());
                return format.format(result_time);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return GTMDate;
    }

    public static Date getLocalTime(String GTMDate) throws ParseException {
        SimpleDateFormat format;
        format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        return format.parse(GTMToLocal(GTMDate));
    }

    public static void main(String[] args) {
        String time = "2017-07-04T08:39:29.471Z";
        String timeS = GTMToLocal(time);
        System.out.println(toNormalTime(time));

    }
}
