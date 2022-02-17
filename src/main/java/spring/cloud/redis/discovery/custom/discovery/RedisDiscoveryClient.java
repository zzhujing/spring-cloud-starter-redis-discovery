package spring.cloud.redis.discovery.custom.discovery;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import spring.cloud.redis.discovery.custom.core.RedisInstance;
import spring.cloud.redis.discovery.custom.core.RedisServiceDiscovery;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Lazylittle(Redis)服务发现客户端
 */
@RequiredArgsConstructor
public class RedisDiscoveryClient implements DiscoveryClient {

    private final RedisServiceDiscovery serviceDiscovery;

    @Override
    public String description() {
        return "lazylittle-discoveryClient";
    }

    @Override
    public List<ServiceInstance> getInstances(String serviceId) {
        List<RedisInstance> instances = serviceDiscovery.getInstancesByServiceId(serviceId);
        return instances.stream().map(RedisInstance::toServiceInstance).collect(Collectors.toList());
    }

    @Override
    public List<String> getServices() {
        return new ArrayList<>(serviceDiscovery.getServices().keySet());
    }
}
