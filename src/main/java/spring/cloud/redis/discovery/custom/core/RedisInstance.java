package spring.cloud.redis.discovery.custom.core;

import cn.hutool.core.bean.BeanUtil;
import lombok.Data;
import org.springframework.cloud.client.serviceregistry.Registration;
import spring.cloud.redis.discovery.custom.discovery.RedisServiceInstance;

import java.io.Serializable;
import java.util.Map;

/**
 * 自定义基于redis注册中心的instance对象
 */
@Data
public class RedisInstance implements Serializable {

    private static final long serialVersionUID = 1L;

    private String serviceId;

    private String host;

    private int port;

    private boolean enabled = true;

    private Map<String, String> metadata;


    public RedisServiceInstance toServiceInstance() {
        RedisServiceInstance instance = new RedisServiceInstance();
        BeanUtil.copyProperties(this, instance);
        instance.setSecure(false);
        return instance;
    }

    public static RedisInstance fromRegistration(Registration registration) {
        RedisInstance instance = new RedisInstance();
        instance.setHost(registration.getHost());
        instance.setServiceId(registration.getServiceId());
        instance.setPort(registration.getPort());
        instance.setMetadata(registration.getMetadata());
        return instance;
    }

}
