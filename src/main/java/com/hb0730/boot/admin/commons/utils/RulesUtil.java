package com.hb0730.boot.admin.commons.utils;

import com.hb0730.boot.admin.commons.enums.DateFormatEnum;
import com.hb0730.boot.admin.commons.enums.DateTimeFormatEnum;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

/**
 * RulesUtil
 * 集中管理校验规则,工具类
 *
 * @author qiaojinfeng3
 * @date 2019 /5/29 14:59
 */
public class RulesUtil {
    /**
     * 可以输入字母、数字，中文，下划线，中划线，英文点
     */
    private static final String PATTEN_NAME = "^([\\w]|[-/_:]|[.]|[\\u4E00-\\u9FA5])*$";
    /**
     * 非数字开头，可以输入字母、数字，中文，下划线，中划线，英文点
     */
    private static final String NONNUMERIC_START_NAME = "^([A-Za-z]|[\\u4E00-\\u9FA5])([\\w]|[-_]|[.]|[\\u4E00-\\u9FA5])*$";
    /**
     * ip base
     */
    private static final String IPV4_BASE = "((25[0-5]|2[0-4]\\d|[1-9]|[1-9]\\d|[1]\\d\\d)\\.)((25[0-5]|2[0-4]\\d|[0-9]|[1-9]\\d|[1]\\d\\d)\\.){2}(25[0-5]|2[0-4]\\d|[0-9]|[1-9]\\d|[1]\\d\\d)";
    /**
     * IPV4地址
     */
    private static final String PATTERN_IPV4 = "^" + IPV4_BASE + "$";
    /**
     * IPV4/掩码 x.x.x.x/mask（24 <= mask <=32）
     */
    private static final String PATTERN_IPV4_MASK = "^" + IPV4_BASE + "(/(2[4-9]|3[0-2]))?$";
    /**
     * IPV4地址范围 x.x.x.n-m（0<= n <m <=255）
     */
    private static final String PATTERN_IPV4_RANGE = "^((25[0-5]|2[0-4]\\d|[1-9]|[1-9]\\d|[1]\\d\\d)\\.)((25[0-5]|2[0-4]\\d|[0-9]|[1-9]\\d|[1]\\d\\d)\\.){2}(25[0-5]|2[0-4]\\d|[0-9]|[1-9]\\d|[1]\\d\\d)-(25[0-5]|2[0-4]\\d|[0-9]|[1-9]\\d|[1]\\d\\d)$";
    /**
     * 端口
     */
    private static final String PATTERN_PORT = "^([0-9]|[1-9]\\d|[1-9]\\d{2}|[1-9]\\d{3}|[1-5]\\d{4}|6[0-4]\\d{3}|65[0-4]\\d{2}|655[0-2]\\d|6553[0-5])$";
    /**
     * 数字
     */
    private static final String PATTEN_NUMBER = "^([0-9])+$";
    /**
     * 日期格式化类
     */
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * 日期格式化类
     */
    private static final SimpleDateFormat DATETIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * MAC地址
     */
    private static final String PATTERN_MAC_ADDRESS = "^([A-Fa-f0-9]{2}[:|-]){5}[A-Fa-f0-9]{2}$";
    /**
     *
     */
    private static final Pattern NUMBER_PATTERN = Pattern.compile(PATTEN_NUMBER);
    /**
     *
     */
    private static final Pattern MAC_ADDRESS_PATTERN = Pattern.compile(PATTERN_MAC_ADDRESS);
    /**
     *
     */
    private static final Pattern NAME_PATTERN = Pattern.compile(PATTEN_NAME);
    /**
     *
     */
    private static final Pattern IPV4_PATTERN = Pattern.compile(PATTERN_IPV4);
    /**
     *
     */
    private static final Pattern IPV4_MASK_PATTERN = Pattern.compile(PATTERN_IPV4_MASK);
    /**
     *
     */
    private static final Pattern PORT_PATTERN = Pattern.compile(PATTERN_PORT);
    /**
     *
     */
    private static final Pattern IPV4_RANGE_PATTERN = Pattern.compile(PATTERN_IPV4_RANGE);
    /**
     *
     */
    private static final Pattern NONNUMERIC_START_NAME_PATTERN = Pattern.compile(NONNUMERIC_START_NAME);
    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(RulesUtil.class);

    /**
     * Check date boolean.
     *
     * @param date the date
     * @return the boolean
     */
    public static boolean checkDate(String date) {
        return RulesUtil.checkDateTime(date, DateTimeFormatEnum.DEFAULT)
                || RulesUtil.checkDateTime(date, DateTimeFormatEnum.DATE_WITHOUT_LINK_TIME)
                || RulesUtil.checkDateTime(date, DateFormatEnum.DATE)
                || RulesUtil.checkDateTime(date, DateFormatEnum.DATE_WITHOUT_LINK);

    }

