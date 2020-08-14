package cc.buddies.cleanarch.data.http;

import java.util.concurrent.TimeUnit;

import cc.buddies.component.network.interceptor.HttpEncryptInterceptor;
import cc.buddies.component.network.interceptor.HttpLoggingInterceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class HttpManager {

    private Retrofit mRetrofit;

    private HttpManager() {
    }

    public static HttpManager getInstance() {
        return HttpManager.SingleTonHolder.INSTANCE;
    }

    private static class SingleTonHolder {
        private static final HttpManager INSTANCE = new HttpManager();
    }

    /**
     * 构建OkHttpClient
     *
     * @return HttpManager
     */
    private synchronized OkHttpClient buildHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        // 添加请求头
        // builder.addInterceptor(new HttpHeaderInterceptor());

        // log相关，同时处理了请求响应code!=0情况下移除info信息。
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("BuddiesCleanArch");
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);        // log打印级别，决定了log显示的详细程度
        builder.addInterceptor(loggingInterceptor);                                 // 添加OkHttp默认debug日志

        // 添加加解密拦截器
        builder.addInterceptor(new HttpEncryptInterceptor());

        // 超时时间设置，默认60秒
        long DEFAULT_MILLISECONDS = 60;
        builder.readTimeout(DEFAULT_MILLISECONDS, TimeUnit.SECONDS);      //全局的读取超时时间
        builder.writeTimeout(DEFAULT_MILLISECONDS, TimeUnit.SECONDS);     //全局的写入超时时间
        builder.connectTimeout(DEFAULT_MILLISECONDS, TimeUnit.SECONDS);   //全局的连接超时时间

        // 配置缓存
        // ClearableCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));
        // builder.cookieJar(cookieJar);

        // SSL数字证书配置(使用系统默认证书，不配置，否则会覆盖掉系统默认证书)。
        // 如果想要更安全一些，可以自己服务器签证书，在此配置之后，在网络代理没有配置指定证书的情况下，就不能拦截https请求了。
        // HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory();
        // builder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);
        // builder.hostnameVerifier(HttpsUtils.UnSafeHostnameVerifier);

        return builder.build();
    }

    /**
     * 创建Retrofit请求构建者
     *
     * @param httpClient OkHttpClient
     * @return Retrofit.Builder
     */
    private synchronized Retrofit.Builder createRetrofitBuilder(OkHttpClient httpClient) {
        return new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(httpClient);
    }

    /**
     * 创建Retrofit请求
     *
     * @return Retrofit
     */
    public synchronized Retrofit build() {
        if (mRetrofit == null) {
            mRetrofit = createRetrofitBuilder(buildHttpClient()).build();
        }
        return mRetrofit;
    }

}
