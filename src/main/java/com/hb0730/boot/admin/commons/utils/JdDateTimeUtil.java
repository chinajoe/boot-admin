package com.hb0730.boot.admin.commons.utils;

import com.hb0730.boot.admin.commons.enums.DateFormatEnum;
import com.hb0730.boot.admin.commons.enums.DateTimeFormatEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.SignStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Date;

import static java.time.temporal.ChronoField.HOUR_OF_DAY;
import static java.time.temporal.ChronoField.MINUTE_OF_HOUR;
import static java.time.temporal.ChronoField.SECOND_OF_MINUTE;

/**
 * JdDateTimeUtil
 * 时间处理工具类
 *
 * @author qiaojinfeng3
 * @date 2019 /5/29 14:50
 */
@Slf4j
public class JdDateTimeUtil {
    // The constant TIME_START.
    private static final String TIME_START = "00:00:00";
    // The constant TIME_END.
    private static final String TIME_END = "23:59:59";
    // The constant ES_TIME_PREFIX.
    private static final char ES_TIME_PREFIX = 'T';
    // The constant ES_TIME_SUFFIX.
    private static final String ES_TIME_SUFFIX = ".000+0800";
    // ES_DATE_TIME
    private static final DateTimeFormatter ES_TIME_WITHOUT_NANO;
    // ES_DATE_TIME
    private static final DateTimeFormatter ES_DATE_TIME = DateTimeFormatter.ofPattern(DateTimeFormatEnum.DATE_OPTIONAL_TIME.getPattern());
    // YYYY_MM
    private static final DateTimeFormatter YYYY_MM_FORMATTER;
    // DEFAULT_FORMATTER
    private static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ofPattern(
        DateTimeFormatEnum.DEFAULT.getPattern());
    /**
     * The constant AUTHORIZATION_DATE_TIME_FORMATTER.
     */
    public static final DateTimeFormatter AUTHORIZATION_DATE_TIME_FORMATTER;
    // zoneId
    private static final ZoneId zoneId = ZoneId.systemDefault();
    // weekOfWeekBasedYear
    private static final TemporalField weekOfWeekBasedYear = WeekFields.ISO.weekOfWeekBasedYear();

    static {
        ES_TIME_WITHOUT_NANO = new DateTimeFormatterBuilder()
            .appendValue(HOUR_OF_DAY, 2)
            .appendLiteral(':')
            .appendValue(MINUTE_OF_HOUR, 2)
            .optionalStart()
            .appendLiteral(':')
            .appendValue(SECOND_OF_MINUTE, 2)
            .toFormatter();
        YYYY_MM_FORMATTER = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendValue(ChronoField.YEAR, 4, 10, SignStyle.EXCEEDS_PAD)
            .appendLiteral('-')
            .appendValue(ChronoField.MONTH_OF_YEAR, 2)
            .toFormatter();
        AUTHORIZATION_DATE_TIME_FORMATTER = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendValue(ChronoField.YEAR, 4, 10, SignStyle.EXCEEDS_PAD)
            .appendValue(ChronoField.MONTH_OF_YEAR, 2)
            .appendValue(ChronoField.DAY_OF_MONTH, 2)
            .appendLiteral("T")
            .appendValue(ChronoField.HOUR_OF_DAY, 2)
            .appendValue(ChronoField.MINUTE_OF_HOUR, 2)
            .optionalStart()
            .appendValue(ChronoField.SECOND_OF_MINUTE, 2)
            .appendLiteral("Z")
            .toFormatter();
    }

    /**
     * Second to time string.秒数转换为 时：分：秒
     *
     * @param seconds the seconds 秒数
     * @return the string hh:mm:ss形式，比如 01:48:30
     */
    public static String secondToTime(long seconds) {
        long temp;
        StringBuilder sb = new StringBuilder();
        temp = seconds / 3600;
        sb.append((temp < 10) ? "0" + temp + ":" : "" + temp + ":");
        temp = seconds % 3600 / 60;
        sb.append((temp < 10) ? "0" + temp + ":" : "" + temp + ":");
        temp = seconds % 3600 % 60;
        sb.append((temp < 10) ? "0" + temp : "" + temp);
        return sb.toString();
    }

