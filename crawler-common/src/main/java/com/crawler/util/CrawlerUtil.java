package com.crawler.util;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @program: aukey-customer-service
 * @author: wgf
 * @create: 2019-01-14 16:38
 * @description: 爬虫工具类
 **/
public class CrawlerUtil {
    private CrawlerUtil() {
    }

    /**
     * 计算总页数
     *
     * @param count    总数
     * @param pageSize 每页大小
     * @return
     */
    public static int getPageCountNum(int count, int pageSize) {
        if (count <= pageSize)
            return 1;

        if (count % pageSize == 0) {
            return count / pageSize;
        } else {
            return count / pageSize + 1;
        }
    }

    /**
     * 判断亚马逊Review星级方法
     *
     * @param classList
     * @return
     */
    public static String chooseReviewDetailStar(Set<String> classList) {
        String star = "0";
        if (classList.contains("a-star-5")) {
            star = "5";
        } else if (classList.contains("a-star-4")) {
            star = "4";
        } else if (classList.contains("a-star-3")) {
            star = "3";
        } else if (classList.contains("a-star-2")) {
            star = "2";
        } else if (classList.contains("a-star-1")) {
            star = "1";
        }
        return star;
    }

    /**
     * 百分比字符串解析为双精度浮点数 (50% -> 0.5)
     *
     * @param str
     * @return
     */
    public static Double parsePercentage(String str) {
        str = str.replace("%", "");
        return convertDouble(str) / 100;
    }

    public static Double convertDouble(String str) {
        if (StringUtils.isBlank(str)) {
            return 0.0D;
        }

        return Double.valueOf(str);
    }


    public static Map<String, String> urlParamAnalysis(String URL) {
        Map<String, String> mapRequest = new HashMap<>();

        String[] arrSplit = null;

        String strUrlParam = truncateUrlPage(URL);
        if (strUrlParam == null) {
            return mapRequest;
        }
        arrSplit = strUrlParam.split("[&]");
        for (String strSplit : arrSplit) {
            String[] arrSplitEqual = null;
            arrSplitEqual = strSplit.split("[=]");

            //解析出键值
            if (arrSplitEqual.length > 1) {
                //正确解析
                mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);

            } else {
                if (arrSplitEqual[0] != "") {
                    //只有参数没有值，不加入
                    mapRequest.put(arrSplitEqual[0], "");
                }
            }
        }
        return mapRequest;
    }

    /**
     * 获取URL中的参数值
     * @param url       URL
     * @param paramName 参数名
     * @return 参数值
     */
    public static String urlParamAnalysis(String url, String paramName) {
        Map<String, String> mapRequest = urlParamAnalysis(url);
        return MapUtils.getString(mapRequest, paramName);
    }

    /**
     * 去掉url中的路径，留下请求参数部分
     *
     * @param strURL url地址
     * @return url请求参数部分
     */
    private static String truncateUrlPage(String strURL) {
        String strAllParam = null;
        String[] arrSplit = null;

        strURL = strURL.trim();

        arrSplit = strURL.split("[?]");
        if (strURL.length() > 1) {
            if (arrSplit.length > 1) {
                if (arrSplit[1] != null) {
                    strAllParam = arrSplit[1];
                }
            }
        }

        return strAllParam;
    }

    /**
     * 删除所有的HTML标签
     *
     * @param source 需要进行除HTML的文本
     * @return
     */
    public static String deleteAllHTMLTag(String source) {

        if(source == null) {
            return "";
        }

        String s = source;
        /** 删除普通标签  */
        s = s.replaceAll("<(S*?)[^>]*>.*?|<.*? />", "");
        /** 删除转义字符 */
        s = s.replaceAll("&.{2,6}?;", "");
        return s;
    }
}
