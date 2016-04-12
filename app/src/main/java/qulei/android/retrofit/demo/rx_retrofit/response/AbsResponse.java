package qulei.android.retrofit.demo.rx_retrofit.response;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.ResponseBody;
import qulei.android.retrofit.demo.rx_retrofit.RxHttpException;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

public abstract class AbsResponse<R, Body> {

    public abstract Observable<R> toObservable();

    private Observable<Body> observable;

    public void setResponseBody(Observable<Body> observable) {
        this.observable = observable;
    }

    protected Observable<Body> getObservable() {
        return observable.onErrorResumeNext(new Func1<Throwable, Observable<? extends Body>>() {    //异常处理
            @Override
            public Observable<? extends Body> call(final Throwable throwable) {
                return Observable.create(new Observable.OnSubscribe<Body>() {
                    @Override
                    public void call(Subscriber<? super Body> subscriber) {
                        if (throwable instanceof HttpException) {
                            subscriber.onError(new RxHttpException((HttpException) throwable));
                        } else {
                            subscriber.onError(new RxHttpException(throwable));
                        }
                    }
                });
            }
        });
    }

    /**
     * 处理响应消息头
     */
    public void onResponseHeader(Headers mapHeader) {

    }

    /**
     * 请求响应内容转换
     */
    protected String getResponse(ResponseBody body) {
        try {
            return body.string();
        } catch (IOException e) {
            return null;
        }
    }

}
