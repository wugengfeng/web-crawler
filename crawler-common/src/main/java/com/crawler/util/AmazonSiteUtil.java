package com.crawler.util;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: aukey-customer-service
 * @author: wgf
 * @create: 2019-01-16 16:05
 * @description:
 **/
public class AmazonSiteUtil {

    private AmazonSiteUtil() {
    }

    private static final SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
    private static final Map<String, Map<String, String>> monthMap = new HashMap<>();

    /**
     * 日期数据字典
     */
    static {
        Map<String, String> mxDate = new HashMap<>();
        Map<String, String> esDate = new HashMap<>();
        Map<String, String> caDate = new HashMap<>();
        Map<String, String> ukDate = new HashMap<>();
        Map<String, String> usDate = new HashMap<>();
        Map<String, String> frDate = new HashMap<>();
        Map<String, String> jpDate = new HashMap<>();
        Map<String, String> itDate = new HashMap<>();
        Map<String, String> deDate = new HashMap<>();

        monthMap.put("MX", mxDate);
        monthMap.put("ES", esDate);
        monthMap.put("CA", caDate);
        monthMap.put("UK", ukDate);
        monthMap.put("US", usDate);
        monthMap.put("FR", frDate);
        monthMap.put("JP", jpDate);
        monthMap.put("IT", itDate);
        monthMap.put("DE", deDate);

        mxDate.put("enero".toLowerCase(), "1");
        mxDate.put("febrero".toLowerCase(), "2");
        mxDate.put("marzo".toLowerCase(), "3");
        mxDate.put("abril".toLowerCase(), "4");
        mxDate.put("mayo".toLowerCase(), "5");
        mxDate.put("junio".toLowerCase(), "6");
        mxDate.put("julio".toLowerCase(), "7");
        mxDate.put("agosto".toLowerCase(), "8");
        mxDate.put("septiembre".toLowerCase(), "9");
        mxDate.put("octubre".toLowerCase(), "10");
        mxDate.put("noviembre".toLowerCase(), "11");
        mxDate.put("diciembre".toLowerCase(), "12");

        esDate.put("enero".toLowerCase(), "1");
        esDate.put("febrero".toLowerCase(), "2");
        esDate.put("marzo".toLowerCase(), "3");
        esDate.put("abril".toLowerCase(), "4");
        esDate.put("mayo".toLowerCase(), "5");
        esDate.put("junio".toLowerCase(), "6");
        esDate.put("julio".toLowerCase(), "7");
        esDate.put("agosto".toLowerCase(), "8");
        esDate.put("septiembre".toLowerCase(), "9");
        esDate.put("octubre".toLowerCase(), "10");
        esDate.put("noviembre".toLowerCase(), "11");
        esDate.put("diciembre".toLowerCase(), "12");

        caDate.put("January".toLowerCase(), "1");
        caDate.put("February".toLowerCase(), "2");
        caDate.put("March".toLowerCase(), "3");
        caDate.put("April".toLowerCase(), "4");
        caDate.put("May".toLowerCase(), "5");
        caDate.put("June".toLowerCase(), "6");
        caDate.put("July".toLowerCase(), "7");
        caDate.put("August".toLowerCase(), "8");
        caDate.put("September".toLowerCase(), "9");
        caDate.put("October".toLowerCase(), "10");
        caDate.put("November".toLowerCase(), "11");
        caDate.put("December".toLowerCase(), "12");

        ukDate.put("January".toLowerCase(), "1");
        ukDate.put("February".toLowerCase(), "2");
        ukDate.put("March".toLowerCase(), "3");
        ukDate.put("April".toLowerCase(), "4");
        ukDate.put("May".toLowerCase(), "5");
        ukDate.put("June".toLowerCase(), "6");
        ukDate.put("July".toLowerCase(), "7");
        ukDate.put("August".toLowerCase(), "8");
        ukDate.put("September".toLowerCase(), "9");
        ukDate.put("October".toLowerCase(), "10");
        ukDate.put("November".toLowerCase(), "11");
        ukDate.put("December".toLowerCase(), "12");

        usDate.put("January".toLowerCase(), "1");
        usDate.put("February".toLowerCase(), "2");
        usDate.put("March".toLowerCase(), "3");
        usDate.put("April".toLowerCase(), "4");
        usDate.put("May".toLowerCase(), "5");
        usDate.put("June".toLowerCase(), "6");
        usDate.put("July".toLowerCase(), "7");
        usDate.put("August".toLowerCase(), "8");
        usDate.put("September".toLowerCase(), "9");
        usDate.put("October".toLowerCase(), "10");
        usDate.put("November".toLowerCase(), "11");
        usDate.put("December".toLowerCase(), "12");

        frDate.put("janvier".toLowerCase(), "1");
        frDate.put("février".toLowerCase(), "2");
        frDate.put("mars".toLowerCase(), "3");
        frDate.put("avril".toLowerCase(), "4");
        frDate.put("mai".toLowerCase(), "5");
        frDate.put("juin".toLowerCase(), "6");
        frDate.put("juillet".toLowerCase(), "7");
        frDate.put("août".toLowerCase(), "8");
        frDate.put("septembre".toLowerCase(), "9");
        frDate.put("octobre".toLowerCase(), "10");
        frDate.put("novembre".toLowerCase(), "11");
        frDate.put("décembre".toLowerCase(), "12");

        itDate.put("Gennaio".toLowerCase(), "1");
        itDate.put("febbraio".toLowerCase(), "2");
        itDate.put("marzo".toLowerCase(), "3");
        itDate.put("aprile".toLowerCase(), "4");
        itDate.put("maggio".toLowerCase(), "5");
        itDate.put("giugno".toLowerCase(), "6");
        itDate.put("luglio".toLowerCase(), "7");
        itDate.put("agosto".toLowerCase(), "8");
        itDate.put("settembre".toLowerCase(), "9");
        itDate.put("ottobre".toLowerCase(), "10");
        itDate.put("novembre".toLowerCase(), "11");
        itDate.put("dicembre".toLowerCase(), "12");

        deDate.put("Januar".toLowerCase(), "1");
        deDate.put("Februar".toLowerCase(), "2");
        deDate.put("März".toLowerCase(), "3");
        deDate.put("April".toLowerCase(), "4");
        deDate.put("Mai".toLowerCase(), "5");
        deDate.put("Juni".toLowerCase(), "6");
        deDate.put("Juli".toLowerCase(), "7");
        deDate.put("August".toLowerCase(), "8");
        deDate.put("September".toLowerCase(), "9");
        deDate.put("Oktober".toLowerCase(), "10");
        deDate.put("November".toLowerCase(), "11");
        deDate.put("Dezember".toLowerCase(), "12");
    }


