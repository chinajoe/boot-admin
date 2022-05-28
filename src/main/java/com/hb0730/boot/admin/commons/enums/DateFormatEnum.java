package com.hb0730.boot.admin.commons.enums;

import lombok.Getter;

/**
 * DateFormatEnum
 * 格式化日期（不含有[小时分秒]10：10：10）的枚举
 *
 * @author qiaojinfeng3
 * @date 2019 /9/25 12:26
 */
public enum DateFormatEnum {
    /**
     * Date date time format enum.
     */
    DATE("yyyy-MM-dd"),
    /**
     * Date without link date time format enum.
     */
    DATE_WITHOUT_LINK("yyyyMMdd"),
    /**
     * Date simple date time format enum.
     */
    DATE_SIMPLE("yyyy-M-d"),
    /**
     * Date month simple date format enum.
     */
    DATE_MONTH_SIMPLE("yyyy-MM");
    /**
     * pattern
     */
    @Getter
    private String pattern;

    DateFormatEnum(String pattern) {
        this.pattern = pattern;
    }
    }
