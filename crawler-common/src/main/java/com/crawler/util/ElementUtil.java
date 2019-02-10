package com.crawler.util;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Objects;

/**
 * @program: aukey-customer-service
 * @author: wgf
 * @create: 2019-01-17 15:48
 * @description: 页面元素工具类
 **/
public class ElementUtil {
    private ElementUtil(){}

    /**
     * 获取字符串，若对象为空则返回空串
     *
     * @param obj
     * @return
     */
    public static String getString(Object obj) {
        return getString(obj, "");
    }

    /**
     * 获取字符串
     *
     * @param obj
     * @param defaultValue 默认值
     * @return
     */
    public static String getString(Object obj, String defaultValue) {
        String result = defaultValue;

        if (Objects.nonNull(obj)) {
            String str = String.valueOf(obj);
            result = str;
        }
        return result;
    }

    /**
     * 获取Elements 的 text属性，空则返回空串 ""
     *
     * @param elements
     * @return
     */
    public static String getTextByElements(Elements elements) {
        String result = "";

        if (Objects.nonNull(elements)) {
            String text = elements.text();
            result = getString(text);
        }
        return result;
    }

    /**
     * 获取Elements 的 text属性，空则返回空串 ""
     *
     * @param element
     * @return
     */
    public static String getTextByElement(Element element) {
        String result = "";

        if (Objects.nonNull(element)) {
            String text = element.text();
            result = getString(text);
        }
        return result;
    }

    public static String getAttrByElements(Elements elements, String attributeKey) {
        String result = "";

        if (Objects.nonNull(elements)) {
            String attr = elements.attr(attributeKey);
            result = getString(attr);
        }

        return result;
    }

    public static String getAttrByElement(Element element, String attributeKey) {
        String result = "";

        if (Objects.nonNull(element)) {
            String attr = element.attr(attributeKey);
            result = getString(attr);
        }

        return result;
    }
}
