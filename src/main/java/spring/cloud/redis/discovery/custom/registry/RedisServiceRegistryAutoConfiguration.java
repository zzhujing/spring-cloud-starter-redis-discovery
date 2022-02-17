package spring.cloud.redis.discovery.custom.registry;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.serviceregistry.AutoServiceRegistrationAutoConfiguration;
import org.springframework.cloud.client.serviceregistry.AutoServiceRegistrationConfiguration;
import org.springframework.cloud.client.serviceregistry.AutoServiceRegistrationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.cloud.redis.discovery.custom.core.RedisServiceDiscovery;
import spring.cloud.redis.discovery.custom.discovery.ConditionalOnRedisDiscoveryEnabled;
import spring.cloud.redis.discovery.custom.discovery.RedisDiscoveryAutoConfiguration;
import spring.cloud.redis.discovery.custom.discovery.RedisProperties;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(RedisProperties.class)
@ConditionalOnRedisDiscoveryEnabled
@ConditionalOnProperty(value = "spring.cloud.service-registry.auto-registration.enabled",
        matchIfMissing = true)
@AutoConfigureAfter({AutoServiceRegistrationConfiguration.class,
        AutoServiceRegistrationAutoConfiguration.class,
        RedisDiscoveryAutoConfiguration.class})
public class RedisServiceRegistryAutoConfiguration {

    @Bean
    @ConditionalOnBean(RedisServiceDiscovery.class)
    public RedisServiceRegistry lazylittleServiceRegistry(RedisServiceDiscovery serviceDiscovery) {
        return new RedisServiceRegistry(serviceDiscovery);
    }

    @Bean
//    @ConditionalOnBean(AutoServiceRegistrationProperties.class)
    public RedisRegistration lazylittleRegistration(RedisProperties redisProperties,
                                                    ApplicationContext applicationContext) {
        return new RedisRegistration(redisProperties, applicationContext);
    }

    @Bean
//    @ConditionalOnBean(AutoServiceRegistrationProperties.class)
    public RedisAutoServiceRegistry lazylittleAutoServiceRegistry(RedisServiceRegistry redisServiceRegistry,
                                                                  AutoServiceRegistrationProperties autoServiceRegistrationProperties,
                                                                  RedisRegistration redisRegistration) {
        return new RedisAutoServiceRegistry(redisServiceRegistry, autoServiceRegistrationProperties, redisRegistration);
    }
}
