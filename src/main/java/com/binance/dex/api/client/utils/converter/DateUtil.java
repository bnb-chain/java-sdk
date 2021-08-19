package com.binance.dex.api.client.utils.converter;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {

    private static final DateTimeFormatter utcFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("UTC"));

    public static long betweenSecs(Date date1, Date date2) {
        return Duration.between(date1.toInstant(), date2.toInstant()).getSeconds();
    }

    public static long betweenDays(Date date1, Date date2) {
        return date1.toInstant().until(date2.toInstant(), ChronoUnit.DAYS);
    }

    public static boolean isSameDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        return cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
    }

    public static Date toDateFromMilliseconds(Long time) {
        if (null == time) {
            return null;
        }
        return Date.from(Instant.ofEpochMilli(time));
    }

    public static Date now() {
        return Date.from(Instant.now());
    }

    public static Date parseUTCMilliTime(String dateStr) {
        ZonedDateTime zdt = ZonedDateTime.parse(dateStr, utcFormatter);
        return Date.from(zdt.toInstant());
    }

    public static Date parseUTCTime(String dateStr) {
        return Date.from(Instant.parse(dateStr));
    }

    /**
     * Formats a date at any given format String, at any given Timezone String.
     *
     * @param date     Valid Date object
     * @param format   String representation of the format, e.g. "yyyy-MM-dd HH:mm"
     * @param timezone String representation of the time zone, e.g. "CST"
     * @return The formatted date in the given time zone.
     */
    public static String toString(final Date date, final String format, final String timezone) {
        final TimeZone tz = TimeZone.getTimeZone(timezone);
        final SimpleDateFormat formatter = new SimpleDateFormat(format);
        formatter.setTimeZone(tz);
        return formatter.format(date);
    }

    private static LocalDateTime dateToUtcDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.of("UTC"));
    }

    private static Date utcDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.of("UTC")).toInstant());
    }

    public static Date getPreviousDate(Date date) {
        LocalDateTime localDateTime = dateToUtcDateTime(date);
        LocalDateTime previousDate = localDateTime.minusDays(1l);
        return utcDateTimeToDate(previousDate);
    }

    public static String toUTCDateString(final Date date) {
        return toString(date, "yyyyMMdd", "UTC");
    }

    public static String toUTCDateString(long timeStamp) {
        return toString(new Date(timeStamp), "yyyyMMdd", "UTC");
    }

    public static Date atStartOfDay(Date date) {
        LocalDateTime localDateTime = dateToUtcDateTime(date);
        LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
        return utcDateTimeToDate(startOfDay);
    }

    public static Date atEndOfDay(Date date) {
        LocalDateTime localDateTime = dateToUtcDateTime(date);
        LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
        return utcDateTimeToDate(endOfDay);
    }


}
