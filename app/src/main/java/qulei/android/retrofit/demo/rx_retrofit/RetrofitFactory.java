package qulei.android.retrofit.demo.rx_retrofit;

import android.util.Log;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSource;
import qulei.android.retrofit.demo.ProtocolStatic;
import qulei.android.retrofit.demo.UrlConstants;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit 构建工厂类
 */
public class RetrofitFactory {

    public static final String TAG = "retrofit-http";

    private Retrofit retrofit;

    private static class LazyHolder {
        private static final RetrofitFactory factory = new RetrofitFactory();
    }

    private RetrofitFactory() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .client(createClient())     //初始化OkHttp
                .baseUrl(UrlConstants.URL_APP_SERVER)   //Url地址
                .addConverterFactory(GsonConverterFactory.create()) //适配Gson解析
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());  //支持RxJava
        retrofit = builder.build();
    }

    public static RetrofitFactory getInstance() {
        return LazyHolder.factory;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    /**
     * 自定义OkHttpClient
     */
    private OkHttpClient createClient() {
        Interceptor interceptor = new Interceptor() {   //OkHttp拦截器，截获请求消息头和响应消息头
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();
                onRequestBuilder(builder);
                Request request = builder.build();

                //日志打印
                longUrl(request);
                logHeaders(request);

                Response response = chain.proceed(request);

                //日志打印
                logHeaders(response);
                logResponse(response);

                onResponseHeader(response.headers());

                return response;
            }
        };

        return new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)      //超时设置
                .connectTimeout(60, TimeUnit.SECONDS)   //超时设置
                .addInterceptor(interceptor).build();
    }


    protected void onRequestBuilder(Request.Builder builder) {
        if (httpCallBack != null) {
            httpCallBack.onRequestBuilder(builder);
        }

        builder.addHeader("Content-Type", "application/json");
        if (!ProtocolStatic.isEmptyCookieValue()) { //请求头Cookie设置
            builder.addHeader(ProtocolStatic.COOKIE_KEY_REQ, ProtocolStatic.COOKIE_VALUE.get(0));
        }

    }

    protected void onResponseHeader(Headers mapHeader) {
        if (httpCallBack != null) {
            httpCallBack.onResponseHeader(mapHeader);
        }
    }

    private void longUrl(Request request) {
        Log.d(RetrofitFactory.TAG, "request url = " + request.url().toString());
    }

    private void logHeaders(Request request) {
        for (int i = 0, size = request.headers().size(); i < size; i++) {
            Log.d(RetrofitFactory.TAG, "request head = " + "name:" + request.headers().name(i)
                    + ",value:" + request.headers().value(i));
        }
    }

    private void logHeaders(Response response) {
        for (int i = 0, size = response.headers().size(); i < size; i++) {
            Log.d(RetrofitFactory.TAG, "response head = " + "name:" + response.headers().name(i)
                    + ", value : " + response.headers().value(i));
        }
    }

    private void logResponse(Response response) throws IOException {
        Log.d(RetrofitFactory.TAG, "response code =" + response.code());

        BufferedSource source = response.body().source();
        source.request(Long.MAX_VALUE);
        Buffer buffer = source.buffer();
        Log.d(RetrofitFactory.TAG, "response body = " +
                buffer.clone().readString(Charset.forName("UTF-8")));
    }


    public interface HttpCallBack {

        void onRequestBuilder(Request.Builder builder);

        void onResponseHeader(Headers mapHeader);

    }

    private HttpCallBack httpCallBack;

    public void setHttpCallBack(HttpCallBack httpCallBack) {
        this.httpCallBack = httpCallBack;
    }

    public void removeHttpCallBack() {
        this.httpCallBack = null;
    }


}
