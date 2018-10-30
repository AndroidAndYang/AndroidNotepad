package com.seabig.common.http;

import com.seabig.common.BuildConfig;
import com.seabig.common.datamgr.AppConstant;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {

    private static Retrofit retrofit;

    public static <T> T getApi(Class<T> clazz) {
        if (retrofit == null) {
            synchronized (RetrofitUtil.class) {
                if (retrofit == null) {
                    retrofit = new Retrofit.Builder()
                            .baseUrl(AppConstant.BASE_URL)
                            // 使用GSon作为数据转换器
                            .addConverterFactory(GsonConverterFactory.create())
                            // 使用RxJava作为回调适配器
                            .client(provideOkHttpClient())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .client(new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS).build())
                            .build();
                }
            }
        }
        return retrofit.create(clazz);
    }

    /**
     * 配置OkHttp
     *
     * @return OkHttpClient
     */
    private static OkHttpClient provideOkHttpClient() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        if (BuildConfig.DEBUG) {
            // log用拦截器
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            // 开发模式记录整个body，否则只记录基本信息如返回200，http协议版本等
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logging);
        }
        // 如果使用到HTTPS，我们需要创建SSLSocketFactory，并设置到client
        //        SSLSocketFactory sslSocketFactory = null;
        return builder
                // 连接超时时间设置
                .connectTimeout(10, TimeUnit.SECONDS)
                // 读取超时时间设置
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
    }
}
