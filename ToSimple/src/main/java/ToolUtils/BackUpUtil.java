package ToolUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class BackUpUtil {

    private static long getTimeMillis(String time) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
            DateFormat dayFormat = new SimpleDateFormat("yy-MM-dd");
            Date curDate = dateFormat.parse(dayFormat.format(new Date()) + " " + time);
            return curDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ScheduledFuture executeEightAtNightPerDay(String hour, String min, Runnable back) {

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
        long oneDay = 24 * 60 * 60 * 1000;
        long initDelay = getTimeMillis(hour + ":" + min + ":00") - System.currentTimeMillis();
        initDelay = initDelay > 0 ? initDelay : oneDay + initDelay;

        System.out.println("HHHH");
        return executor.scheduleAtFixedRate(
                back,
                initDelay,
                oneDay,
                TimeUnit.MILLISECONDS);


    }

    class EchoServer implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("This is a echo server. The current time is " +
                    System.currentTimeMillis() + ".");
        }
    }

}
