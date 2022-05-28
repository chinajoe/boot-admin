package com.hb0730.boot.admin.commons.enums;

import lombok.Getter;

/**
 * DateTimeFormatEnum
 * 格式化时间的枚举
 *
 * @author qiaojinfeng3
 * @date 2019 /5/29 14:53
 */
public enum DateTimeFormatEnum {
    /**
     * The Default.
     */
    DEFAULT("yyyy-MM-dd HH:mm:ss"),
    /**
     * Time date time format enum.
     */
    TIME("HH:mm:ss"),
    /**
     * Strict hour minute second fraction date time format enum.
     */
    STRICT_HOUR_MINUTE_SECOND_FRACTION("HH:mm:ss.SSS"),
    /**
     * Datetime without link date time format enum.
     */
    DATETIME_WITHOUT_LINK("yyyyMMddHHmmss"),
    /**
     * The Date without link time.
     */
    DATE_WITHOUT_LINK_TIME("yyyyMMdd HH:mm:ss"),
    /**
     * Date optional time date format enum.
     */
    DATE_OPTIONAL_TIME("yyyy-MM-dd'T'HH:mm:ss.SSSZZ"),
    /**
     * short year
     */
    SHORT_DATE_SHORT_TIME("yy-MM-dd HH:mm");
    /**
     * pattern
     */
    @Getter
    private String pattern;

    /**
     * @param pattern pattern
     */
    DateTimeFormatEnum(String pattern) {
        this.pattern = pattern;
    }
}
