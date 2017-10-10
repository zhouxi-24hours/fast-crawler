package com.fast.crawler.proxyIp.filter;

import com.fast.crawler.proxyIp.entity.ProxyHost;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Package com.fast.crawler.proxyIp.filter
 * @Description: ip筛选过滤器
 * @Author zhouxi
 * @Date 2017/10/10 11:50
 */
public class IPFilter {

    private static ExecutorService ipoolFilter = null;  // 定义Java线程池
    private static Logger LOGGER = LogManager.getLogger(IPFilter.class);

    static {
        if (ipoolFilter == null) {
            ipoolFilter = Executors.newCachedThreadPool();
        }
    }

    /**
     * @Description: 判断IP和端口是否能连通,可用
     * @param ip
     * @param port
     * @return
     */
    private static boolean isHostConnectable(String ip, int port) {
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(ip, port));
        } catch (IOException e) {
            LOGGER.info("ip[{}],port[{}]无法连通" ,ip, port);
            return false;
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * @Description: 过滤方法;筛选出有用的ip
     * @param proxyHostList
     * @return
     */
    public static List<ProxyHost> doIPoolFilter(List<ProxyHost> proxyHostList) {

        // 在并发中使用CopyOnWriteArrayList代替List
        CopyOnWriteArrayList<ProxyHost> newIPoolList = new CopyOnWriteArrayList<ProxyHost>();
        for (ProxyHost proxyHost: proxyHostList) {
            ipoolFilter.execute(new Runnable() {
                @Override
                public void run() {
                    double speed = Double.parseDouble(proxyHost.getSpeed());
                    if(speed <= 2.0 && "HTTPS".equals(proxyHost.getType()) && isHostConnectable(proxyHost.getIp(), proxyHost.getPort())) {
                        newIPoolList.add(proxyHost);
                    }
                }
            });
        }
        ipoolFilter.shutdown();
        try {
            boolean loop = true;
            do {    //等待所有任务完成
                loop = !ipoolFilter.awaitTermination(2, TimeUnit.SECONDS);
            } while(loop);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LOGGER.info("当前爬取中可用IP集合[{}]",newIPoolList);
        return newIPoolList;
    }
}
