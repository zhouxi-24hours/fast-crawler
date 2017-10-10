package com.fast.crawler.proxyIp.entity;

import java.io.Serializable;

/**
 * @Package com.fast.crawler.proxyIp.entity
 * @Description: 代理ip对象
 * @Author zhouxi
 * @Date 2017/10/1 21:20
 */
public class ProxyHost implements Serializable {

    private static final long serialVersionUID = -1169405105004428367L;

    private String ip;      // 代理ip

    private Integer port;   // 端口

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return this.port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "ProxyHost{" +
                "ip='" + ip + '\'' +
                ", port=" + port +
                '}';
    }
}
