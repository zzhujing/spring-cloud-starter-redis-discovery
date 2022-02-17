package spring.cloud.redis.discovery.custom.discovery;


import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.cloud.client.CommonsClientAutoConfiguration;
import org.springframework.cloud.client.ConditionalOnBlockingDiscoveryEnabled;
import org.springframework.cloud.client.ConditionalOnDiscoveryEnabled;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.simple.SimpleDiscoveryClientAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.cloud.redis.discovery.custom.core.RedisServiceDiscovery;

/**
 * 服务发现客户端 Spring Cloud Commons {@link DiscoveryClient}实现
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnDiscoveryEnabled
@ConditionalOnBlockingDiscoveryEnabled
@ConditionalOnRedisDiscoveryEnabled
@AutoConfigureBefore({ SimpleDiscoveryClientAutoConfiguration.class,
        CommonsClientAutoConfiguration.class })
@AutoConfigureAfter(RedisDiscoveryAutoConfiguration.class)
public class RedisDiscoveryClientConfiguration {


    @Bean
    public RedisDiscoveryClient discoveryClient(RedisServiceDiscovery serviceDiscovery) {
        return new RedisDiscoveryClient(serviceDiscovery);
    }
}
