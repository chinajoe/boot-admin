package com.hb0730.boot.admin.project.course.prepare.service.impl;

import com.hb0730.boot.admin.commons.utils.OkHttpUtil;
import com.hb0730.boot.admin.configuration.properties.BootAdminProperties;
import com.hb0730.boot.admin.project.course.orchestrate.dto.CourseContentAddDTO;
import com.hb0730.boot.admin.project.course.orchestrate.service.TCourseService;
import com.hb0730.boot.admin.project.course.prepare.dto.PrepareConstant;
import com.hb0730.boot.admin.project.course.prepare.service.TPrepareService;
import com.hb0730.commons.lang.io.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * The interface T prepare service.
 *
 * @description:
 * @author: qiaojinfeng3
 * @date: 2022 -06-06
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TPrepareServiceImpl implements TPrepareService {
    private final TCourseService tCourseService;
    private final BootAdminProperties bootAdminProperties;
    private final Environment environment;
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    public void setThreadPoolTaskExecutor(@Qualifier(value = "threadPoolTaskExecutor") ThreadPoolTaskExecutor threadPoolTaskExecutor) {
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
    }

    @Override
    public boolean content2Image(CourseContentAddDTO params) throws IOException {
        String htmlContent = params.getHtmlContent();
        String exportHtml =
            PrepareConstant.EXPORT_TEMPLATE_PREFIX + htmlContent + PrepareConstant.EXPORT_TEMPLATE_SUFFIX;
        String uuid = UUID.randomUUID().toString();
        String exportFileName = uuid + ".html";
        URL url = ResourceUtils.getURL(ResourceUtils.CLASSPATH_URL_PREFIX + "static/");
        File file = ResourceUtils.getFile(url + exportFileName);
        FileUtils.writeString(file, exportHtml, StandardCharsets.UTF_8.name());

        /* export */
        String doctronConvertEndpoint = bootAdminProperties.getDoctronConvertEndpoint();
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("u", "doctron");
        paramsMap.put("p", "lampnick");
        paramsMap.put("url", doctronConvertEndpoint + ":" + environment.getProperty("server.port") + "/" + exportFileName);
        Optional<Response> responseOptional = OkHttpUtil.get(doctronConvertEndpoint + ":9090" + "/convert/html2image", paramsMap);
        try {
            if (responseOptional.isPresent()) {
                log.info("content2Image--doctron convert success.");
                Response response = responseOptional.get();
                ResponseBody responseBody = response.body();
                assert responseBody != null;
                File imageFile = new File(System.getProperty("user.dir") + uuid + ".png");
                FileUtils.writeByStream(imageFile, responseBody.byteStream(), true);

                /* upload */
                tCourseService.upload("/export/", Collections.singletonList(imageFile));
                return true;
            }
        } catch (Exception e) {
            log.error("content2Image--", e);
        } finally {
            FileUtils.deleteFile(file);
        }
        return false;
    }
}
