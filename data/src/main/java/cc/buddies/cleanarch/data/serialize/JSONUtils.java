package cc.buddies.cleanarch.data.serialize;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class JSONUtils {

    public static class GSONInstance {
        public static final Gson GSON = new Gson();
    }

    public static boolean isJSON(String text) {
        try {
            JSONTokener jsonTokener = new JSONTokener(text);
            Object o = jsonTokener.nextValue();
            return o instanceof JSONObject || o instanceof JSONArray;
        } catch (JSONException ignore) {
            return false;
        }
    }

    public static boolean isJSONObject(String text) {
        try {
            JSONTokener jsonTokener = new JSONTokener(text);
            Object o = jsonTokener.nextValue();
            return o instanceof JSONObject;
        } catch (JSONException ignore) {
            return false;
        }
    }

    public static boolean isJSONArray(String text) {
        try {
            JSONTokener jsonTokener = new JSONTokener(text);
            Object o = jsonTokener.nextValue();
            return o instanceof JSONArray;
        } catch (JSONException ignore) {
            return false;
        }
    }

    /**
     * 获取JSONObject，可以自己解析里面的字段
     * 使用org.json.JSONObject，不耦合框架。
     *
     * @param text JSON
     * @return org.json.JSONObject
     */
    public static org.json.JSONObject getJSONObject(String text) {
        try {
            return new org.json.JSONObject(text);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * 对象转JSON字符串，List和Map也可以使用
     *
     * @param object 对象
     * @return JSON
     */
    @NonNull
    public static String toJSON(Object object) {
        return GSONInstance.GSON.toJson(object);
    }

    /**
     * JSON转泛型对象
     *
     * @param text  JSON
     * @param clazz 对象类型
     * @param <T>   泛型
     * @return 对象
     */
    public static <T> T toBean(String text, Class<T> clazz) {
        return GSONInstance.GSON.fromJson(text, clazz);
    }

    public static <T> T toType(String text, Type type) {
        return GSONInstance.GSON.fromJson(text, type);
    }

    /**
     * JSON转换为List
     *
     * @param text  JSON
     * @param clazz List泛型类型
     * @param <T>   泛型
     * @return List<T>
     */
    public static <T> List<T> toList(String text, Class<T> clazz) {
        return GSONInstance.GSON.fromJson(text, TypeToken.getParameterized(List.class, clazz).getType());
    }

    /**
     * JSON转换为Map
     *
     * @param text      JSON
     * @param keyType   Map key类型
     * @param valueType Map value类型
     * @param <K>       Map Key Type 泛型
     * @param <V>       Map Value Type 泛型
     * @return Map
     */
    public static <K, V> Map<K, V> toMap(String text, Class<K> keyType, Class<V> valueType) {
        return GSONInstance.GSON.fromJson(text, TypeToken.getParameterized(Map.class, keyType, valueType).getType());
    }

    /**
     * JSON转换为Map，默认key为String类型，value为Object类型。
     *
     * @param text JSON
     * @return Map
     */
    public static Map<String, Object> toMap(String text) {
        return toMap(text, String.class, Object.class);
    }

}
