package ru.sbtqa.tag.qautils.managers;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import ru.sbtqa.tag.qautils.strategies.DateStrategy;

/**
 * Support class to work with date and time.
 */
public class DateManager {
    
    private static final DateFormatSymbols alphabeticFormatSymbols = new DateFormatSymbols() {

        @Override
        public String[] getMonths() {
            return new String[]{"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};
        }
    };

    private DateManager() {
        throw new IllegalAccessError("Utility class");
    }
    
    
    /**
     * Get current date and time
     *
     * @return a {@link java.util.Date} object
     */
    public static Date getNowDate() {
        return new Date();
    }

    /**
     * Get current date in desired format
     *
     * @param format the pattern describing the date and time format
     * @return a datetime in format
     */
    public static String getCurrentDate(String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);

        return simpleDateFormat.format(new Date());
    }

    /**
     * Get current time in milliseconds
     *
     * @return a current timestamp in milliseconds
     */
    public static Long getCurrentTimestamp() {
        return Calendar.getInstance().getTimeInMillis();
    }

    /**
     * Get date by date strategy in default date format dd.MM.yyyy
     *
     * @param dateStrategy the {ru.sbtqa.tag.qautils.strategies.DateStrategy} object
     * @param format the pattern describing the date and time format
     * @return a {@link java.lang.String} object in default date format dd.MM.yyyy
     */
    public static String getDate(DateStrategy dateStrategy, String format) {
        Calendar calendar = Calendar.getInstance();

        switch (dateStrategy) {
            case BYESTERDAY:
                calendar.add(Calendar.DATE, -2);
                break;
            case YESTERDAY:
                calendar.add(Calendar.DATE, -1);
                break;
            case TODAY:
                break;
            case TOMORROW:
                calendar.add(Calendar.DATE, +1);
                break;
            case ATOMORROW:
                calendar.add(Calendar.DATE, +2);
                break;
        }

        return new SimpleDateFormat(format).format(calendar.getTime());
    }

    /**
     * Get date by date strategy in default date format dd.MM.yyyy
     *
     * @param dateStrategy the {ru.sbtqa.tag.qautils.strategies.DateStrategy} object
     * @return a {@link java.lang.String} object in default date format dd.MM.yyyy
     */
    public static String getDate(DateStrategy dateStrategy) {
        return getDate(dateStrategy, "dd.MM.yyyy");
    }

    /**
     * Convert milliseconds long value to Date.
     *
     * @param milliseconds the {@link long} object
     * @return a {@link java.util.Date} object
     */
    public static Date toDate(long milliseconds) {
        Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);

        return calendar.getTime();
    }

    /**
     * Convert Date to string by format
     *
     * @param date the {@link java.util.Date}
     * @param format the pattern describing the date and time format
     * @return a {@link java.lang.String} object in format
     */
    public static String toString(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);

        return dateFormat.format(date);
    }

    /**
     * Convert Date to string by specified format with alphabetic month
     *
     * @param date the {@link java.util.Date} object
     * @param format the pattern describing the date and time format
     * @return a {@link java.lang.String} object in format with alphabetic month
     */
    public static String toStringWithAlphabeticMonth(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, alphabeticFormatSymbols);

        return dateFormat.format(date);
    }

    /**
     * Convert string date value to milliseconds
     *
     * @param sDate the {@link java.lang.String} date
     * @param format the pattern describing the date and time format
     * @return milliseconds as {@link long} object
     * @throws java.text.ParseException if the source date is not in format
     */
    public static long toMills(String sDate, String format) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Calendar calendar = java.util.Calendar.getInstance();
        Date date = dateFormat.parse(sDate);
        calendar.setTime(date);

        return calendar.getTimeInMillis();
    }

    /**
     * Parse date from string by format and timezone
     *
     * @param inputDate the {@link java.lang.String} object date
     * @param inputDateFormat the pattern describing the date and time format
     * @param inputTimeZone the ID for a TimeZone, either an abbreviation such as "PST", a
     * full name such as "America/Los_Angeles", or a custom ID such as "GMT-8:00". Note
     * that the support of abbreviations is for JDK 1.1.x compatibility only and full
     * names should be used
     * @return the {@link java.util.Date} object
     * @throws java.text.ParseException if cannot parse result date by format
     */
    public static Date parseDate(String inputDate, String inputDateFormat, String inputTimeZone) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(inputDateFormat);
        dateFormat.setTimeZone(TimeZone.getTimeZone(inputTimeZone));

        return dateFormat.parse(inputDate);
    }

    /**
     * Parse date from string by format
     *
     * @param inputDate the {@link java.lang.String} object date
     * @param inputDateFormat the pattern describing the date and time format
     * @return the {@link java.util.Date} object
     * @throws java.text.ParseException if cannot parse result date by format
     */
    public static Date parseDate(String inputDate, String inputDateFormat) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(inputDateFormat);

        return dateFormat.parse(inputDate);
    }

    /**
     * Parse date from timestamp
     *
     * @param timestamp a {@link java.lang.Long} object
     * @param format the pattern describing the date and time format
     * @return the {@link java.util.Date} object
     */
    public static String parseTimestamp(Long timestamp, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);

        return dateFormat.format(DateManager.toDate(timestamp));
    }

    /**
     * Increases current date to n-days
     *
     * @param dateFormat the pattern describing the date and time format
     * @param days days to increase
     * @return the current date increased by n-days
     */
    public static String increaseCurrentDateByDays(String dateFormat, int days) {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getNowDate());
        calendar.add(Calendar.DATE, days);

        return format.format(calendar.getTime());
    }

    /**
     * Increases current date to n-hours
     *
     * @param dateFormat the pattern describing the date and time format
     * @param hours hours to increase
     * @return the current date increased by n-hours
     */
    public static String increaseCurrentDateByHours(String dateFormat, int hours) {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getNowDate());
        calendar.add(Calendar.HOUR, hours);

        return format.format(calendar.getTime());
    }

    /**
     * Convert date from one timezone to another. Date should be in yyyy-MM-dd HH:mm:ss
     * date format.
     *
     * @param srcDate the date to convert
     * @param srcTimezone the date source timezone
     * @param dstTimezone the date destination timezone
     * @throws java.text.ParseException if the source date is not in yyyy-MM-dd HH:mm:ss
     * date format
     * @return the source date in destination timezone.
     */
    public static String convertTimezones(String srcDate, String srcTimezone, String dstTimezone) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone(srcTimezone));
        Date date = dateFormat.parse(srcDate);
        dateFormat.setTimeZone(TimeZone.getTimeZone(dstTimezone));

        return dateFormat.format(date);
    }
}
