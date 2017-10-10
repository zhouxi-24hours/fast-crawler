package com.fast.crawler.config;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

import java.util.Properties;

/**
 * 
 * @Description: MyBatis扫描接口，使用的tk.mybatis.spring.mapper.MapperScannerConfigurer
 * @author zhouxi
 * @date 2017年8月25日 下午5:54:08
 *
 */
@Configuration
@AutoConfigureAfter(MybatisConfig.class)
public class MyBatisMapperScannerConfig {

	@Bean(name = "mapperScannerConfigurer")
	public MapperScannerConfigurer mapperScannerConfigurer() {
		MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
		mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
		mapperScannerConfigurer.setBasePackage("com.fast.crawler.mapper"); // 扫描(*mapper.java)
		Properties properties = new Properties();
		/*
		 * 不要把MyMapper放在BasePackage中,不能同其他mapper被扫描到
		 */
		properties.setProperty("mappers", "com.fast.crawler.util.MyMapper");
		properties.setProperty("notEmpty", "false");
		properties.setProperty("IDENTITY", "MYSQL");
		mapperScannerConfigurer.setProperties(properties);
		return mapperScannerConfigurer;
	}
}
