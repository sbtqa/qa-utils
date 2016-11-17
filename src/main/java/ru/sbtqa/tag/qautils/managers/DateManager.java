package ru.sbtqa.tag.qautils.managers;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DateManager class.
 *
 * @author Viktor Sidochenko <viktor.sidochenko@gmail.com>
 */
//TODO i18n
public class DateManager {

    private static final Logger log = LoggerFactory.getLogger(DateManager.class);

    private static final DateFormatSymbols alphabeticFormatSymbols = new DateFormatSymbols() {
        @Override
        public String[] getMonths() {
            return new String[]{"января", "февраля", "марта", "апреля", "мая", "июня",
                "июля", "августа", "сентября", "октября", "ноября", "декабря"};
        }

    };

    /**
     * <p>
     * getPeriodEnd.</p>
     *
     * @param date a {@link java.util.Date} object.
     * @param datepart a int.
     * @return a {@link java.util.Date} object.
     * @throws java.lang.Exception if any.
     */
    public static Date getPeriodEnd(Date date, int datepart) throws Exception {
        Date beginDate = getPeriodBegin(date, datepart);
        Calendar c = Calendar.getInstance();
        c.setTime(beginDate);

        switch (datepart) {
            case Calendar.YEAR:
                c.add(Calendar.YEAR, 1);
                break;
            case Calendar.MONTH:
                c.add(Calendar.MONTH, 1);
                break;
            case Calendar.DAY_OF_MONTH:
                c.add(Calendar.DAY_OF_MONTH, 1);
                break;
            case Calendar.HOUR_OF_DAY:
                c.add(Calendar.HOUR_OF_DAY, 1);
                break;
            case Calendar.MINUTE:
                c.add(Calendar.MINUTE, 1);
                break;
            case Calendar.SECOND:
                c.add(Calendar.SECOND, 1);
                break;
            default:
                throw new Exception("Недопустимый период! Используйте Calendar.YEAR-Calendar.SECOND!");
        }
        c.add(Calendar.MILLISECOND, -1);
        return c.getTime();
    }

    /**
     * getPeriodBegin.
     *
     * @param date a {@link java.util.Date} object.
     * @param datepart a int.
     * @return a {@link java.util.Date} object.
     * @throws java.lang.Exception if any.
     */
    public static Date getPeriodBegin(Date date, int datepart) throws Exception {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        switch (datepart) {
            case Calendar.YEAR:
                c.set(Calendar.MONTH, 1);
                break;
            case Calendar.MONTH:
                c.set(Calendar.DAY_OF_MONTH, 1);
                break;
            case Calendar.DAY_OF_MONTH:
                c.set(Calendar.HOUR_OF_DAY, 0);
                break;
            case Calendar.HOUR_OF_DAY:
                c.set(Calendar.MINUTE, 0);
                break;
            case Calendar.MINUTE:
                c.set(Calendar.SECOND, 0);
                break;
            case Calendar.SECOND:
                c.set(Calendar.MILLISECOND, 0);
                break;
            default:
                throw new Exception("Недопустимый период! Используйте Calendar.YEAR-Calendar.SECOND!");
        }
        return c.getTime();
    }

    /**
     * Convert milliseconds value to Date.
     *
     * @param string a {@link java.lang.String} object.
     * @throws java.lang.Exception Throws "Use date in format: milliseconds
     * since UNIX time"
     * @return a {@link java.util.Date} object.
     */
    public static Date toDate(String string) throws Exception {
        try {
            long mills = Long.parseLong(string.trim());
            Calendar cdr = java.util.Calendar.getInstance();
            cdr.setTimeInMillis(mills);
            return cdr.getTime();
        } catch (NumberFormatException e) {
            throw new Exception("Use date in format: <milliseconds since UNIX time>");
        }
    }

