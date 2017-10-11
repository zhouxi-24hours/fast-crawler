package com.fast.crawler.runner;

import com.fast.crawler.constant.DataCommonConstant;
import com.fast.crawler.task.dynamic.QuartzDynamicTask;
import com.fast.crawler.task.jobs.CrawlerIPoolJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @Package com.fast.crawler.runner
 * @Description: 爬取西刺网站IP
 * @Author zhouxi
 * @Date 2017/10/11 16:21
 */
@Component
public class IPoolStartupRunner implements CommandLineRunner {

    @Autowired
    private QuartzDynamicTask quartzDynamicTask;

    @Override
    public void run(String... strings) throws Exception {
        quartzDynamicTask.addJob(DataCommonConstant.XICI_TASK_NAME, CrawlerIPoolJob.class);
    }
}
