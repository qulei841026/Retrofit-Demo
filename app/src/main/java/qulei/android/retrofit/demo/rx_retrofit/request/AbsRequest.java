package qulei.android.retrofit.demo.rx_retrofit.request;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import qulei.android.retrofit.demo.rx_retrofit.RetrofitFactory;
import qulei.android.retrofit.demo.rx_retrofit.ServiceApi;
import qulei.android.retrofit.demo.rx_retrofit.response.AbsResponse;
import retrofit2.Retrofit;
import rx.Observable;

public abstract class AbsRequest<Body, Res extends AbsResponse<?, Body>> {

    static ServiceApi API;
    protected Res response;
    private Retrofit retrofit;

    AbsRequest(Res response) {    //原项目使用dagger依赖注入的方式，这里不做Dagger框架的搭建了
        this.response = response;
        this.retrofit = RetrofitFactory.getInstance().getRetrofit();
    }

    protected synchronized ServiceApi getServiceApi() {
        if (API == null) {
            API = retrofit.create(ServiceApi.class);
        }
        return API;
    }

    /**
     * JSon格式的RequestBody封装
     */
    protected RequestBody requestBody(String body) {
        return RequestBody.create(MediaType.parse("application/json"), body);
    }

    /**
     * 请求体封装
     */
    protected abstract Observable<Body> call();

    /**
     * 获得请求相应类
     */
    public Res getResponse() {
        response.setResponseBody(call());
        return response;
    }

}
