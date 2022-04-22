package com.hb0730.boot.admin.xiaoe;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hb0730.boot.admin.commons.utils.OkHttpUtil;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicSessionCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.UploadResult;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.transfer.TransferManager;
import com.qcloud.cos.transfer.TransferManagerConfiguration;
import com.qcloud.cos.transfer.Upload;
import okhttp3.Response;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @description:
 * @author: qiaojinfeng3
 * @date: 2022/4/22 23:12
 */
public class UploadTest {
    @Test
    public void addGroupTest() {
        String url = "https://admin.xiaoe-tech.com/api/material/category/update";
        try {
            final JSONObject content = new JSONObject();
            content.put("app_id", "appzFG9dgU09033");
            content.put("type", 1);
            content.put("id", 0);
            content.put("parent_id", 0);
            content.put("name", "qq-2022");
            final HashMap<String, String> headers = new HashMap<>();
            headers.put("Cookie", "appsc=appzFG9dgU09033; b_user_token=token_6262be1722825uM343cgUKlTrndJ0m05O; cookie_is_signed=1; shop_type=0m05O; with_app_id=appzFG9dgU09033; laravel_session=eyJpdiI6IkZIT0twa3B3UkNqbjhZSmhLWENnT0E9PSIsInZhbHVlIjoiVzV0M2ZwQkNZWWFvczRRbmcrbDRtaHg2TmxWUXVHQTZUcWpxUWxkbSszRDRJQUZ3ZmYyVWZUSVVGQmpkaVZDQ1RaWFZaWjJcLzRkK2NVWlVXSkt6U3F3PT0iLCJtYWMiOiI4NWQ3NDNkZDZlNDFhODQzNDZjMDYzMzJkODc2MTQwM2UwZWU1NTJhOTEzMDFlNDFiZDU4YTZhZTcwZmI0YjYwIn0%3D");
            headers.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.74 Safari/537.36 Edg/99.0.1150.46");
            final Optional<Response> responseOptional = OkHttpUtil.postJson(url, content, headers);
            responseOptional.ifPresent(response -> {
                try {
                    System.out.println(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void uploadImageTest() {
        // 上传多个图片时，重复获取零时token，然后进行上传
        // 使用高级接口必须先保证本进程存在一个 TransferManager 实例，如果没有则创建
        // 详细代码参见本页：高级接口 -> 创建 TransferManager
        TransferManager transferManager = createTransferManager();
        // 存储桶的命名格式为 BucketName-APPID，此处填写的存储桶名称必须为此格式
        String bucketName = "wechatapppro-1252524126";
        // 对象键(Key)是对象在存储桶中的唯一标识。
        String key = "/appzFG9dgU09033/image/b_u_5fd3ab12dcefc_4EFEH1Jq/" + "l2al0obx07mf.png";
        // 本地文件路径
        String localFilePath = "C:\\Users\\qiaojinfeng3\\Desktop\\2022-04-22_232146.png";
        File localFile = new File(localFilePath);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
        try {
            // 高级接口会返回一个异步结果Upload
            // 可同步地调用 waitForUploadResult 方法等待上传完成，成功返回UploadResult, 失败抛出异常
            Upload upload = transferManager.upload(putObjectRequest);
            UploadResult uploadResult = upload.waitForUploadResult();
            System.out.println(JSON.toJSONString(uploadResult));
        } catch (CosServiceException e) {
            e.printStackTrace();
        } catch (CosClientException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 确定本进程不再使用 transferManager 实例之后，关闭之
        // 详细代码参见本页：高级接口 -> 关闭 TransferManager
        shutdownTransferManager(transferManager);
    }

    // 创建 TransferManager 实例，这个实例用来后续调用高级接口
    TransferManager createTransferManager() {
        // 创建一个 COSClient 实例，这是访问 COS 服务的基础实例。
        // 详细代码参见本页: 简单操作 -> 创建 COSClient
        COSClient cosClient = createCOSClient();
        // 自定义线程池大小，建议在客户端与 COS 网络充足（例如使用腾讯云的 CVM，同地域上传 COS）的情况下，设置成16或32即可，可较充分的利用网络资源
        // 对于使用公网传输且网络带宽质量不高的情况，建议减小该值，避免因网速过慢，造成请求超时。
        ExecutorService threadPool = Executors.newFixedThreadPool(32);
        // 传入一个 threadpool, 若不传入线程池，默认 TransferManager 中会生成一个单线程的线程池。
        TransferManager transferManager = new TransferManager(cosClient, threadPool);
        // 设置高级接口的配置项
        // 分块上传阈值和分块大小分别为 5MB 和 1MB
        TransferManagerConfiguration transferManagerConfiguration = new TransferManagerConfiguration();
        transferManagerConfiguration.setMultipartUploadThreshold(5 * 1024 * 1024);
        transferManagerConfiguration.setMinimumUploadPartSize(1 * 1024 * 1024);
        transferManager.setConfiguration(transferManagerConfiguration);
        return transferManager;
    }

    void shutdownTransferManager(TransferManager transferManager) {
        // 指定参数为 true, 则同时会关闭 transferManager 内部的 COSClient 实例。
        // 指定参数为 false, 则不会关闭 transferManager 内部的 COSClient 实例。
        transferManager.shutdownNow(true);
    }

    COSClient createCOSClient() {
        // 1 传入获取到的临时密钥 (tmpSecretId, tmpSecretKey, sessionToken)
        String tmpSecretId = "AKIDm0sfcz8eKm6HmemBERGoVkhbZqeITxxaQ6_zusqkFPrrdKcOEfW9Mav34KCZPSo4";
        String tmpSecretKey = "S+3BrRbZf8yLVncNhbx+Zfnul9WKog1sCgni4ikKEOU=";
        String sessionToken = "myouEHPonwgJIjLqAUDGR35a4BvWhwtac41ce71b193308cf1e8c14cac2c2a7cadOSKVPvx2bEGydYre779t1M1AvuACvlcJr2T3FwEJtY0LYjq5cP7XM1UKIwmpSt-bm8tHeOt0GTAaHRqcRTOSMBc4mvJXDOZQBvdLl6rnqz1E22ihPaU_SfG6qp8nLNNkBVX90emFiCYHNfNNGRNoY22kcXXJ3hXi1_6MIwBoWcvIPX-0cHE9ieVBqNgYM_2tcwYdauY6-S6iP4iRKB4Kc5-mLc6DmFzN-Ul0RNjx4cjxnMy9sFpSceuLKubu51N5_B2qVOxwNQ1PksEn5UAXoT2u223PPj8Siw9TuLDABslVIiD3xymjCidupxbLuZV3Z4xeh3A6O16JyNYcVGqwaHiCQ4cpDB86y8_TsNe45pcJEfwrUAtGvbHjIzFfFxXYlezwvE65SkXosO2MABPTZtme7PJ0gS1nl-yBEhoZdAlsyrgFZZNzPgvarDD1h8ZHzmEmmP0UELV3fR1M6-oNv5v5SNoI4NT9MCd2KEWPWnu6mps7-n-5N6w5z0Xcc0LRrWpK7xrindL1AK6ReaX-SbIrBflDXYGUHIsIxXPuIgDH108lvYFcPC_34bsm32my1htnOH9T6_TAfb7ZYEWudvPWzNwWzKz7WlimH7RcHGWhKnyHq0UcfEZpQWStvqAefAKf75O_Ovg2KBWXEXDng";
        BasicSessionCredentials cred = new BasicSessionCredentials(tmpSecretId, tmpSecretKey, sessionToken);
        // 2 设置 bucket 的地域, COS 地域的简称请参阅 https://cloud.tencent.com/document/product/436/6224
        // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参阅源码或者常见问题 Java SDK 部分
        Region region = new Region("ap-shanghai");
        ClientConfig clientConfig = new ClientConfig(region);
        // 3 生成 cos 客户端
        return new COSClient(cred, clientConfig);
    }
}
