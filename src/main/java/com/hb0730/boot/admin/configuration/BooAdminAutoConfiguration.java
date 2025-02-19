package com.hb0730.boot.admin.configuration;

import com.hb0730.boot.admin.configuration.properties.BootAdminProperties;
import com.hb0730.boot.admin.security.model.User;
import com.hb0730.boot.admin.token.ITokenService;
import com.hb0730.boot.admin.token.impl.RedisTokenServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * boot admin 配置
 *
 * @author bing_huang
 * @since 3.0.0
 */
@Configuration
@RequiredArgsConstructor
public class BooAdminAutoConfiguration {
    private final BootAdminProperties properties;
    private final StringRedisTemplate stringRedisTemplate;
    private final RedisTemplate<String, User> redisTemplate;

    /**
     * 配置redis token缓存
     *
     * @return {@link ITokenService}
     */
    @Bean
    public ITokenService tokenService() {
        return new RedisTokenServiceImpl(properties.getTokenConfig(), stringRedisTemplate, redisTemplate);
    }
}
