package com.hb0730.boot.admin.commons.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;

import java.time.LocalDateTime;

/**
 * FEBS工具类
 *
 * @author JDT
 */
@Slf4j
public abstract class FebsUtil {


    public static void printSystemUpBanner(Environment environment) {
        String banner = "\n-----------------------------------------\n" +
                "服务启动成功，时间：" + LocalDateTime.now() + "\n" +
                "服务名称：" + environment.getProperty("spring.application.name") + "\n" +
                "端口号：" + environment.getProperty("server.port") + "\n" +
                "-----------------------------------------";
        log.info(banner);
    }


}
