package qulei.android.retrofit.demo.rx_retrofit.request;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Headers;
import okhttp3.Request;
import qulei.android.retrofit.demo.LoginInfo;
import qulei.android.retrofit.demo.rx_retrofit.RetrofitFactory;
import qulei.android.retrofit.demo.rx_retrofit.response.LoginResponse;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

public class LoginRequest extends AbsRequest<LoginInfo, LoginResponse> {

    private String userName;
    private String password;

    private String deviceId;
    private String systemType;
    private String mobileFirms;
    private String deviceToken;

    public LoginRequest(LoginResponse response) {
        super(response);
    }

    public LoginRequest setParams(String name, String password) {
        this.userName = name;
        this.password = password;
        return this;
    }

    public LoginRequest setInfo(String deviceId, String systemType,
                                String mobileFirms, String deviceToken) {
        this.deviceId = deviceId;
        this.systemType = systemType;
        this.mobileFirms = mobileFirms;
        this.deviceToken = deviceToken;
        return this;
    }

    @Override
    protected Observable<LoginInfo> call() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    subscriber.onNext(createBody());
                    subscriber.onCompleted();
                } catch (JSONException e) {
                    subscriber.onError(e);
                }
            }
        }).flatMap(new Func1<String, Observable<LoginInfo>>() {
            @Override
            public Observable<LoginInfo> call(String s) {
                return getServiceApi().login(requestBody(s));
            }
        });
    }

    private String createBody() throws JSONException {
        return new JSONObject()
                .put("username", userName)
                .put("password", password)
                .put("deviceId", deviceId)
                .put("systemType", systemType)
                .put("mobileFirms", mobileFirms)
                .put("deviceToken", deviceToken).toString();
    }

    @Override
    public LoginResponse getResponse() {
        response.setResponseBody(
                call().startWith(Observable.create(new Observable.OnSubscribe<LoginInfo>() {
                    @Override
                    public void call(Subscriber<? super LoginInfo> subscriber) {
                        RetrofitFactory.getInstance().setHttpCallBack(callBack);
                        subscriber.onCompleted();
                    }
                })).doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        RetrofitFactory.getInstance().removeHttpCallBack();
                    }
                }).doOnCompleted(new Action0() {
                    @Override
                    public void call() {
                        RetrofitFactory.getInstance().removeHttpCallBack();
                    }
                }));
        return response;
    }

    private RetrofitFactory.HttpCallBack callBack = new RetrofitFactory.HttpCallBack() {
        @Override
        public void onRequestBuilder(Request.Builder builder) {
        }

        @Override
        public void onResponseHeader(Headers mapHeader) {
            getResponse().onResponseHeader(mapHeader);
        }
    };

}
