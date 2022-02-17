package spring.cloud.redis.discovery.custom.discovery;

import lombok.Data;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;

import java.net.URI;
import java.util.Map;

/**
 * 实现Spring Cloud Commons, 用于服务发现实体
 */
@Data
public class RedisServiceInstance implements ServiceInstance {

    private String serviceId;

    private String host;

    private int port;

    private boolean secure;

    private Map<String, String> metadata;

    @Override
    public String getServiceId() {
        return serviceId;
    }

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public boolean isSecure() {
        return secure;
    }

    @Override
    public URI getUri() {
        return DefaultServiceInstance.getUri(this);
    }

    @Override
    public Map<String, String> getMetadata() {
        return metadata;
    }
}
