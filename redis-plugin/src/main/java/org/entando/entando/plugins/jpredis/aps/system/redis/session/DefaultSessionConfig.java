package org.entando.entando.plugins.jpredis.aps.system.redis.session;

import javax.servlet.Filter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RedisSessionActive(false)
public class DefaultSessionConfig {

    @Bean
    public Filter springSessionRepositoryFilter() {
        // When Redis session is not active the springSessionRepositoryFilter
        // is replaced with a no-op filter
        return (servletRequest, servletResponse, filterChain)
                -> filterChain.doFilter(servletRequest, servletResponse);
    }
}
