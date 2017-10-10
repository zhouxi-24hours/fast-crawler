package com.fast.crawler.proxyIp.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
}
