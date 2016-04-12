package qulei.android.retrofit.demo;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProtocolStatic {

    public static final String COOKIE_KEY_REQ = "Cookie";       //请求Cookie关键字
    public static final String COOKIE_KEY_RES = "Set-Cookie";   //响应CooKie关键字
    public static final String COOKIE_TAG_RES = "tboxUcCookie"; //响应Cookie标签

    public static final List<String> COOKIE_VALUE = Collections.synchronizedList(new ArrayList<String>(1));

    public static void setCookieValue(String cookie) {
        COOKIE_VALUE.clear();
        COOKIE_VALUE.add(cookie);
    }

    //判断Cookie值是否为空
    public static boolean isEmptyCookieValue() {
        return TextUtils.isEmpty(COOKIE_VALUE.size() == 0 ? "" : COOKIE_VALUE.get(0));
    }


}
