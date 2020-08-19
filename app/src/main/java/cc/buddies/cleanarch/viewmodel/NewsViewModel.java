package cc.buddies.cleanarch.viewmodel;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import cc.buddies.cleanarch.BuildConfig;
import cc.buddies.cleanarch.base.BaseViewModel;
import cc.buddies.cleanarch.data.service.NewsRepositoryImpl;
import cc.buddies.cleanarch.domain.interactor.GetNewsUseCase;
import cc.buddies.cleanarch.domain.model.NewsModel;
import cc.buddies.cleanarch.domain.repository.NewsRepository;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class NewsViewModel extends BaseViewModel {

    private GetNewsUseCase mGetNewsUseCase;

    public MutableLiveData<String> stateErrorLiveData = new MutableLiveData<>();

    public MutableLiveData<List<NewsModel>> newsLiveData = new MutableLiveData<>();

    public NewsViewModel() {
        String apiKey = BuildConfig.JUHE_API_KEY;
        NewsRepository newsRepository = new NewsRepositoryImpl(apiKey);
        mGetNewsUseCase = new GetNewsUseCase(newsRepository);
    }

    public void fetchNews(String type) {
        Disposable subscribe = mGetNewsUseCase.execute(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(newsModels -> newsLiveData.setValue(newsModels),
                        throwable -> stateErrorLiveData.setValue(throwable.getMessage()));

        addDisposable(subscribe);
    }

}
