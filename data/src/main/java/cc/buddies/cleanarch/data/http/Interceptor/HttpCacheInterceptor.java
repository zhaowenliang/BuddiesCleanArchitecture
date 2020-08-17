package cc.buddies.cleanarch.data.http.Interceptor;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HttpCacheInterceptor implements Interceptor {

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        Response originResponse = chain.proceed(request);

        String responseHeaderCacheControl = originResponse.header("Cache-Control");
        if (responseHeaderCacheControl != null) {
            return originResponse;
        }

        // 聚合接口每天只有100条的请求次数，增加强制缓存，以延长使用时长。

        String requestHeaderCacheControl = request.header("Cache-Control");
        if (requestHeaderCacheControl != null) {
            originResponse = originResponse.newBuilder()
                    .removeHeader("pragma")
                    .header("Cache-Control", requestHeaderCacheControl)
                    .build();
        }

        return originResponse;
    }
}
