package spring.cloud.redis.discovery.custom.discovery;

import org.redisson.api.RedissonClient;
import org.redisson.spring.starter.RedissonAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.ConditionalOnDiscoveryEnabled;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.cloud.redis.discovery.custom.core.RedisServiceDiscovery;

/**
 * 基于Redis注册中心的核心自动配置配置类
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnDiscoveryEnabled
@ConditionalOnRedisDiscoveryEnabled
@AutoConfigureAfter(RedissonAutoConfiguration.class)
@EnableConfigurationProperties(RedisProperties.class)
public class RedisDiscoveryAutoConfiguration {

    @Bean
    public RedisServiceDiscovery lazylittleServiceDiscovery(RedissonClient redisson,
                                                            RedisProperties redisProperties) {
        return new RedisServiceDiscovery(redisson, redisProperties.getRegistryTableName());
    }
}
