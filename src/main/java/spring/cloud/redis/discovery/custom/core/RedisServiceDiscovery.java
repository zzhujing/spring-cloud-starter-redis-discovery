package spring.cloud.redis.discovery.custom.core;

import lombok.AllArgsConstructor;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;

import java.util.*;

/**
 * redis服务发现工具类
 */
@AllArgsConstructor
public class RedisServiceDiscovery {
    private final RedissonClient client;
    private final String registryTableName;

    /**
     * 根据服务id获取实例列表
     * @param serviceId
     * @return
     */
    public List<RedisInstance> getInstancesByServiceId(String serviceId) {
        RMap<String, List<RedisInstance>> routingTable = client.getMap(registryTableName);
        return routingTable.getOrDefault(serviceId, Collections.emptyList());
    }

    /**
     * 获取具体实例信息
     * @param serviceId
     * @param host
     * @return
     */
    public RedisInstance getInstanceByServiceIdAndHost(String serviceId, String host) {
        RMap<String, List<RedisInstance>> routingTable = client.getMap(registryTableName);
        List<RedisInstance> instance = routingTable.getOrDefault(serviceId, Collections.emptyList());
        return instance.stream().filter(i -> i.getHost().equals(host)).findFirst().orElse(null);
    }

    /**
     * 获取所有服务
     * @return
     */
    public Map<String, List<RedisInstance>> getServices() {
        return client.getMap(registryTableName);
    }

    /**
     * 注册实例
     * @param redisInstance
     */
    public void registerInstance(RedisInstance redisInstance) {
        RMap<String, List<RedisInstance>> routingTable = client.getMap(registryTableName);
        List<RedisInstance> instances = Optional.ofNullable(routingTable.get(redisInstance.getServiceId())).orElse(new ArrayList<>());
        instances.add(redisInstance);
        routingTable.put(redisInstance.getServiceId(), instances);
    }

    /**
     * 注销实例
     * @param redisInstance
     */
    public void deRegisterInstance(RedisInstance redisInstance) {
        RMap<String, List<RedisInstance>> routingTable = client.getMap(registryTableName);
        List<RedisInstance> instances = Optional.ofNullable(routingTable.get(redisInstance.getServiceId())).orElse(new ArrayList<>());
        instances.removeIf(i->i.getHost().equals(redisInstance.getHost()));
        routingTable.put(redisInstance.getServiceId(), instances);
    }

    /**
     * 更新实例信息
     * @param redisInstance
     */
    public void updateInstance(RedisInstance redisInstance) {
        RMap<String, List<RedisInstance>> routingTable = client.getMap(registryTableName);
        List<RedisInstance> instances = Optional.ofNullable(routingTable.get(redisInstance.getServiceId())).orElse(new ArrayList<>());
        instances.removeIf(i->i.getHost().equals(redisInstance.getHost()));
        instances.add(redisInstance);
        routingTable.put(redisInstance.getServiceId(), instances);
    }

    /**
     * 关闭redisson连接
     */
    public void close() {
        client.shutdown();
    }
}
