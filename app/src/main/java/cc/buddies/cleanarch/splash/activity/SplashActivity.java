package cc.buddies.cleanarch.splash.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

import cc.buddies.cleanarch.R;
import cc.buddies.cleanarch.login.activity.LoginActivity;
import cc.buddies.cleanarch.MainActivity;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SplashActivity extends AppCompatActivity {

    // 页面延迟基本时长ms
    private static final long DELAY_TIME = 2000;

    // 初始化任务订阅容器
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    private long waitTime = 0;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();
    }

    private void init() {
        this.mCompositeDisposable.add(createLoginCompletable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::startMain, throwable -> startLogin()));
    }

    // 创建登录被观察者
    private Completable createLoginCompletable() {
        return Completable.create(emitter -> {
            waitUntilFinish();

            // 此处可判断是否登录
            // 1. 已登录 -> emitter.onComplete();
            // 2. 未登录 -> emitter.onError(new Exception());

            emitter.onComplete();
        });
    }

    @WorkerThread
    private void waitUntilFinish() {
        long beforeTime = System.currentTimeMillis();

        // do something, waiting until finish.

        this.waitTime = System.currentTimeMillis() - beforeTime;
    }

    private void startLogin() {
        this.mCompositeDisposable.add(Completable
                .timer(getDelayTime(), TimeUnit.MILLISECONDS)
                .doAfterTerminate(this::finish)
                .subscribe(() -> startActivity(new Intent(SplashActivity.this, LoginActivity.class))));
    }

    private void startMain() {
        this.mCompositeDisposable.add(Completable
                .timer(getDelayTime(), TimeUnit.MILLISECONDS)
                .doAfterTerminate(this::finish)
                .subscribe(() -> startActivity(new Intent(SplashActivity.this, MainActivity.class))));
    }

    private long getDelayTime() {
        return DELAY_TIME > this.waitTime ? DELAY_TIME - this.waitTime : 0;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (this.mCompositeDisposable != null && !this.mCompositeDisposable.isDisposed()) {
            this.mCompositeDisposable.clear();
        }
    }
}
