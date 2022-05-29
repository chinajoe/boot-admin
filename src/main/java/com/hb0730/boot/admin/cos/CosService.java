package com.hb0730.boot.admin.cos;

import com.hb0730.boot.admin.configuration.properties.BootAdminProperties;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.http.HttpMethodName;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.transfer.TransferManager;
import com.qcloud.cos.transfer.Upload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URL;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * The type Cos service.
 */
@Service
@RequiredArgsConstructor
public class CosService {
    private final BootAdminProperties bootAdminProperties;
    private final TransferManager transferManager;
    private final COSClient cosClient;

    /**
     * Upload upload.
     *
     * @param key        the key
     * @param sourceFile the source file
     * @return the upload
     */
    public Upload upload(String key, String sourceFile) {
        String bucketName = bootAdminProperties.getCos().getBucketName();
        File localFile = new File(sourceFile);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
        return transferManager.upload(putObjectRequest);
    }

    /**
     * Generate presigned url string.
     *
     * @param key      the key
     * @param duration the duration
     * @return the string
     */
    public String generateResignedUrl(String key, Duration duration) {
        String bucketName = bootAdminProperties.getCos().getBucketName();
        // 这里设置签名在半个小时后过期
        Date expirationDate = new Date(System.currentTimeMillis() + duration.toMillis());
        // 填写本次请求的参数，需与实际请求相同，能够防止用户篡改此签名的 HTTP 请求的参数
        Map<String, String> params = new HashMap<>();
        params.put("qk-p1", UUID.randomUUID().toString());
        // 填写本次请求的头部，需与实际请求相同，能够防止用户篡改此签名的 HTTP 请求的头部
        Map<String, String> headers = new HashMap<>();
        headers.put("qk-h1", UUID.randomUUID().toString());
        // 请求的 HTTP 方法，上传请求用 PUT，下载请求用 GET，删除请求用 DELETE
        HttpMethodName method = HttpMethodName.GET;
        URL url = cosClient.generatePresignedUrl(bucketName, key, expirationDate, method, headers, params);
        return url.toString();
    }

    /**
     * Generate pre signed url string.
     *
     * @param key the key
     * @return the string
     */
    public String generateResignedUrl(String key) {
        return generateResignedUrl(key, Duration.ofHours(1L));
    }
}
