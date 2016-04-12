package qulei.android.retrofit.demo;

public class UrlConstants {

    /**
     * 正式环境服务器
     */
    private static final String DOMAIN_RELEASE = "";

    /**
     * 测试环境服务器
     */
    private static final String DOMAIN_TEST = "";

    private static final ENVIRONMENT SERVER_ENVIRONMENT = getWebServer();

    private static ENVIRONMENT getWebServer() {
        switch (BuildConfig.web_server) {
            case "debug":
                return ENVIRONMENT.TEST;
            case "release":
                return ENVIRONMENT.RELEASE;
        }
        return ENVIRONMENT.TEST;
    }

    enum ENVIRONMENT {

        RELEASE(DOMAIN_RELEASE), TEST(DOMAIN_TEST);
        String SERVER_ADDRESS; // 服务器地址

        ENVIRONMENT(String address) {
            this.SERVER_ADDRESS = address;
        }
    }

    /**
     * 应用URL
     */
    public static final String URL_APP_SERVER = "http://" + SERVER_ENVIRONMENT.SERVER_ADDRESS;


}
