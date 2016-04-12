package qulei.android.retrofit.demo.rx_retrofit.request;


import okhttp3.ResponseBody;
import qulei.android.retrofit.demo.rx_retrofit.response.GetCarBrandResponse;
import rx.Observable;

public class GetCarBrandRequest extends AbsRequest<ResponseBody, GetCarBrandResponse> {

    private String adapter;

    public GetCarBrandRequest(GetCarBrandResponse response) {
        super(response);
    }

    @Override
    protected Observable<ResponseBody> call() {
        return getServiceApi().getCarBrand(adapter);
    }

    public GetCarBrandRequest setParams(String adapter) {
        this.adapter = adapter;
        return this;
    }

}
