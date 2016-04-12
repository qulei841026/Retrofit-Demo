package qulei.android.retrofit.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.List;

import qulei.android.retrofit.demo.rx_retrofit.request.GetCarBrandRequest;
import qulei.android.retrofit.demo.rx_retrofit.request.LoginRequest;
import qulei.android.retrofit.demo.rx_retrofit.response.GetCarBrandResponse;
import qulei.android.retrofit.demo.rx_retrofit.response.LoginResponse;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Override
    @SuppressWarnings("ConstantConditions")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnPost).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginResponse response = new LoginResponse();
                LoginRequest request = new LoginRequest(response);
                request.setParams("", "");  //d0970714757783e6cf17b26fb8e2298f
                request.getResponse().toObservable().subscribeOn(Schedulers.io())
                        .subscribe(new Subscriber<LoginInfo>() {
                            @Override
                            public void onCompleted() {
                                Log.d("MainActivity", "onCompleted");
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d("MainActivity", "onError :" + e.toString());
                            }

                            @Override
                            public void onNext(LoginInfo loginInfo) {
                                Log.d("MainActivity", "onNext : " + loginInfo.toString());
                            }
                        });
            }
        });


        findViewById(R.id.btnGet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetCarBrandResponse response = new GetCarBrandResponse();
                GetCarBrandRequest request = new GetCarBrandRequest(response);
                request.setParams("true");
                request.getResponse().toObservable().subscribeOn(Schedulers.io())
                        .subscribe(new Subscriber<List<CarBrand>>() {
                            @Override
                            public void onCompleted() {
                                Log.d("MainActivity", "onCompleted");
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d("MainActivity", "onError :" + e.toString());
                            }

                            @Override
                            public void onNext(List<CarBrand> carBrands) {
                                Log.d("MainActivity", "onNext : " + carBrands.toString());
                            }
                        });
            }
        });
    }
}