    /**
     * To local date time local date time.
     *
     * @param date the date
     * @return the local date time
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        ZonedDateTime zonedDateTime = toZonedDateTime(date);
        if (null == zonedDateTime) {
            return null;
        }
        return zonedDateTime.toLocalDateTime();
    }

    /**
     * To local date time local date time.
     * yyyy-MM-dd HH:mm:ss
     *
     * @param time the time
     * @return the local date time
     */
    public static LocalDateTime toLocalDateTime(String time) {
        LocalDateTime localDateTime = parseToLocalDateTime(time, DateTimeFormatEnum.DEFAULT);
        return localDateTime;
    }

    /**
     * To zoned date time zoned date time.
     *
     * @param date the date
     * @return the zoned date time
     */
    public static ZonedDateTime toZonedDateTime(Date date) {
        if (null == date) {
            return null;
        }
        return date.toInstant().atZone(ZoneId.systemDefault());
    }

    /**
     * To local date local date.
     *
     * @param date the date
     * @return the local date
     */
    public static LocalDate toLocalDate(Date date) {
        if (null == date) {
            return null;
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * Local date to date date.
     *
     * @param localDate the local date
     * @return the date
     */
    public static Date localDateToDate(LocalDate localDate) {
        if (null == localDate) {
            return null;
        }
        return Date.from(localDate.atStartOfDay(zoneId).toInstant());
    }

    /**
     * Local date time to date date.
     *
     * @param localDateTime the local date time
     * @return the date
     */
    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        if (null == localDateTime) {
            return null;
        }
        return Date.from(localDateTime.atZone(zoneId).toInstant());
    }

    /**
     * Date time format string.格式化时间
     *
     * @param date the date
     * @return the string
     */
    public static String formatDateTime(Date date) {
        if (null == date) {
            return null;
        }
        Instant instant = Instant.ofEpochMilli(date.getTime());
        return DateTimeFormatter.ofPattern(DateTimeFormatEnum.DEFAULT.getPattern()).withZone(zoneId).format(instant);
    }

    /**
     * Format date time string.
     *
     * @param milliseconds the milliseconds
     * @return the string
     */
    public static String formatDateTime(long milliseconds) {
        if (milliseconds == 0) {
            return null;
        }
        Instant instant = Instant.ofEpochMilli(milliseconds);
        return DateTimeFormatter.ofPattern(DateTimeFormatEnum.DEFAULT.getPattern()).withZone(zoneId).format(instant);
    }

    /**
     * Format date time string.
     *
     * @param date   the date
     * @param zoneId the zoneId id
     * @return the string
     */
    public static String formatDateTime(Date date, ZoneId zoneId) {
        if (null == date) {
            return null;
        }
        Instant instant = Instant.ofEpochMilli(date.getTime());
        ZoneId systemDefault = ZoneId.systemDefault();
        if (null != zoneId) {
            systemDefault = zoneId;
        }
        return DateTimeFormatter.ofPattern(DateTimeFormatEnum.DEFAULT.getPattern()).withZone(systemDefault).format(
            instant);
    }

    /**
     * Date time format string.格式化时间
     *
     * @param millisecond the millisecond
     * @param pattern     the pattern
     * @return the string
     */
    public static String formatDateTime(long millisecond, DateTimeFormatEnum pattern) {
        return formatDateTime(millisecond, pattern, zoneId);
    }

    /**
     * Format date time string.
     *
     * @param millisecond the millisecond
     * @param pattern     the pattern
     * @param zoneId      the zone id
     * @return the string
     */
    public static String formatDateTime(long millisecond, DateTimeFormatEnum pattern, ZoneId zoneId) {
        if (millisecond <= 0 || null == pattern) {
            return null;
        }
        Instant instant = Instant.ofEpochMilli(millisecond);
        return DateTimeFormatter.ofPattern(pattern.getPattern()).withZone(zoneId).format(instant);
    }

    /**
     * Format date time string.
     *
     * @param millisecond the millisecond
     * @param pattern     the pattern
     * @return the string
     */
    public static String formatDateTime(long millisecond, DateFormatEnum pattern) {
        if (millisecond <= 0 || null == pattern) {
            return null;
        }
        Instant instant = Instant.ofEpochMilli(millisecond);
        return DateTimeFormatter.ofPattern(pattern.getPattern()).withZone(zoneId).format(instant);
    }

    /**
     * Format date time string.
     *
     * @param localDateTime the local date time
     * @param pattern       the pattern
     * @return the string
     */
    public static String formatDateTime(LocalDateTime localDateTime, DateTimeFormatEnum pattern) {
        if (null == localDateTime || null == pattern) {
            return null;
        }
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern.getPattern()));
    }

    /**
     * Format date time string.
     *
     * @param localDateTime the local date time
     * @param pattern       the pattern
     * @return the string
     */
    public static String formatDateTime(LocalDateTime localDateTime, DateFormatEnum pattern) {
        if (null == localDateTime || null == pattern) {
            return null;
        }
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern.getPattern()));
    }

    /**
     * Format date time string.
     *
     * @param localDateTime the local date time
     * @param zoneId        the zone id
     * @param pattern       the pattern
     * @return the string
     */
    public static String formatDateTime(LocalDateTime localDateTime, ZoneId zoneId, DateTimeFormatEnum pattern) {
        if (null == localDateTime || null == pattern || null == zoneId) {
            return null;
        }
        return localDateTime.atZone(zoneId).format(DateTimeFormatter.ofPattern(pattern.getPattern()));
    }

    /**
     * Format date time string.
     *
     * @param localDateTime the local date time
     * @return the string
     */
    public static String formatDateTime(LocalDateTime localDateTime) {
        if (null == localDateTime) {
            return null;
        }
        return localDateTime.format(DEFAULT_FORMATTER);
    }

    /**
     * Format date time string.
     *
     * @param localDate         the local date
     * @param dateTimeFormatter the date time formatter
     * @return the string
     */
    public static String formatDateTime(LocalDate localDate, DateTimeFormatter dateTimeFormatter) {
        if (null == localDate || null == dateTimeFormatter) {
            return null;
        }
        return localDate.format(dateTimeFormatter);
    }

    /**
     * Format date string.
     *
     * @param localDateTime the local date time
     * @return the string
     */
    public static String formatDate(LocalDateTime localDateTime) {
        if (null == localDateTime) {
            return null;
        }
        return localDateTime.format(DateTimeFormatter.ofPattern(DateFormatEnum.DATE.getPattern()));
    }

    /**
     * Format date string.
     *
     * @param localDate the local date
     * @return the string
     */
    public static String formatDate(LocalDate localDate) {
        if (null == localDate) {
            return null;
        }
        return localDate.format(DateTimeFormatter.ofPattern(DateFormatEnum.DATE.getPattern()));
    }

    /**
     * Format date string.
     *
     * @param millisecond the millisecond
     * @return the string
     */
    public static String formatDate(Long millisecond) {
        if (null == millisecond || millisecond == 0) {
            return null;
        }
        Instant instant = Instant.ofEpochMilli(millisecond);
        return DateTimeFormatter.ofPattern(DateFormatEnum.DATE.getPattern()).withZone(zoneId).format(instant);
    }

    /**
     * Parse to date date.
     *
     * @param time the time
     * @return the date
     */
    public static Date parseToDate(String time) {
        if (StringUtils.isBlank(time)) {
            return null;
        }
        if (RulesUtil.checkDateTime(time, DateTimeFormatEnum.DEFAULT)) {
            LocalDateTime localDateTime = parseToLocalDateTime(time, DateTimeFormatEnum.DEFAULT);
            if (null != localDateTime) {
                return Date.from(localDateTime.atZone(zoneId).toInstant());
            }
        }
        return null;
    }

    /**
     * To date date.
     *
     * @param localDateTime the local date time
     * @return the date
     */
    public static Date toDate(LocalDateTime localDateTime) {
        if (null == localDateTime) {
            return null;
        }
        return Date.from(localDateTime.atZone(zoneId).toInstant());
    }

    /**
     * Parse to date date.
     *
     * @param time               the time
     * @param dateTimeFormatEnum the date time format enum
     * @return the date
     */
    public static Date parseToDate(String time, DateTimeFormatEnum dateTimeFormatEnum) {
        if (StringUtils.isBlank(time) || null == dateTimeFormatEnum) {
            return null;
        }
        if (RulesUtil.checkDateTime(time, dateTimeFormatEnum)) {
            LocalDateTime localDateTime = parseToLocalDateTime(time, dateTimeFormatEnum);
            if (null != localDateTime) {
                return Date.from(localDateTime.atZone(zoneId).toInstant());
            }
        }
        return null;
    }

    /**
     * Parse to local date time local date time.
     *
     * @param time               the time
     * @param dateTimeFormatEnum the date time format enum
     * @return the local date time
     */
    public static LocalDateTime parseToLocalDateTime(String time, DateTimeFormatEnum dateTimeFormatEnum) {
        if (StringUtils.isBlank(time) || null == dateTimeFormatEnum) {
            return null;
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimeFormatEnum.getPattern());
        return LocalDateTime.parse(time, dateTimeFormatter);
    }

    /**
     * Parse to zoned date time zoned date time.
     *
     * @param time               the time
     * @param dateTimeFormatEnum the date time format enum
     * @return the zoned date time
     */
    public static ZonedDateTime parseToZonedDateTime(String time, DateTimeFormatEnum dateTimeFormatEnum) {
        if (StringUtils.isBlank(time) || null == dateTimeFormatEnum) {
            return null;
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimeFormatEnum.getPattern());
        return ZonedDateTime.parse(time, dateTimeFormatter);
    }

    /**
     * Parse to local date local date.
     *
     * @param time               the time
     * @param dateTimeFormatEnum the date time format enum
     * @return the local date
     */
    public static LocalDate parseToLocalDate(String time, DateFormatEnum dateTimeFormatEnum) {
        if (StringUtils.isBlank(time) || null == dateTimeFormatEnum) {
            return null;
        }
        if (dateTimeFormatEnum == DateFormatEnum.DATE_MONTH_SIMPLE) {
            TemporalAccessor parse = YYYY_MM_FORMATTER.parse(time);
            int year = parse.get(ChronoField.YEAR);
            int month = parse.get(ChronoField.MONTH_OF_YEAR);
            return LocalDate.of(year, month, 1);
        } else {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimeFormatEnum.getPattern());
            return LocalDate.parse(time, dateTimeFormatter);
        }
    }

    /**
     * Format date time local date time.
     *
     * @param localDateTime the local date time 获取据时间戳（1970-01-01）的毫秒数
     * @return the local date time
     */
    public static long getMillisecond(LocalDateTime localDateTime) {
        if (null == localDateTime) {
            return 0;
        }
        return localDateTime.atZone(zoneId).toInstant().toEpochMilli();
    }

    /**
     * Gets millisecond.
     *
     * @param localDate the local date
     * @return the millisecond
     */
    public static long getMillisecond(LocalDate localDate) {
        if (null == localDate) {
            return 0;
        }
        return LocalDateTime.of(localDate, LocalTime.MIN).atZone(zoneId).toInstant().toEpochMilli();
    }

    /**
     * Local date now string.
     *
     * @param dateTimeFormatEnum the date time format enum
     * @return the string
     */
    private static String formatNow(DateFormatEnum dateTimeFormatEnum) {
        if (null == dateTimeFormatEnum) {
            log.info("In method  localDateNow of dateTimeFormatEnum is null");
            return null;
        }
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(dateTimeFormatEnum.getPattern()));
    }

    /**
     * Format now string.
     *
     * @param dateTimeFormatEnum the date time format enum
     * @return the string
     */
    private static String formatNow(DateTimeFormatEnum dateTimeFormatEnum) {
        if (null == dateTimeFormatEnum) {
            log.info("In method  localDateNow of dateTimeFormatEnum is null");
            return null;
        }
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(dateTimeFormatEnum.getPattern()));
    }

    /**
     * Local date now string.
     *
     * @return the string
     */
    public static String localDateNow() {
        return formatNow(DateFormatEnum.DATE);
    }

    /**
     * Local date now string.
     *
     * @return the string
     */
    public static String localDateTimeNow() {
        return formatNow(DateTimeFormatEnum.DEFAULT);
    }

    /**
     * Now date.
     *
     * @return the date
     */
    public static Date now() {
        return new Date(System.currentTimeMillis());
    }

    /**
     * Format es time string.
     *
     * @param date the date
     * @return the string
     */
    public static String formatESTime(Date date) {
        if (null == date) {
            return null;
        }
        ZonedDateTime zonedDateTime = toZonedDateTime(date);
        String pattern = DateTimeFormatEnum.DATE_OPTIONAL_TIME.getPattern();
        return zonedDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * To floor es time string. 将字符串符合"yyyy-MM-dd","yyyyMMdd","yyyy-MM-dd HH:mm:ss","yyyyMMdd
     * HH:mm:ss"的字符串 <p> 格式转为yyyy-MM-dd'T'HH:mm:ss.SSS+0800 <p> <p> 20170205 12:12:12 ===>
     * 2017-02-05T12:12:12.000+0800 2017-02-05 12:12:12 ===> 2017-02-05T12:12:12.000+0800 2017-02-05
     * ===> 2017-02-05T00:00:00.000+0800
     *
     * @param time the time
     * @return the string
     */
    public static String toFloorESTime(String time) {
        String esTime = toESTime(time);
        if (StringUtils.isNotBlank(esTime)) {
            return esTime;
        } else {
            String time1 = toStartESTimeForLocalDate(time);
            return StringUtils.isNotBlank(time1) ? time1 : null;
        }
    }

    /**
     * To ceiling es time string.
     * <p>
     * 将字符串符合"yyyy-MM-dd","yyyyMMdd","yyyy-MM-dd HH:mm:ss","yyyyMMdd
     * HH:mm:ss"的字符串
     * <p>
     * 格式转为yyyy-MM-dd'T'HH:mm:ss.SSS+0800
     * <p>
     * 20170205 12:12:12 ===>2017-02-05T12:12:12.000+0800
     * <p>
     * 2017-02-05 12:12:12 ===> 2017-02-05T12:12:12.000+0800
     * <p>
     * 2017-02-05 ===> 2017-02-05T23:59:59.000+0800
     *
     * @param time the time
     * @return the string
     */
    public static String toCeilingESTime(String time) {
        String esTime = toESTime(time);
        if (StringUtils.isNotBlank(esTime)) {
            return esTime;
        } else {
            String time1 = toEndESTimeForLocalDate(time);
            return StringUtils.isNotBlank(time1) ? time1 : null;
        }
    }

    /**
     * time属于LocalDateTime的，时间格式转为yyyy-MM-dd'T'HH:mm:ss.SSS+0800
     *
     * @param time time
     * @return String string
     */
    public static String toESTime(String time) {
        if (StringUtils.isBlank(time)) {
            return null;
        }
        if (RulesUtil.checkDateTime(time, DateTimeFormatEnum.DEFAULT)) {
            LocalDateTime localDateTime = parseToLocalDateTime(time, DateTimeFormatEnum.DEFAULT);
            return toESTime(localDateTime);
        } else if (RulesUtil.checkDateTime(time, DateTimeFormatEnum.DATE_WITHOUT_LINK_TIME)) {
            LocalDateTime localDateTime = parseToLocalDateTime(time, DateTimeFormatEnum.DATE_WITHOUT_LINK_TIME);
            return toESTime(localDateTime);
        } else if (RulesUtil.checkDateTime(time, DateTimeFormatEnum.DATETIME_WITHOUT_LINK)) {
            LocalDateTime localDateTime = parseToLocalDateTime(time, DateTimeFormatEnum.DATETIME_WITHOUT_LINK);
            return toESTime(localDateTime);
        }
        return null;
    }

    /**
     * To es time string.
     *
     * @param date the date
     * @return the string
     */
    public static String toESTime(Date date) {
        if (null == date) {
            return null;
        }
        Instant instant = date.toInstant();
        return instant.atZone(zoneId).format(ES_DATE_TIME);
    }

    /**
     * To es time string.
     *
     * @param milliSeconds the milli seconds
     * @return the string
     */
    public static String toESTime(long milliSeconds) {
        if (milliSeconds == 0) {
            return null;
        }
        Instant instant = Instant.ofEpochMilli(milliSeconds);
        return instant.atZone(zoneId).format(ES_DATE_TIME);
    }

    /**
     * time属于LocalDateTime的，时间格式转为yyyy-MM-dd'T'HH:mm:ss.SSS+0800
     *
     * @param time time
     * @return String
     */
    private static String toStartESTimeForLocalDate(String time) {
        if (StringUtils.isBlank(time)) {
            return null;
        }
        if (RulesUtil.checkDateTime(time, DateFormatEnum.DATE)) {
            LocalDate localDate = parseToLocalDate(time, DateFormatEnum.DATE);
            return toStartESTime(localDate);
        } else if (RulesUtil.checkDateTime(time, DateFormatEnum.DATE_WITHOUT_LINK)) {
            LocalDate localDate = parseToLocalDate(time, DateFormatEnum.DATE_WITHOUT_LINK);
            return toStartESTime(localDate);
        }
        return null;
    }

    /**
     * time属于LocalDate的，时间格式转为yyyy-MM-dd'T'HH:mm:ss.SSS+0800
     *
     * @param time time
     * @return String
     */
    private static String toEndESTimeForLocalDate(String time) {
        if (StringUtils.isBlank(time)) {
            return null;
        }
        if (RulesUtil.checkDateTime(time, DateFormatEnum.DATE)) {
            LocalDate localDate = parseToLocalDate(time, DateFormatEnum.DATE);
            return toEndESTime(localDate);
        } else if (RulesUtil.checkDateTime(time, DateFormatEnum.DATE_WITHOUT_LINK)) {
            LocalDate localDate = parseToLocalDate(time, DateFormatEnum.DATE_WITHOUT_LINK);
            return toEndESTime(localDate);
        }
        return null;
    }

    /**
     * @param time time
     * @return String
     */
    private static String toStartESTime(String time) {
        if (StringUtils.isBlank(time)) {
            return null;
        }
        if (RulesUtil.checkDateTime(time, DateTimeFormatEnum.DEFAULT)) {
            LocalDateTime localDateTime = parseToLocalDateTime(time, DateTimeFormatEnum.DEFAULT);
            return toStartESTime(localDateTime);
        } else if (RulesUtil.checkDateTime(time, DateTimeFormatEnum.DATE_WITHOUT_LINK_TIME)) {
            LocalDateTime localDateTime = parseToLocalDateTime(time, DateTimeFormatEnum.DATE_WITHOUT_LINK_TIME);
            return toStartESTime(localDateTime);
        } else {
            String time1 = toStartESTimeForLocalDate(time);
            return StringUtils.isNotBlank(time1) ? time1 : null;
        }
    }

    /**
     * @param time time
     * @return String
     */
    private static String toEndESTime(String time) {
        if (StringUtils.isBlank(time)) {
            return null;
        }
        if (RulesUtil.checkDateTime(time, DateTimeFormatEnum.DEFAULT)) {
            LocalDateTime localDateTime = parseToLocalDateTime(time, DateTimeFormatEnum.DEFAULT);
            return toEndESTime(localDateTime);
        } else if (RulesUtil.checkDateTime(time, DateTimeFormatEnum.DATE_WITHOUT_LINK_TIME)) {
            LocalDateTime localDateTime = parseToLocalDateTime(time, DateTimeFormatEnum.DATE_WITHOUT_LINK_TIME);
            return toEndESTime(localDateTime);
        } else {
            String time1 = toEndESTimeForLocalDate(time);
            return StringUtils.isNotBlank(time1) ? time1 : null;
        }
    }

    /**
     * 把localDateTime时间格式化为：yyyy-MM-dd'T'HH:mm:ss.SSS+0800
     *
     * @param localDateTime localDateTime
     * @return String string
     */
    public static String toESTime(LocalDateTime localDateTime) {
        if (null == localDateTime) {
            return null;
        }
        return localDateTime.atZone(ZoneId.systemDefault()).format(ES_DATE_TIME);
    }

    /**
     * 把localDateTime时间格式化为：yyyy-MM-dd'T'HH:mm:ss.SSS+0800
     *
     * @param localDateTime localDateTime
     * @return String
     */
    private static String toStartESTime(LocalDateTime localDateTime) {
        if (null == localDateTime) {
            return null;
        }
        ZonedDateTime zonedDateTime = localDateTime.with(LocalTime.MIN).atZone(zoneId);
        return zonedDateTime.format(ES_DATE_TIME);
    }

    /**
     * 把localDateTime时间格式化为：yyyy-MM-dd'T'HH:mm:ss.SSS+0800
     *
     * @param localDateTime localDateTime
     * @return String
     */
    private static String toEndESTime(LocalDateTime localDateTime) {
        if (null == localDateTime) {
            return null;
        }
        ZonedDateTime zonedDateTime = localDateTime.with(LocalTime.MAX).atZone(zoneId);
        return zonedDateTime.format(ES_DATE_TIME);
    }

    /**
     * localDate：yyyy-MM-dd'T'HH:mm:ss.SSS+0800
     *
     * @param localDate localDate
     * @return String string
     */
    public static String toStartESTime(LocalDate localDate) {
        if (null == localDate) {
            return null;
        }
        ZonedDateTime zonedDateTime = localDate.atTime(LocalTime.MIN).atZone(ZoneId.systemDefault());
        return zonedDateTime.format(ES_DATE_TIME);
    }

    /**
     * localDate：yyyy-MM-dd'T'HH:mm:ss.SSS+0800
     *
     * @param localDate localDate
     * @return String string
     */
    public static String toEndESTime(LocalDate localDate) {
        if (null == localDate) {
            return null;
        }
        ZonedDateTime zonedDateTime = localDate.atTime(LocalTime.MAX).atZone(ZoneId.systemDefault());
        return zonedDateTime.format(ES_DATE_TIME);
    }

    /**
     * 把时间格式化为：yyyy-MM-dd'T'00:00:00.000+0800
     *
     * @param timeMillis timeMillis
     * @return String
     */
    private static String toStartESTime(long timeMillis) {
        if (timeMillis == 0L) {
            return null;
        }
        Instant instant = Instant.ofEpochMilli(timeMillis).with(LocalTime.MIN);
        return ES_DATE_TIME.withZone(zoneId).format(instant);
    }

    /**
     * In same month of year boolean.判断两个时间是否是在同年同月
     *
     * @param src1 the src 1
     * @param src2 the src 2
     * @return the boolean
     */
    public static boolean inSameMonthOfYear(LocalDateTime src1, LocalDateTime src2) {
        if (null == src1 || null == src2) {
            return false;
        }
        int year1 = src1.getYear();
        int year2 = src2.getYear();
        if (year1 == 0 || year2 == 0 || year1 != year2) {
            return false;
        }
        int monthValue1 = src1.getMonthValue();
        int monthValue2 = src2.getMonthValue();
        return !(monthValue1 == 0 || monthValue2 == 0 || monthValue1 != monthValue2);
    }

    /**
     * In same week of year boolean.判断两个时间是否是在同年同周
     *
     * @param src1 the src 1
     * @param src2 the src 2
     * @return the boolean
     */
    public static boolean inSameWeekOfYear(LocalDateTime src1, LocalDateTime src2) {
        if (null == src1 || null == src2) {
            return false;
        }
        int year1 = src1.getYear();
        int year2 = src2.getYear();
        if (year1 == 0 || year2 == 0 || year1 != year2) {
            return false;
        }
        int week1 = src1.get(weekOfWeekBasedYear);
        int week2 = src2.get(weekOfWeekBasedYear);
        return !(week1 == 0 || week2 == 0 || week1 != week2);
    }

    /**
     * First date time of month local date time.
     * <p>
     * 获取当月1号的零点时间
     * <p>
     * 比如当前时间2020-06-08 12:12:12 ==> 2020-06-01 00:00:00
     *
     * @return the local date time
     */
    public static LocalDateTime firstDateTimeOfMonth() {
        return LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIN);
    }

    /**
     * Is gte boolean.updateTime是否大于等于startTime
     *
     * @param startTime  the start time
     * @param updateTime the update time
     * @return the boolean
     */
    public static boolean isGTE(LocalDateTime startTime, LocalDateTime updateTime) {
        return null != startTime && null != updateTime
            && (updateTime.isAfter(startTime) || updateTime.isEqual(startTime));
    }

    /**
     * Gap long.
     *
     * @param start      the start
     * @param end        the end
     * @param chronoUnit the chrono unit
     * @return the long
     */
    public static long gapSeconds(LocalDate start, LocalDate end, ChronoUnit chronoUnit) {
        if (null == start || null == end) {
            return 0;
        }
        Period age = Period.between(start, end);
        return age.get(chronoUnit);
    }

    /**
     * Gap long.
     *
     * @param start the start
     * @param end   the end
     * @return the long
     */
    public static long gapSeconds(LocalDateTime start, LocalDateTime end) {
        if (null == start || null == end) {
            return 0;
        }
        Duration age = Duration.between(start, end);
        return age.get(ChronoUnit.SECONDS);
    }
}



