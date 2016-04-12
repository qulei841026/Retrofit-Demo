package qulei.android.retrofit.demo.rx_retrofit.response;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import qulei.android.retrofit.demo.CarBrand;
import rx.Observable;
import rx.functions.Func1;


public class GetCarBrandResponse extends AbsResponse<List<CarBrand>, ResponseBody> {

    public GetCarBrandResponse() {

    }

    @Override
    public Observable<List<CarBrand>> toObservable() {
        return getObservable().map(new Func1<ResponseBody, List<CarBrand>>() {
            @Override
            public List<CarBrand> call(ResponseBody responseBody) {
                return doResponse(responseBody);
            }
        });
    }

    protected List<CarBrand> doResponse(ResponseBody body) {
        String response = getResponse(body);
        //格式解析略
        return new ArrayList<>();
    }
}