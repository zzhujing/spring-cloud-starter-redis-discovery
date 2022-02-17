# 基于Redission + Spring Cloud Commons实现的简易注册中心
> 实现参考Naocs,仿写，未实现服务的订阅拉取更新

## 使用步骤

- 引入starter
- 配置redis地址和指定注册中心路由表key (`spring.cloud.redis.discovery.registryTableName=xxx`) 即可
- 具体配置参见`RedisProperties`
