package br.com.pag.service.order.configuration;

import org.infinispan.manager.EmbeddedCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class InfinispanConfig {

    private final EmbeddedCacheManager cacheManager;

    @Autowired
    public InfinispanConfig(EmbeddedCacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

}
