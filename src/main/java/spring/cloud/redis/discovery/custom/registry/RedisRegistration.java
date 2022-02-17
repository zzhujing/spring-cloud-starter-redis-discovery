package spring.cloud.redis.discovery.custom.registry;

import lombok.Data;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.ApplicationContext;
import spring.cloud.redis.discovery.custom.discovery.RedisProperties;

import java.net.URI;
import java.util.Map;

/**
 * Spring Cloud Commons 服务注册实体实现
 */
@Data
public class RedisRegistration implements Registration, ServiceInstance {

    private RedisProperties redisProperties;
    private ApplicationContext applicationContext;

    public RedisRegistration(RedisProperties redisProperties, ApplicationContext applicationContext) {
        this.redisProperties = redisProperties;
        this.applicationContext = applicationContext;
    }

    @Override
    public String getServiceId() {
        return redisProperties.getServiceName();
    }

    @Override
    public String getHost() {
        return redisProperties.getHost();
    }

    @Override
    public int getPort() {
        return redisProperties.getPort();
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public URI getUri() {
        return DefaultServiceInstance.getUri(this);
    }

    @Override
    public Map<String, String> getMetadata() {
        return redisProperties.getMetadata();
    }


}
