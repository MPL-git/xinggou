package com.xinggou.common.utils;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.xinggou.common.exception.BizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

/**
 * http 工具类 获取请求中的参数
 */
public class HttpUtils {

    private final static Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    /**
     * 将URL的参数和body参数合并
     */
    public static SortedMap<String, Object> sortParams(Map<String, Object> param) throws IOException {
        SortedMap<String, Object> result = new TreeMap<>();
        for (Map.Entry entry : param.entrySet()) {
            result.put((String) entry.getKey(), entry.getValue());
        }
        return result;
    }

    /**
     * 将URL的参数和body参数合并
     */
    public static SortedMap<String, Object> getAllParams(HttpServletRequest request) throws IOException {
        SortedMap<String, Object> result = new TreeMap<>();
        //获取URL上的参数
        Map<String, String> urlParams = getUrlParams(request);
        for (Map.Entry entry : urlParams.entrySet()) {
            result.put((String) entry.getKey(), entry.getValue());
        }
        Map<String, Object> allRequestParam = new HashMap<>(16);
        // get请求不需要拿body参数
        if (!HttpMethod.GET.name().equals(request.getMethod())) {
            allRequestParam = getAllRequestParam(request);
        }
        //将URL的参数和body参数进行合并
        if (allRequestParam != null) {
            result.putAll(allRequestParam);
            /*for (Map.Entry entry : allRequestParam.entrySet()) {
                result.put((String) entry.getKey(), (String) entry.getValue());
            }*/
        }
        return result;
    }

    /**
     * 获取 Body 参数
     */
    public static Map<String, Object> getAllRequestParam(final HttpServletRequest request) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String str = "";
        StringBuilder wholeStr = new StringBuilder();
        //一行一行的读取body体里面的内容；
        while ((str = reader.readLine()) != null) {
            wholeStr.append(str);
        }

        return JSONObject.parseObject(wholeStr.toString(), Map.class);
    }

    /**
     * 将URL请求参数转换成Map
     */
    public static Map<String, String> getUrlParams(HttpServletRequest request) {
        String queryString = request.getQueryString();
        if (StrKit.isBlank(queryString)) return new HashMap<>(16);


        String param = "";
        try {
            param = URLDecoder.decode(queryString, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Map<String, String> result = new HashMap<>(16);
        String[] params = param.split("&");
        for (String s : params) {
            int index = s.indexOf("=");
            result.put(s.substring(0, index), s.substring(index + 1));
        }
        return result;
    }


    /**
     * 取得请求的参数
     *
     * @param request
     * @return
     */
    public static String getRequestUrl(HttpServletRequest request) {
        StringBuffer requestUrl = request.getRequestURL();
        String queryString = request.getQueryString();

        if (queryString != null) {
            requestUrl.append("?" + queryString);
        }
        try {
            return URLEncoder.encode(requestUrl.toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("对url进行编码遇到错误,将被忽略", e);
            return null;
        }
    }

    /**
     * 拆分请求参数
     *
     * @param queryParas 形如a=b&c=d
     * @return
     */
    public static Map<String, Object> splitQueryParas(String queryParas) {
        Map<String, Object> queryParaMap = new HashMap<String, Object>();
        if (StrKit.isBlank(queryParas)) {
            return queryParaMap;
        }

        String[] nameValuePairs = queryParas.split("&");
        for (String nameValuePair : nameValuePairs) {
            String[] nameValueArray = nameValuePair.split("=");
            if (nameValueArray.length != 2) {
                // throw new IllegalArgumentException("queryParas is wrong");
                continue;
            }

            queryParaMap.put(nameValueArray[0].trim(), nameValueArray[1].trim());
        }

        return queryParaMap;
    }

    /**
     * 尝试通过get调用接口
     *
     * @param apiUrl
     * @param paramMap
     * @param totalRetryCount 重试次数
     * @return
     */
    public static JSONObject tryGetJson(String apiUrl, Map<String, Object> paramMap, int totalRetryCount) {
        int retriedCount = 1;
        Map<String, String> params = new HashMap<String, String>();
        if (paramMap != null) {
            Set<String> keySet = paramMap.keySet();
            for (String key : keySet) {
                params.put(key, paramMap.get(key) + "");
            }
        }
        while (true) {
            try {
                logger.info("正在进行第[" + retriedCount + "]次调用[" + apiUrl + "], 参数：" + JSONObject.toJSONString(paramMap));
                String response = HttpKit.get(apiUrl, params);
                logger.info(response);
                return JSONObject.parseObject(response);
            } catch (JSONException e) {
                logger.error("GET调用:" + apiUrl + " 共:" + retriedCount + " 次，请求返回的结果不是JSON串");
                throw new BizException("系统繁忙，请稍后再试");
            } catch (Exception e) {
                logger.warn("第" + retriedCount + "次GET调用:" + apiUrl + " 遇到错误，正在重试", e);
                retriedCount++;

                if (retriedCount > totalRetryCount) {
                    logger.error("GET调用:" + apiUrl + " 共:" + retriedCount + " 次，没有得到正确的返回值");
                    throw new BizException("系统繁忙，请稍后再试");
                }
            }
        }
    }

    /**
     * 尝试通过get调用接口
     *
     * @param apiUrl
     * @param paramMap
     * @param data
     * @param totalRetryCount
     * @return
     */
    public static JSONObject tryPostJson(String apiUrl, Map<String, Object> paramMap, String data, int totalRetryCount) {
        int retriedCount = 1;
        Map<String, String> params = new HashMap<String, String>();
        if (paramMap != null) {
            Set<String> keySet = paramMap.keySet();
            for (String key : keySet) {
                params.put(key, paramMap.get(key) + "");
            }
        }
        while (true) {
            try {
                logger.info("正在进行第[" + retriedCount + "]次调用[" + apiUrl + "], 参数：" + JSONObject.toJSONString(paramMap));
                String response = HttpKit.post(apiUrl, params, data);
                logger.info(response);
                return JSONObject.parseObject(response);
            } catch (JSONException e) {
                logger.error("POST调用:" + apiUrl + " 共:" + retriedCount + " 次，请求返回的结果不是JSON串");
                throw new BizException("系统繁忙，请稍后再试");
            } catch (Exception e) {
                logger.warn("第" + retriedCount + "次POST调用:" + apiUrl + " 遇到错误，正在重试", e);
                retriedCount++;

                if (retriedCount > totalRetryCount) {
                    logger.error("POST调用:" + apiUrl + " 共:" + retriedCount + " 次，没有得到正确的返回值");
                    throw new BizException("系统繁忙，请稍后再试");
                }
            }
        }
    }

}