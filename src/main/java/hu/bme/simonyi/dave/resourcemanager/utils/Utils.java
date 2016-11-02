package hu.bme.simonyi.dave.resourcemanager.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by dkiss on 2016. 10. 31..
 */
public final class Utils {

    private Utils() {
        // Empty private constructor for static class.
    }

    public static String getCurrentTimeInString() {
        DateFormat df = new SimpleDateFormat("YYYY.MM.dd HH:mm");
        Date today = Calendar.getInstance().getTime();
        return df.format(today);
    }
}
