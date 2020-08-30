package cc.buddies.cleanarch.splash.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

import cc.buddies.cleanarch.R;
import cc.buddies.cleanarch.main.activity.MainActivity;
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
                .subscribe(this::startMain, throwable -> Log.e("启动页", "初始化失败")));
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

        // 添加测试数据
//        if (UserManager.getInstance().isLogin()) {
//            PostDao postDao = DBDaoFactory.getPostDao(getApplicationContext());
//            PostPraiseDao postPraiseDao = DBDaoFactory.getPostPraiseDao(getApplicationContext());
//            PostRepository postRepository = new PostRepositoryImpl(postDao, postPraiseDao);
//            ReleasePostUseCase releasePostUseCase = new ReleasePostUseCase(postRepository);
//
//            UserModel userInfo = UserManager.getInstance().getUserInfo();
//            long uid = userInfo == null ? 0 : userInfo.getUid();
//
//            List<String> images = new ArrayList<>();
//            images.add("https://03imgmini.eastday.com/mobile/20200830/20200830010352_bba0bd88a66c45b504843b0d8c3cbca1_2_mwpm_03200403.jpg");
//            images.add("https://09imgmini.eastday.com/mobile/20200830/20200830010242_817f649a9669e2cbd3c7a4757c2fb30b_10_mwpm_03200403.jpg");
////            images.add("https://00imgmini.eastday.com/mobile/20200830/20200830010241_68b6c156023c8ef65c40445f2330fb58_6_mwpm_03200403.jpg");
//
//            ReleasePostParams releasePostParams = new ReleasePostParams();
//            releasePostParams.setUserId(uid);
//            releasePostParams.setDescription("找朋友PY了台Z17的全新机，拿到手的时候被新机膜包的严严实实的，讲道理个人觉得这个正面外观放到现在也还是挺耐看的。整体显示效果还可以，彩虹纹感知不强。刷了个Los,再配上一代神U 835。将就着用用吧。毕竟这玩意只要五百块钱还要什么自行车[doge]...");
//            releasePostParams.setImages(images);
//
//            Disposable subscribe = releasePostUseCase.execute(releasePostParams)
//                    .observeOn(Schedulers.io())
//                    .subscribeOn(Schedulers.io())
//                    .subscribe(postModel -> Log.d("aaaa", "添加测试帖子成功: " + JSONUtils.toJSON(postModel)),
//                            throwable -> Log.d("aaaa", "添加测试帖子失败: " + throwable.getMessage()));
//
//            this.mCompositeDisposable.add(subscribe);
//        }

        this.waitTime = System.currentTimeMillis() - beforeTime;
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
