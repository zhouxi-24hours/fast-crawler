package com.fast.crawler.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.fast.crawler.property.DataSourceProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @Description: druid数据源的配置
 * @author zhouxi
 * @date 2017年8月25日 下午9:32:48
 *
 */
@Configuration
@EnableConfigurationProperties(DataSourceProperties.class)
public class DatasourceConfig {

	@Autowired
	private DataSourceProperties dataSourceProperties;
	
	@Bean(name = "druidDataSource")
	public DruidDataSource druidDataSource() {
		DruidDataSource dataSource=new DruidDataSource();
		dataSource.setDriverClassName(dataSourceProperties.getDriverClass());
		dataSource.setUrl(dataSourceProperties.getUrl());
        dataSource.setUsername(dataSourceProperties.getUser());
        dataSource.setPassword(dataSourceProperties.getPassword());
        dataSource.setInitialSize(dataSourceProperties.getInitialSize());
        dataSource.setMinIdle(dataSourceProperties.getMinIdle());
        dataSource.setMaxActive(dataSourceProperties.getMaxActive());
        dataSource.setMaxWait(dataSourceProperties.getMaxWait());
        dataSource.setTimeBetweenEvictionRunsMillis(dataSourceProperties.getTimeBetweenEvictionRunsMillis());
		return dataSource;
	}
}
