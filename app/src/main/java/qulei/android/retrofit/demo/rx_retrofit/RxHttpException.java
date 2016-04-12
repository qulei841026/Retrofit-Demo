package qulei.android.retrofit.demo.rx_retrofit;

import retrofit2.adapter.rxjava.HttpException;

/**
 * Http异常处理类
 * <p/>
 * <p>针对平台业务异常处理，各个平台的处理方式不同，具体内容不做实现。</p>
 */
public class RxHttpException extends Exception {

    /**
     * 响应异常
     * <p>服务器一般响应500,404之类的错误</p>
     */
    public RxHttpException(HttpException e) {
        super(e);
        //服务器一般响应500直接的错误码
    }

    /**
     * 请求异常和协议封装异常
     * <p>连接服务器异常,封装语法异常等</p>
     */
    public RxHttpException(Throwable t) {
        super(t);
    }
}
