package cc.buddies.cleanarch.viewmodel;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import cc.buddies.cleanarch.BuildConfig;
import cc.buddies.cleanarch.base.BaseViewModel;
import cc.buddies.cleanarch.data.service.NewsRepositoryImpl;
import cc.buddies.cleanarch.domain.interactor.GetNewsUseCase;
import cc.buddies.cleanarch.domain.model.NewsModel;
import cc.buddies.cleanarch.domain.repository.NewsRepository;
import cc.buddies.component.common.provider.CommonContextProvider;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class NewsViewModel extends BaseViewModel {

    private GetNewsUseCase mGetNewsUseCase;

    private MutableLiveData<List<NewsModel>> mNewsLiveData = new MutableLiveData<>();

    public NewsViewModel() {
        String apiKey = BuildConfig.JUHE_API_KEY;
        NewsRepository newsRepository = new NewsRepositoryImpl(apiKey);
        mGetNewsUseCase = new GetNewsUseCase(newsRepository);
    }

    @NonNull
    public MutableLiveData<List<NewsModel>> fetchNews(String type) {
        Disposable subscribe = mGetNewsUseCase.execute(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(newsModels ->
                                mNewsLiveData.setValue(newsModels),
                        throwable ->
                                Toast.makeText(CommonContextProvider.getApplication(), throwable.getMessage(), Toast.LENGTH_SHORT).show());

        addDisposable(subscribe);
        return mNewsLiveData;
    }

}
