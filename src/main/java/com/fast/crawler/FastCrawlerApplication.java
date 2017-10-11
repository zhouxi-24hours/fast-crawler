package com.fast.crawler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class FastCrawlerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FastCrawlerApplication.class, args);
    }

    /**
     *
     * @Title: restTemplate
     * @Description: 配置RestTemplate
     * @param @return    设定文件
     * @return RestTemplate    返回类型
     */
    @Bean
    public RestTemplate restTemplate() {
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        return restTemplateBuilder.build();
    }
}
