package qulei.android.retrofit.demo.rx_retrofit;


import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import qulei.android.retrofit.demo.LoginInfo;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface ServiceApi {

    /**
     * 登录
     */
    @POST("")
    Observable<LoginInfo> login(@Body RequestBody body);

    /**
     * 获取汽车品牌列表
     */
    @GET("")
    Observable<ResponseBody> getCarBrand(@Query("adapter") String adapter);


}
