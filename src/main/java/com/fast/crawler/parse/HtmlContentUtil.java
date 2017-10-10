package com.fast.crawler.parse;

import com.fast.crawler.proxyIp.entity.ProxyHost;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @Package com.fast.crawler.parse
 * @Description: 爬取url得到内容
 * @Author zhouxi
 * @Date 2017/10/1 21:26
 */
public class HtmlContentUtil {

    private static Logger LOGGER = LogManager.getLogger(HtmlContentUtil.class);

    /**
     * @Description: 爬取url的数据 - 使用本机IP爬取
     * @param url
     * @return
     */
    public static String getHtml(String url) {
        return getHtml(url, null, null);
    }

    /**
     * @Description: 爬取url的数据 - 切换userAgent
     * @param url
     * @param userAgent
     * @return
     */
    public static String getHtml(String url, String userAgent) {
        return getHtml(url, null, userAgent);
    }

    /**
     * @Description: 爬取url的数据 - 使用代理ip
     * @param url
     * @param proxyHost
     * @return
     */
    public static String getHtml(String url, ProxyHost proxyHost, String userAgent){

        String htmlContent = null;  // 解析得到的页面数据
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        if(proxyHost != null) {
            HttpHost proxy = new HttpHost(proxyHost.getIp(), proxyHost.getPort());  // 使用代理IP
            // 设置超时时间
            RequestConfig config = RequestConfig.custom().setProxy(proxy).setConnectTimeout(3000).setSocketTimeout(3000).build();
            httpGet.setConfig(config);
        }
        httpGet.setHeader("Accept", "application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        httpGet.setHeader("Accept-Encoding", "gzip, deflate, br");
        httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
        httpGet.setHeader("Connection", "keep-alive");
        httpGet.setHeader("Pragma", "no-cache");
        httpGet.setHeader("Upgrade-Insecure-Requests", "1");
        String defaultUserAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.90 Safari/537.36";
        if(userAgent == null) {
            httpGet.setHeader("User-Agent", defaultUserAgent);
        } else {
            httpGet.setHeader("User-Agent", userAgent);
        }
        try {
            // 客户端执行httpGet方法，返回响应
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                htmlContent = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
            } else {
                return "error";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return htmlContent;
    }
}