    /**
     * Convert Date to string by specified format
     *
     * @param date Date
     * @param format date format
     * @return formatted date string: Letter Date or Time Component Presentation
     * Examples G Era designator Text AD y Year Year 1996; 96 M Month in year
     * Month July; Jul; 07 w Week in year Number 27 W Week in month Number 2 D
     * Day in year Number 189 d Day in month Number 10 F Day of week in month
     * Number 2 E Day in week Text Tuesday; Tue a Am/pm marker Text PM H Hour in
     * day (0-23) Number 0 k Hour in day (1-24) Number 24 K Hour in am/pm (0-11)
     * Number 0 h Hour in am/pm (1-12) Number 12 m Minute in hour Number 30 s
     * Second in minute Number 55 S Millisecond Number 978 z Time zone General
     * time zone Pacific Standard Time; PST; GMT-08:00 Z Time zone RFC 822 time
     * zone -0800
     */
    public static String toString(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    /**
     * Convert Date to string by specified format with alphabetic month
     *
     * @param date Date
     * @param format date format
     * @return formatted date string: Letter Date or Time Component Presentation
     * Examples G Era designator Text AD y Year Year 1996; 96 M Month in year
     * Month July; Jul; 07 w Week in year Number 27 W Week in month Number 2 D
     * Day in year Number 189 d Day in month Number 10 F Day of week in month
     * Number 2 E Day in week Text Tuesday; Tue a Am/pm marker Text PM H Hour in
     * day (0-23) Number 0 k Hour in day (1-24) Number 24 K Hour in am/pm (0-11)
     * Number 0 h Hour in am/pm (1-12) Number 12 m Minute in hour Number 30 s
     * Second in minute Number 55 S Millisecond Number 978 z Time zone General
     * time zone Pacific Standard Time; PST; GMT-08:00 Z Time zone RFC 822 time
     * zone -0800
     */
    public static String toStringWithAlphabeticMonth(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, alphabeticFormatSymbols);
        return dateFormat.format(date);
    }

    /**
     * Convert date value to milliseconds
     *
     * @param string a {@link java.lang.String} object.
     * @throws java.text.ParseException if any.
     * @return a long.
     * @throws java.lang.Exception if any.
     */
    public static long toMills(String string) throws ParseException, Exception {
        try {
            String normDate = string;
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Calendar cdr = java.util.Calendar.getInstance();
            Date date = dateFormat.parse(normDate);

            cdr.setTime(date);
            return cdr.getTimeInMillis();
        } catch (ParseException e) {
            throw new Exception("Use date in format: <dd-MM-yyyy>");
        }
    }

    /**
     * Convert date value to milliseconds
     *
     * @param string a {@link java.lang.String} object.
     * @param format a {@link java.lang.String} object.
     * @throws java.text.ParseException if any.
     * @return a long.
     * @throws java.lang.Exception if any.
     */
    public static long toMills(String string, String format) throws ParseException, Exception {
        String normDate = string;
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Calendar cdr = java.util.Calendar.getInstance();
        Date date = dateFormat.parse(normDate);

        cdr.setTime(date);
        return cdr.getTimeInMillis();
    }

