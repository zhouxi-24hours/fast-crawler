package com.fast.crawler.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * 
 * @Description: ehcache配置
 * @author zhouxi
 * @date 2017年8月30日 上午11:23:52
 *
 */
@Configuration  
@EnableCaching  //标注启动缓存.  
public class CacheConfiguration {  
    
	private static Logger LOGGER = LogManager.getLogger(CacheConfiguration.class);

    /**
     * ehcache 主要的管理器
     * 
     * @param ehCacheManagerFactoryBean
     * @return
     */
    @Bean(name = "ehCacheCacheManager")
    public EhCacheCacheManager ehCacheCacheManager(EhCacheManagerFactoryBean ehCacheManagerFactoryBean){
        LOGGER.info("CacheConfiguration.ehCacheCacheManager()");
        return new EhCacheCacheManager(ehCacheManagerFactoryBean.getObject());
    }

    /**
     * 据shared与否的设置,
     * Spring分别通过CacheManager.create()
     * 或new CacheManager()方式来创建一个ehcache基地.
     *
     * 也说是说通过这个来设置cache的基地是这里的Spring独用,还是可以跟别的技术共享
     *
     */
    @Bean(name = "ehCacheManagerFactoryBean")
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean(){
        LOGGER.info("CacheConfiguration读取ehcache.xml配置文件");
        EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean ();
        cacheManagerFactoryBean.setConfigLocation (new ClassPathResource("ehcache.xml"));
        cacheManagerFactoryBean.setShared(true);
        return cacheManagerFactoryBean;
    }
}
