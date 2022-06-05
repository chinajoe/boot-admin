package com.hb0730.boot.admin.commons.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * FEBS工具类
 *
 * @author JDT
 */
@Slf4j
public abstract class FebsUtil {
    public static final Set<String> IMAGE_SET = new HashSet<>();
    public static final Set<String> AUDIO_SET = new HashSet<>();
    public static final Set<String> VIDEO_SET = new HashSet<>();

    static {
        // image
        IMAGE_SET.add("bmp");
        IMAGE_SET.add("gif");
        IMAGE_SET.add("ico");
        IMAGE_SET.add("jpeg");
        IMAGE_SET.add("jpg");
        IMAGE_SET.add("png");
        IMAGE_SET.add("svg");
        IMAGE_SET.add("tif");
        IMAGE_SET.add("webp");
        // audio
        AUDIO_SET.add("aac");
        AUDIO_SET.add("mid");
        AUDIO_SET.add("midi");
        AUDIO_SET.add("mp3");
        AUDIO_SET.add("oga");
        AUDIO_SET.add("opus");
        AUDIO_SET.add("wav");
        // video
        VIDEO_SET.add("3g2");
        VIDEO_SET.add("3gp");
        VIDEO_SET.add("avi");
        VIDEO_SET.add("mpeg");
        VIDEO_SET.add("ogv");
        VIDEO_SET.add("ts");
        VIDEO_SET.add("webm");
        VIDEO_SET.add("mp4");
    }

    /**
     * Print system up banner.
     *
     * @param environment the environment
     */
    public static void printSystemUpBanner(Environment environment) {
        String banner = "\n-----------------------------------------\n" +
            "服务启动成功，时间：" + LocalDateTime.now() + "\n" +
            "服务名称：" + environment.getProperty("spring.application.name") + "\n" +
            "端口号：" + environment.getProperty("server.port") + "\n" +
            "-----------------------------------------";
        log.info(banner);
    }

    /**
     * Is image boolean.
     *
     * @param extension the extension
     * @return the boolean
     */
    public static boolean isImage(String extension) {
        return IMAGE_SET.contains(extension.toLowerCase(Locale.ROOT));
    }

    /**
     * Is audio boolean.
     *
     * @param extension the extension
     * @return the boolean
     */
    public static boolean isAudio(String extension) {
        return AUDIO_SET.contains(extension.toLowerCase(Locale.ROOT));
    }

    /**
     * Is video boolean.
     *
     * @param extension the extension
     * @return the boolean
     */
    public static boolean isVideo(String extension) {
        return VIDEO_SET.contains(extension.toLowerCase(Locale.ROOT));
    }
}
