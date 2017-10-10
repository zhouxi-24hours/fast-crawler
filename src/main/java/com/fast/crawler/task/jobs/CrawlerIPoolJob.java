package com.fast.crawler.task.jobs;

import com.fast.crawler.constant.DataCommonConstant;
import com.fast.crawler.proxyIp.entity.ProxyHost;
import com.fast.crawler.proxyIp.filter.IPFilter;
import com.fast.crawler.proxyIp.website.CrawlerIpWebsite;
import com.fast.crawler.redis.RedisClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Package com.fast.crawler.task.jobs
 * @Description:  创建任务 - 爬取西刺网站数据存入redis中
 * @Author zhouxi
 * @Date 2017/10/10 15:28
 */
@Component
public class CrawlerIPoolJob implements Job {

    private static Logger LOGGER = LogManager.getLogger(CrawlerIPoolJob.class);

    @Autowired
    private RedisClient<String, Object> redisClient;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LOGGER.info("###定时任务,爬取西刺网站IP存入redis中###");
        String url = DataCommonConstant.IP_COMM_URL + DataCommonConstant.COUNT;
        List<ProxyHost> list = IPFilter.doIPoolFilter(CrawlerIpWebsite.crawlerXiCiWebsite(url));  // 获取到有效的ip
        redisClient.putListData(DataCommonConstant.REDIS_IP_PARAM, list);
    }
}