    /**
     * Parse date from string
     *
     * @param inputDate String in format: dd-MM-yyyy
     * @return Date dd-MM-yyyy
     * @throws java.lang.Exception if any.
     */
    public static Date parseDate(String inputDate) throws Exception {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            return dateFormat.parse(inputDate);
        } catch (ParseException ex) {
            throw new Exception("ERROR: cannot parse date <" + inputDate + ">. Use date in format: <dd-MM-yyyy>");
        }

    }

    /**
     * Parse date from string
     *
     * @param timestamp a {@link java.lang.Long} object.
     * @param format output date
     * @return Date dd-MM-yyyy
     * @throws java.lang.Exception if any.
     */
    public static String parseTimestamp(Long timestamp, String format) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(DateManager.toDate(String.valueOf(timestamp)));
    }

    /**
     * Parse date from string
     *
     * @param inputDate String
     * @param inputDateFormat String
     * @return Date dd-MM-yyyy hh:mm:ss
     * @throws java.lang.Exception if any.
     */
    public static Date parseDate(String inputDate, String inputDateFormat) throws Exception {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(inputDateFormat);
            return dateFormat.parse(inputDate);
        } catch (ParseException ex) {
            throw new Exception("ERROR: cannot parse date <" + inputDate
                    + "> with date format <" + inputDateFormat
                    + ">. Please use SimpleDateFormat date format");
        }

    }

    /**
     * Parse date from string
     *
     * @param inputDate String
     * @param inputDateFormat String
     * @param inputTimeZone String
     * @return Date dd-MM-yyyy hh:mm:ss
     * @throws java.lang.Exception if any.
     */
    public static Date parseDate(String inputDate, String inputDateFormat, String inputTimeZone) throws Exception {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(inputDateFormat);
            dateFormat.setTimeZone(TimeZone.getTimeZone(inputTimeZone));
            return dateFormat.parse(inputDate);
        } catch (ParseException ex) {
            throw new Exception("ERROR: cannot parse date <" + inputDate
                    + "> with date format <" + inputDateFormat
                    + "> and TimeZone <" + inputTimeZone
                    + ">. Please use SimpleDateFormat date format");
        }

    }

    /**
     * Get current time in milliseconds
     *
     * @return timestamp
     */
    public static Long getCurrentTimestamp() {
        Calendar cdr = java.util.Calendar.getInstance();
        return cdr.getTimeInMillis();
    }

    /**
     * Get current date in desired format
     *
     * @param format a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    public static String getCurrentDate(String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Date now = new Date();
        return dateFormat.format(now);
    }

    /**
     * Get current date in Date format
     *
     * @return a {@link java.util.Date} object.
     */
    public static Date getNowDate() {
        Date now = new Date();
        return now;
    }

    /**
     * Increases current date to n-days
     *
     * @param format Dateformat
     * @param days Days to increase
     * @throws java.text.ParseException if any.
     * @return a {@link java.lang.String} object.
     */
    public static String increaseDaysToCurrentDate(String format, int days) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Calendar c = Calendar.getInstance();
        c.setTime(getNowDate());
        c.add(Calendar.DATE, days);
        return sdf.format(c.getTime());
    }

    /**
     * Increases current date to n-hours
     *
     * @param format Dateformat
     * @param hours hours Days to increase
     * @throws java.text.ParseException if any.
     * @return a {@link java.lang.String} object.
     */
    public static String increaseHoursToCurrentDate(String format, int hours) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Calendar c = Calendar.getInstance();
        c.setTime(getNowDate());
        c.add(Calendar.HOUR, hours);
        return sdf.format(c.getTime());
    }

    /**
     * Converts date from one timezone to another
     *
     * @param srcDate a {@link java.lang.String} object.
     * @param srcTimezone a {@link java.lang.String} object.
     * @param dstTimezone a {@link java.lang.String} object.
     * @throws java.text.ParseException if any.
     * @return a {@link java.lang.String} object.
     */
    public static String convertTimezones(String srcDate, String srcTimezone, String dstTimezone) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone(srcTimezone));
        Date d = dateFormat.parse(srcDate);
        dateFormat.setTimeZone(TimeZone.getTimeZone(dstTimezone));
        return dateFormat.format(d);
    }

    /**
     * возвращает дату в формате "dd.MM.yyyy" в зависимости от входного
     * параметра (текущую, вчерашнюю, завтрашнюю, послезавтрашнюю,
     * позавчерашнюю)
     *
     * @param when - может принимать значения=
     * today||yesterday||tomorrow||atomorrow||byesterday
     * @throws java.text.ParseException if any.
     * @return a {@link java.lang.String} object.
     */
    //TODO: Make when as enum
    //TODO: Translate javadoc
    public static String findDate(String when) throws ParseException {
        String todate = null;
        Calendar cal = Calendar.getInstance();
        if (when.equals("today")) {
            todate = new SimpleDateFormat("dd.MM.yyyy").format(cal.getTime());
        }
        if (when.equals("yesterday")) {
            cal.add(Calendar.DATE, -1);
            todate = new SimpleDateFormat("dd.MM.yyyy").format(cal.getTime());
        }
        if (when.equals("tomorrow")) {
            cal.add(Calendar.DATE, +1);
            todate = new SimpleDateFormat("dd.MM.yyyy").format(cal.getTime());
        }
        if (when.equals("atomorrow")) {
            cal.add(Calendar.DATE, +2);
            todate = new SimpleDateFormat("dd.MM.yyyy").format(cal.getTime());
        }
        if (when.equals("byesterday")) {
            cal.add(Calendar.DATE, -2);
            todate = new SimpleDateFormat("dd.MM.yyyy").format(cal.getTime());
        }
        if (todate == null) {
            log.error("Incorrect input parameters, current parameters: " + when);
        }
        //System.out.println(todate);			
        return todate;
    }
}
