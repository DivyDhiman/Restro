package functionalityAll;


import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTime {

    private String resultDate;

    public String getCurrentTimeInUTC() {
        Date d = new Date();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");
            String strtime = sdf.format(d);
            resultDate = String.valueOf(strtime);
        } catch (Exception e) {
            resultDate = "error";
            CatchResponse.Report(e);
            e.printStackTrace();
        }

        return resultDate;
    }

    public String getTime(long time) {
        int SECOND = 1000;
        int MINUTE = 60 * SECOND;
        int HOUR = 60 * MINUTE;
        int DAY = 24 * HOUR;

        if (time < 1000000000000L) {
            time *= 1000;
        }

        long now = System.currentTimeMillis();
        if (time > now || time <= 0) {
            return null;
        }

        final long diff = now - time;
        if (diff < MINUTE) {
            return "just now";
        } else if (diff < 2 * MINUTE) {
            return "a minute ago";
        } else if (diff < 50 * MINUTE) {
            return diff / MINUTE + " minutes ago";
        } else if (diff < 90 * MINUTE) {
            return "an hour ago";
        } else if (diff < 24 * HOUR) {
            return diff / HOUR + " hours ago";
        } else if (diff < 48 * HOUR) {
            return "yesterday";
        } else {
            return diff / DAY + " days ago";
        }

    }

}
