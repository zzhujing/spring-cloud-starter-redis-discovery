package spring.cloud.redis.discovery.custom.discovery;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 核心配置类
 */
@ConfigurationProperties(prefix = "spring.cloud.redis.discovery")
@Data
public class RedisProperties {

    /**
     * 注册中心路由表key名称
     */
    private String registryTableName;
    /**
     * 服务名称
     */
    @Value("${spring.cloud.redis.discovery.serviceName:${spring.application.name:}}")
    private String serviceName;

    /**
     * 主机ip
     */
    private String host;

    /**
     * 端口号
     */
    private int port;

    /**
     * 网卡
     */
    private String networkInterface;

    /**
     * 元数据信息
     */
    private Map<String,String> metadata = new HashMap<>();

    @Autowired
    private InetUtils inetUtils;

    @PostConstruct
    public void init() throws SocketException {
        if (StringUtils.isEmpty(host)) {
            // traversing network interfaces if didn't specify a interface
            if (StringUtils.isEmpty(networkInterface)) {
                host = inetUtils.findFirstNonLoopbackHostInfo().getIpAddress();
            }
            else {
                NetworkInterface netInterface = NetworkInterface
                        .getByName(networkInterface);
                if (null == netInterface) {
                    throw new IllegalArgumentException(
                            "no such interface " + networkInterface);
                }

                Enumeration<InetAddress> inetAddress = netInterface.getInetAddresses();
                while (inetAddress.hasMoreElements()) {
                    InetAddress currentAddress = inetAddress.nextElement();
                    if (currentAddress instanceof Inet4Address
                            && !currentAddress.isLoopbackAddress()) {
                        host = currentAddress.getHostAddress();
                        break;
                    }
                }

                if (StringUtils.isEmpty(host)) {
                    throw new RuntimeException("cannot find available ip from"
                            + " network interface " + networkInterface);
                }

            }
        }
    }
}
