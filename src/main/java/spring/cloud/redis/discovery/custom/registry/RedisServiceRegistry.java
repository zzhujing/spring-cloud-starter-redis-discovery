package spring.cloud.redis.discovery.custom.registry;


import lombok.AllArgsConstructor;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.cloud.client.serviceregistry.ServiceRegistry;
import spring.cloud.redis.discovery.custom.core.RedisInstance;
import spring.cloud.redis.discovery.custom.core.RedisServiceDiscovery;

@AllArgsConstructor
public class RedisServiceRegistry implements ServiceRegistry<Registration> {

    private final RedisServiceDiscovery serviceDiscovery;
    public static final String UP = "UP";
    public static final String DOWN = "DOWN";


    @Override
    public void register(Registration registration) {
        serviceDiscovery.registerInstance(RedisInstance.fromRegistration(registration));
    }

    @Override
    public void deregister(Registration registration) {
        serviceDiscovery.deRegisterInstance(RedisInstance.fromRegistration(registration));
    }

    @Override
    public void close() {
        serviceDiscovery.close();
    }

    @Override
    public void setStatus(Registration registration, String status) {
        if (!status.equals(UP) && !status.equals(DOWN)) {
            throw new IllegalArgumentException("status must be up or down");
        }
        RedisInstance instance = RedisInstance.fromRegistration(registration);
        instance.setEnabled(status.equals(UP));
        serviceDiscovery.updateInstance(instance);
    }

    @Override
    public Object getStatus(Registration registration) {
        RedisInstance instance = serviceDiscovery.getInstanceByServiceIdAndHost(registration.getServiceId(), registration.getHost());
        return instance == null ? "null" : instance.isEnabled() ? UP : DOWN;
    }
}
