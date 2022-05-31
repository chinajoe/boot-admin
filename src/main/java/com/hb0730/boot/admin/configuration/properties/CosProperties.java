package com.hb0730.boot.admin.configuration.properties;

import com.hb0730.commons.lang.codec.Base64Utils;
import lombok.Data;

import java.nio.charset.StandardCharsets;

/**
 * The type Cos properties.
 */
@Data
public class CosProperties {
    private static final String SECRET_ID = "QUtJREVLRXVNWlhIcDlGRjVxTmtzbVJyMnpoOVFTRWwzN3dX";
    private static final String SECRET_KEY = "T2JaSW0yNXRTbnh1YzVKSTNacHRpSWVtZ0xsd3doRFU=";
    private String bucketName;
    private String region;
    private String secretId = new String(
        Base64Utils.decode(SECRET_ID.getBytes(StandardCharsets.UTF_8))
    );
    private String secretKey = new String(
        Base64Utils.decode(SECRET_KEY.getBytes(StandardCharsets.UTF_8))
    );
}