    /**
     * Check date time boolean. 判断字符串是否符合时间格式
     *
     * @param dateTime           the date time
     * @param dateTimeFormatEnum the date time format enum
     * @return the boolean
     */
    public static boolean checkDateTime(String dateTime, DateFormatEnum dateTimeFormatEnum) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimeFormatEnum.getPattern());
        try {
            return null != LocalDate.parse(dateTime, dateTimeFormatter);
        } catch (Exception ignored) {
        }
        return false;
    }

    /**
     * Check date time boolean.
     *
     * @param dateTime           the date time
     * @param dateTimeFormatEnum the date time format enum
     * @return the boolean
     */
    public static boolean checkDateTime(String dateTime, DateTimeFormatEnum dateTimeFormatEnum) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimeFormatEnum.getPattern());
        try {
            return null != LocalDateTime.parse(dateTime, dateTimeFormatter);
        } catch (Exception ignored) {
        }
        return false;
    }

    /**
     * Check number boolean.
     *
     * @param number the number
     * @return the boolean
     */
    public static boolean checkNumber(String number) {
        return StringUtils.isBlank(number) || NUMBER_PATTERN.matcher(number.trim()).matches();
    }

    /**
     * Check mac boolean.
     *
     * @param mac the mac
     * @return the boolean
     */
    public static boolean checkMac(String mac) {
        return StringUtils.isBlank(mac) || MAC_ADDRESS_PATTERN.matcher(mac.trim()).matches();
    }

    /**
     * Check name boolean.
     *
     * @param name the name
     * @return the boolean
     */
    public static boolean checkName(String name) {
        return StringUtils.isBlank(name) || NAME_PATTERN.matcher(name.trim()).matches();
    }

    /**
     * Check non numeric started name boolean.
     *
     * @param name the name
     * @return the boolean
     */
    public static boolean checkNonNumericStartedName(String name) {
        return StringUtils.isBlank(name) || NONNUMERIC_START_NAME_PATTERN.matcher(name.trim()).matches();
    }

    /**
     * Check ipv 4 boolean.
     *
     * @param ipv4 the ipv 4
     * @return the boolean
     */
    public static boolean checkIpv4(String ipv4) {
        return StringUtils.isBlank(ipv4) || IPV4_PATTERN.matcher(ipv4.trim()).matches();
    }

    /**
     * Check ipv 4 with mask boolean.
     *
     * @param ipv4 the ipv 4
     * @return the boolean
     */
    public static boolean checkIpv4WithMask(String ipv4) {
        return StringUtils.isBlank(ipv4) || IPV4_MASK_PATTERN.matcher(ipv4.trim()).matches();
    }

    /**
     * Check ipv 4 range boolean.
     *
     * @param ipv4 the ipv 4
     * @return the boolean
     */
    public static boolean checkIpv4Range(String ipv4) {
        if (StringUtils.isBlank(ipv4)) {
            return true;
        }
        String trimmedIpv4 = ipv4.trim();
        if (IPV4_RANGE_PATTERN.matcher(trimmedIpv4).matches()) {
            int lastIndexOf = trimmedIpv4.lastIndexOf(".");
            if (lastIndexOf > -1 && lastIndexOf < trimmedIpv4.length()) {
                String substring = trimmedIpv4.substring(lastIndexOf + 1);
                if (StringUtils.isNotBlank(substring)) {
                    String[] split = substring.split("-");
                    if (split.length == 2) {
                        int n;
                        int m;
                        try {
                            n = Integer.parseInt(split[0]);
                            m = Integer.parseInt(split[1]);
                        } catch (NumberFormatException e) {
                            logger.info("", e);
                            return false;
                        }
                        return checkIpv4Within254(split[0]) && checkIpv4Within254(split[1])
                                && m >= n;
                    }
                }

            }

        }
        return false;
    }

    /**
     * Check ipv 4 within 255 boolean.
     *
     * @param ipv4 the ipv 4
     * @return the boolean
     */
    private static boolean checkIpv4Within254(String ipv4) {
        try {
            if (StringUtils.isNotBlank(ipv4)) {
                int i = Integer.parseInt(ipv4);
                return i <= 254 && i >= 1;
            }
        } catch (NumberFormatException e) {
            logger.info("", e);
            return false;
        }
        return false;

    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        // System.out.println(checkName("123- _.J你好+"));
        // System.out.println(checkIpv4("1.0.0.0"));
        // System.out.println(checkIpv4Range("1.0.0.0-255"));
        System.out.println(checkIpv4WithMask("111.62.70.87"));

    }

}
