package com.hb0730.boot.admin.configuration.properties;

import lombok.Data;

/**
 * The type Cos properties.
 */
@Data
public class CosProperties {
    private String bucketName;
    private String region;
    private String secretId = "AKIDEKEuMZXHp9FF5qNksmRr2zh9QSEl37wW";
    private String secretKey = "ObZIm25tSnxuc5JI3ZptiIemgLlwwhDU";
}
