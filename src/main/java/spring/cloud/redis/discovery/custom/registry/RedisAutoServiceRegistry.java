package spring.cloud.redis.discovery.custom.registry;

import org.springframework.cloud.client.serviceregistry.AbstractAutoServiceRegistration;
import org.springframework.cloud.client.serviceregistry.AutoServiceRegistrationProperties;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.cloud.client.serviceregistry.ServiceRegistry;

public class RedisAutoServiceRegistry extends AbstractAutoServiceRegistration<Registration> {

    private RedisRegistration registration;

    protected RedisAutoServiceRegistry(ServiceRegistry<Registration> serviceRegistry,
                                       AutoServiceRegistrationProperties properties,
                                       RedisRegistration registration) {
        super(serviceRegistry, properties);
        this.registration = registration;
    }

    @Override
    protected void register() {
        registration.getRedisProperties().setPort(this.getPort().get());
        super.register();
    }

    @Override
    protected Object getConfiguration() {
        return this.registration.getRedisProperties();
    }

    @Override
    protected boolean isEnabled() {
        return true;
    }

    @Override
    protected Registration getRegistration() {
        return registration;
    }

    @Override
    protected Registration getManagementRegistration() {
        return null;
    }
}
