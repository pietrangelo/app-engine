package org.entando.entando.plugins.jpredis.aps.system.redis;

import static org.entando.entando.plugins.jpredis.aps.system.redis.RedisEnvironmentVariables.REDIS_ADDRESS;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.resource.DefaultClientResources;
import org.apache.commons.lang3.StringUtils;
import org.entando.entando.plugins.jpredis.aps.system.redis.condition.RedisActive;
import org.entando.entando.plugins.jpredis.aps.system.redis.condition.RedisSentinel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

@Configuration
@EnableCaching
@RedisActive(true)
@RedisSentinel(false)
public class SingleNodeConfig extends BaseRedisCacheConfig {

    private static final Logger logger = LoggerFactory.getLogger(SingleNodeConfig.class);

    @Value("${" + REDIS_ADDRESS + ":redis://localhost:6379}")
    private String redisAddress;

    @Bean(destroyMethod = "destroy")
    public LettuceConnectionFactory connectionFactory() {
        logger.info("** Redis with single node configuration **");
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        String[] sections = this.redisAddress.substring(REDIS_PREFIX.length()).split(":");
        redisStandaloneConfiguration.setHostName(sections[0]);
        redisStandaloneConfiguration.setPort(Integer.parseInt(sections[1]));
        if (!StringUtils.isBlank(this.redisPassword)) {
            redisStandaloneConfiguration.setPassword(this.redisPassword);
        }
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean(destroyMethod = "shutdown")
    public RedisClient getRedisClient(DefaultClientResources resources) {
        RedisURI redisUri = RedisURI.create(
                (this.redisAddress.startsWith(REDIS_PREFIX)) ? this.redisAddress : REDIS_PREFIX + this.redisAddress);
        if (!StringUtils.isBlank(this.redisPassword)) {
            redisUri.setPassword(this.redisPassword.toCharArray());
        }
        return new RedisClient(resources, redisUri) {
        };
    }
}
