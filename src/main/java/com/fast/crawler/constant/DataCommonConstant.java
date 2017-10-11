package com.fast.crawler.constant;

/**
 * @Package com.fast.crawler.constant
 * @Description: 公共数据
 * @Author zhouxi
 * @Date 2017/10/10 10:36
 */
public class DataCommonConstant {

    /**
     * 以队列的形式存入到redis中的key值
     */
    public static final String REDIS_IP_PARAM = "ipools";

    /**
     * 西刺IP网站地址
     */
    public static final String IP_COMM_URL = "http://www.xicidaili.com/nn/";

    /**
     * 计数 - 西刺网站中第几页
     */
    public static int COUNT = 1;

    /**
     * 定时任务名称
     */
    public static final String XICI_TASK_NAME = "crawlerXiciIp";
}
