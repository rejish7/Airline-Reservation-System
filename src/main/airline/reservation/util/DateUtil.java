package airline.reservation.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private static final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    private static final SimpleDateFormat displayFormat = new SimpleDateFormat("dd MMM yyyy, hh:mm a");
    
    public static Date parseDate(String dateStr) throws ParseException {
        return dateFormat.parse(dateStr);
    }
    
    public static Date parseDateTime(String dateTimeStr) throws ParseException {
        return dateTimeFormat.parse(dateTimeStr);
    }
    
    public static String formatDate(Date date) {
        if (date == null) return "";
        return dateFormat.format(date);
    }
    
    public static String formatDateTime(Date date) {
        if (date == null) return "";
        return dateTimeFormat.format(date);
    }
    
    public static String formatForDisplay(Date date) {
        if (date == null) return "";
        return displayFormat.format(date);
    }
    
    public static boolean isDateInFuture(Date date) {
        return date != null && date.after(new Date());
    }
    
    public static boolean isSameDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) return false;
        
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        return fmt.format(date1).equals(fmt.format(date2));
    }
    
    public static long getDaysDifference(Date date1, Date date2) {
        long diff = date2.getTime() - date1.getTime();
        return diff / (24 * 60 * 60 * 1000);
    }
    
    public static Date addDays(Date date, int days) {
        return new Date(date.getTime() + (days * 24L * 60 * 60 * 1000));
    }
}
