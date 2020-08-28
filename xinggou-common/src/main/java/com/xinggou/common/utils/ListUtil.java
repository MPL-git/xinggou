package com.xinggou.common.utils;

import com.alibaba.fastjson.JSONArray;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * List工具类
 */
public class ListUtil {

    /**
     * 前端经常会传过来的多个状态转List
     *
     * @param str [10,20]
     * @return
     */
    public static <T> List<T> strToList(String str, Class<T> modelClass) {
        if (StrKit.isBlank(str) || str.equals("[]")) {
            return new ArrayList<>();
        }

        if (!str.startsWith("[")) {
            str = "[" + str + "]";
        }

        return JSONArray.parseArray(str, modelClass);
    }

    /**
     * 深度克隆
     */
    public static <T> List<T> deepCopyList(List<T> src) {
        List<T> dest = null;
        try {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteOut);
            out.writeObject(src);
            ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
            ObjectInputStream in = new ObjectInputStream(byteIn);
            dest = (List<T>) in.readObject();
        } catch (IOException e) {

        } catch (ClassNotFoundException e) {

        }
        return dest;
    }


}
