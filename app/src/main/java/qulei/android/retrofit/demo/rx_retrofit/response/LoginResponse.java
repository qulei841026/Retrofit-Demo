package qulei.android.retrofit.demo.rx_retrofit.response;

import android.text.TextUtils;

import okhttp3.Headers;
import qulei.android.retrofit.demo.LoginInfo;
import qulei.android.retrofit.demo.ProtocolStatic;
import rx.Observable;
import rx.functions.Action1;

public class LoginResponse extends AbsResponse<LoginInfo, LoginInfo> {

    private String cookies;

    public LoginResponse() {

    }

    @Override
    public void onResponseHeader(Headers mapHeader) {
        super.onResponseHeader(mapHeader);
        String cookie = mapHeader.get(ProtocolStatic.COOKIE_KEY_RES);   //响应头cookie处理
        if (!TextUtils.isEmpty(cookie)) {
            String[] array = cookie.split(";");
            for (String value : array) {
                if (value.contains(ProtocolStatic.COOKIE_TAG_RES)) {
                    cookies = value;
                }
            }
        }
    }

    @Override
    public Observable<LoginInfo> toObservable() {
        return getObservable().doOnNext(new Action1<LoginInfo>() {
            @Override
            public void call(LoginInfo loginInfo) {
                loginInfo.cookies = cookies;
                ProtocolStatic.setCookieValue(cookies);
            }
        });
    }


}
