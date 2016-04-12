package qulei.android.retrofit.demo;

import com.google.gson.annotations.SerializedName;

public class LoginInfo {

    public String cookies;

    public String imei;
    public String driverCode;

    public UserInfo user;
    public SettingInfo setting;

    public String brandId;
    public String modelId;
    public String styleId;

    @SerializedName("nowtime")
    public long loginTime;

    public static class UserInfo {
        public String customerId;
        public String passportId;
        public String mobile;
        public String email;
    }

    public static class SettingInfo {
        public String autoRiseWindow;
        public String autoLockDoor;
        public String whistle;
        public String flash;
        public String security;
    }


    @Override
    public String toString() {
        return "LoginInfo{" +
                "cookies='" + cookies + '\'' +
                ", imei='" + imei + '\'' +
                ", driverCode='" + driverCode + '\'' +
                ", user=" + user +
                ", setting=" + setting +
                ", brandId='" + brandId + '\'' +
                ", modelId='" + modelId + '\'' +
                ", styleId='" + styleId + '\'' +
                ", loginTime=" + loginTime +
                '}';
    }
}
