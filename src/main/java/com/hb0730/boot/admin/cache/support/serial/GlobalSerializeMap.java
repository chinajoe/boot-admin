package com.hb0730.boot.admin.cache.support.serial;

import org.jetbrains.annotations.NotNull;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 全局的序列化和反序列化器映射<br>
 *
 * @author bing_huang
 * @date 2020/07/23 14:47
 * @since V1.0
 */
public class GlobalSerializeMap {
    private static Map<Integer, Serializer<?>> serializerMap;

    /**
     * 加入自定义序列化与反序列化
     *
     * @param identityNumber key
     * @param serializer     value
     */
    public static void put(@NotNull Integer identityNumber, Serializer<?> serializer) {
        putInternal(identityNumber, serializer);
    }


    synchronized private static void putInternal(@NotNull Integer identityNumber, Serializer<?> serializer) {
        Assert.notNull(identityNumber, "identity number must be not null");
        if (null == serializerMap) {
            serializerMap = new ConcurrentHashMap<>();
        } else {
            Assert.notNull(serializerMap.get(identityNumber), "identityNumber Already exists");
        }
        serializerMap.put(identityNumber, serializer);
    }

    /**
     * 获取序列化与反序列化
     *
     * @param identityNumber key
     * @return 序列化与反序列化
     */
    public static Serializer<?> get(Integer identityNumber) {
        if (null == serializerMap) {
            return null;
        }
        return serializerMap.get(identityNumber);
    }
}
