package spring.cloud.redis.discovery.custom.core;

import com.fasterxml.jackson.core.type.TypeReference;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.redisson.spring.starter.RedissonAutoConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration(proxyBeanMethods = false)
public class RedissonConfiguration {

    /**
     * redisson Json Codec
     * @return
     */
    @Bean
    public RedissonAutoConfigurationCustomizer jsonCodecCustomizer() {
        return configuration-> configuration.setCodec(new TypedJsonJacksonCodec(new TypeReference<String>(){}, new TypeReference<List<RedisInstance>>(){}));
    }

}