    /**
     * 通过站点解析日期
     *
     * @param date
     * @param site
     * @return
     * @throws ParseException
     */
    public static Date chooseDateForSite(String date, String site) throws ParseException {
        switch (site) {
            case "MX": {
                // 11 de agosto de 2018
                String str[] = date.replaceAll("de", "").split("  ");
                String result = str[2] + "-" + monthMap.get("MX").get(str[1].toLowerCase()) + "-" + str[0];
                return parseDate(result);
            }
            case "ES": {
                // 7 de mayo de 2018
                String[] str = date.replace("el ", "").split(" ");
                String result = str[4] + "-" + monthMap.get("ES").get(str[2].toLowerCase()) + "-" + str[0];
                return parseDate(result);
            }
            case "UK": {
                // 12 July 2017
                String[] str = date.split(" ");
                String result = str[2] + "-" + monthMap.get("UK").get(str[1].toLowerCase()) + "-" + str[0];
                return parseDate(result);
            }
            case "US": {
                // July 6, 2017
                String[] str = date.replaceAll("[, ]+", " ").split(" ");

                String result = str[2] + "-" + monthMap.get("US").get(str[0].toLowerCase()) + "-" + str[1];
                return parseDate(result);
            }
            case "FR": {
                // 22 janvier 2019
                String[] str = date.split(" ");
                String result = str[2] + "-" + monthMap.get("FR").get(str[1].toLowerCase()) + "-" + str[0];
                return parseDate(result);
            }
            case "JP": {
                // REVIEW日期格式： 2018年11月25日
                // FEEDBACK日期格式：2018/04/24

                String result = null;
                if (date.contains("/")) {
                    // FEEDBACK
                    result = date.replace("/", "-");
                } else {
                    // REVIEW
                    result = date.replace("年", "-").replace("月", "-").replace("日", "");
                }
                return parseDate(result);
            }
            case "IT": {
                // 22 ottobre 2018
                String[] str = date.split(" ");
                String result = str[2] + "-" + monthMap.get("IT").get(str[1].toLowerCase()) + "-" + str[0];
                return parseDate(result);
            }
            case "DE": {
                // 23. Dezember 2017
                String[] str = date.replace(".", "").split(" ");
                String result = str[2] + "-" + monthMap.get("DE").get(str[1].toLowerCase()) + "-" + str[0];
                return parseDate(result);
            }
            case "CA": {
                // December 27, 2017
                String[] str = date.replace(",", "").split(" ");
                String result = str[2] + "-" + monthMap.get("CA").get(str[0].toLowerCase()) + "-" + str[1];
                return parseDate(result);
            }
        }
        return null;
    }

