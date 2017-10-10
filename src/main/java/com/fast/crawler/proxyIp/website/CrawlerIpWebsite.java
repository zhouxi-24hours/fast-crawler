package com.fast.crawler.proxyIp.website;

import com.fast.crawler.parse.HtmlContentUtil;
import com.fast.crawler.proxyIp.entity.ProxyHost;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * @Package com.fast.crawler.proxyIp.service
 * @Description: 爬取西刺网站的IP
 * @Author zhouxi
 * @Date 2017/10/10 10:43
 */
public class CrawlerIpWebsite {

    private static Logger LOGGER = LogManager.getLogger(CrawlerIpWebsite.class);

    /**
     * @Description: 爬取西刺网站IP - 西刺网站数据的爬取都是用本机IP来获取
     * @param url
     * @return
     */
    public static List<ProxyHost> crawlerXiCiWebsite(String url) {
        List<ProxyHost> iproxyHostList = new ArrayList<ProxyHost>();
        String htmlContent = HtmlContentUtil.getHtml(url);  // 利用本机IP爬取到数据内容
        if(htmlContent == null) {
            // 假如获取数据为null,重复爬取,循环3次,3次内如果依然不能获取内容,则结束本次爬取
            for(int i = 0; i < 3; i++) {
                htmlContent = HtmlContentUtil.getHtml(url);
                if(htmlContent != null) {
                    break;
                }
            }
        }
        if(htmlContent != null) {
            // 将html解析成DOM结构
            Document document = Jsoup.parse(htmlContent);
            // 提取所需要的数据
            Elements elementList = document.select("table[id=ip_list]").select("tbody").select("tr");
            for (int i = 1; i < elementList.size(); i++) {
                ProxyHost proxyHost = new ProxyHost();
                String ipAddress = elementList.get(i).select("td").get(1).text();
                String ipPort = elementList.get(i).select("td").get(2).text();
                String ipType = elementList.get(i).select("td").get(5).text();
                String ipSpeed = elementList.get(i).select("td").get(6).select("div[class=bar]").attr("title");
                proxyHost.setIp(ipAddress);
                proxyHost.setPort(Integer.parseInt(ipPort));
                proxyHost.setType(ipType);
                proxyHost.setSpeed(ipSpeed);
                iproxyHostList.add(proxyHost);
            }
        }
        return iproxyHostList;
    }
}
