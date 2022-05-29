package com.hb0730.boot.admin.configuration;

import com.hb0730.boot.admin.configuration.properties.BootAdminProperties;
import com.hb0730.boot.admin.configuration.properties.CosProperties;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.transfer.TransferManager;
import com.qcloud.cos.transfer.TransferManagerConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The type Cos configuration.
 */
@Configuration
@RequiredArgsConstructor
public class CosConfiguration {
    private final BootAdminProperties bootAdminProperties;

    /**
     * Cos client .
     *
     * @return the cos client
     */
    @Bean
    public COSClient cosClient() {
        CosProperties cosProperties = bootAdminProperties.getCos();
        String secretId = cosProperties.getSecretId();
        String secretKey = cosProperties.getSecretKey();
        String region = cosProperties.getRegion();
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // ClientConfig 中包含了后续请求 COS 的客户端设置：
        ClientConfig clientConfig = new ClientConfig();
        // 设置 bucket 的地域
        // COS_REGION 请参照 https://cloud.tencent.com/document/product/436/6224
        clientConfig.setRegion(new Region(region));
        // 设置 socket 读取超时，默认 30s
        clientConfig.setSocketTimeout(30 * 1000);
        // 设置建立连接超时，默认 30s
        clientConfig.setConnectionTimeout(30 * 1000);
        // 生成 cos 客户端。
        return new COSClient(cred, clientConfig);
    }

    /**
     * Create transfer manager.
     *
     * @param cosClient the cos client
     * @return the transfer manager
     */
    @Bean
    public TransferManager createTransferManager(COSClient cosClient) {
        ExecutorService threadPool = Executors.newFixedThreadPool(32);
        // 传入一个 thread pool, 若不传入线程池，默认 TransferManager 中会生成一个单线程的线程池。
        TransferManager transferManager = new TransferManager(cosClient, threadPool);
        // 设置高级接口的配置项
        // 分块上传阈值和分块大小分别为 5MB 和 1MB
        TransferManagerConfiguration transferManagerConfiguration = new TransferManagerConfiguration();
        transferManagerConfiguration.setMultipartUploadThreshold(5 * 1024 * 1024);
        transferManagerConfiguration.setMinimumUploadPartSize(1024 * 1024);
        transferManager.setConfiguration(transferManagerConfiguration);
        return transferManager;
    }
}
