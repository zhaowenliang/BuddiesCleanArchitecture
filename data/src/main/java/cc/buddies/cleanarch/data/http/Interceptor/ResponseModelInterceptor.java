package cc.buddies.cleanarch.data.http.Interceptor;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ResponseModelInterceptor implements Interceptor {

    private static final String TRANS_HOST = "v.juhe.cn";

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        String host = request.url().host();
        // 不是指定host请求，不需要转化数据模型
        if (!TRANS_HOST.equalsIgnoreCase(host)) {
            return chain.proceed(request);
        }

        // 匹配上指定host请求，转化相应数据模型
        Response response = chain.proceed(chain.request());
        return transResponse(response);
    }

    private Response transResponse(Response response) {
        Response.Builder builder = response.newBuilder();
        Response clone = builder.build();
        ResponseBody responseBody = clone.body();

        if (responseBody == null) return response;

        try {
            MediaType mediaType = responseBody.contentType();
            if (isJSON(mediaType)) {
                String string = responseBody.string();
                String result = transData(string);

                ResponseBody resultBody = ResponseBody.create(responseBody.contentType(), result);
                return response.newBuilder().body(resultBody).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    private String transData(String data) {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(data);
        } catch (JSONException e) {
            e.printStackTrace();
            return data;
        }

        JSONObject transObject = new JSONObject();

        try {
            if (!jsonObject.has("code")) {
                transObject.put("code", jsonObject.optInt("error_code", 0));
            } else {
                transObject.put("code", jsonObject.optInt("code", 0));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            if (!jsonObject.has("message")) {
                transObject.put("message", jsonObject.optString("reason"));
            } else {
                transObject.put("message", jsonObject.optString("message"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            Object optData;

            if (!jsonObject.has("data")) {
                final String result = jsonObject.optString("result");
                if ("".equals(result) || "null".equalsIgnoreCase(result)) {
                    optData = null;
                } else {
                    JSONObject resultObject = new JSONObject(result);
                    optData = resultObject.has("data") ? resultObject.opt("data") : resultObject;
                }
            } else {
                optData = jsonObject.opt("data");
            }

            transObject.put("data", optData);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return transObject.toString();
    }

    private boolean isJSON(MediaType mediaType) {
        if (mediaType != null) {
            String subtype = mediaType.subtype().toLowerCase();
            return subtype.contains("json");
        } else {
            return false;
        }
    }

}
