package com.example.rxdemo;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private DisposableSubscriber<Bean> disposableSubscriber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit build = new Retrofit.Builder()
                .baseUrl("http://120.27.23.105/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        ApiService apiService = build.create(ApiService.class);
        disposableSubscriber = apiService.get()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableSubscriber<Bean>() {
                    @Override
                    public void onNext(Bean bean) {
                        Log.d("WXW", "onNext" + bean.toString());
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.d("WXW", "onError" + t.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.d("WXW", "onComplete");

                    }
                });
        //被观察者
        Observable<String> stringObservable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("first");
                e.onNext("second");
                e.onNext("third");
                e.onComplete();
            }
        });
        //观察者
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d("MainActivity", "onSubscribe" + d.toString());
            }

            @Override
            public void onNext(String s) {
                Log.d("MainActivity", "onNext" + s.toString());

            }

            @Override
            public void onError(Throwable e) {
                Log.d("MainActivity", "onError" + e.toString());

            }

            @Override
            public void onComplete() {
                Log.d("MainActivity", "onComplete");

            }
        };
        //建立连接
        stringObservable.subscribe(observer);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //防止内存泄漏
        if (disposableSubscriber != null) {
            if (!disposableSubscriber.isDisposed()) {
                disposableSubscriber.dispose();
            }
        }
    }
}