    private static Date parseDate(String result) throws ParseException {
        synchronized (sf) {
            return sf.parse(result);
        }
    }

    /**
     * 对有用评论数处理
     *
     * @param site     站点
     * @param userfull 数据
     * @return 数量
     */
    public static Integer gainUserFullNum(String site, String userfull) {
        if (StringUtils.isBlank(userfull)) {
            return null;
        }
        switch (site) {
            case "ES": {
                String res = userfull.split("persona")[0].replaceAll("A", "").replaceAll(" ", "").replaceAll("\\.", "");
                if (res.equals("una")) {
                    return 1;
                } else {
                    return Integer.parseInt(res);
                }
            }
            case "CA": {
                String res = userfull.replace("person", "people").split("people")[0].replaceAll(" ", "").replaceAll("\\,", "");
                if (res.equalsIgnoreCase("one")) {
                    return 1;
                } else {
                    return Integer.parseInt(res);
                }

            }
            case "UK": {
                String res = userfull.replace("person", "people").split("people")[0].replaceAll(" ", "").replaceAll("\\,", "");
                if (res.equalsIgnoreCase("one")) {
                    return 1;
                } else {
                    return Integer.parseInt(res);
                }
            }
            case "US": {
                String res = userfull.replace("person", "people").split("people")[0].replaceAll(" ", "").replaceAll("\\,", "");
                if (res.equalsIgnoreCase("one")) {
                    return 1;
                } else {
                    return Integer.parseInt(res);
                }
            }
            case "FR": {
                String res = userfull.split("personne")[0].replaceAll(" ", "").replace(" ", "");
                if (res.equalsIgnoreCase("Une")) {
                    return 1;
                } else {
                    return Integer.parseInt(res);
                }
            }
            case "JP": {
                return Integer.parseInt(userfull.split("人")[0].replaceAll("\\,", ""));
            }
            case "IT": {
                String res = userfull.replace("persona", "persone").split("persone")[0].replaceAll(" ", "").replaceAll("\\.", "");
                if (res.equalsIgnoreCase("Una")) {
                    return 1;
                } else {
                    return Integer.parseInt(res);
                }
            }
            case "DE": {
                String res = userfull.replace("Person", "Personen").split("Personen")[0].replaceAll(" ", "").replaceAll("\\.", "");
                if (res.equalsIgnoreCase("Eine")) {
                    return 1;
                } else {
                    return Integer.parseInt(res);
                }
            }
        }
        return null;
    }
}
