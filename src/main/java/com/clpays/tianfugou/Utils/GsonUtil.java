package com.clpays.tianfugou.Utils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Name: GsonUtil
 * Author: Dusky
 * QQ: 1042932843
 * Comment: 方便使用的Gson工具类
 * Date: 2017-04-11 22:25
 */

public class GsonUtil {

    public static <T> T parseJsonWithGson(String jsonData, Class<T> type) {
        Gson gson = new Gson();
        try {
            T result = gson.fromJson(jsonData, type);
            return result;
        }catch (JsonSyntaxException e){

        }
        return null;

    }
    public static <T> List<T> parseJsonArrayWithGson(String jsonData, Class<T> type) {
        Gson gson = new Gson();
        List<T> result = gson.fromJson(jsonData, new TypeToken<List<T>>() {
        }.getType());
        return result;
    }
}
