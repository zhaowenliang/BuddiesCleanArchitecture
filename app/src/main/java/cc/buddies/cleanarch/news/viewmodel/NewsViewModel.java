package cc.buddies.cleanarch.news.viewmodel;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import cc.buddies.cleanarch.common.base.BaseViewModel;
import cc.buddies.cleanarch.domain.interactor.GetNewsUseCase;
import cc.buddies.cleanarch.domain.model.NewsModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class NewsViewModel extends BaseViewModel {

    public MutableLiveData<String> stateErrorLiveData = new MutableLiveData<>();

    public MutableLiveData<List<NewsModel>> newsLiveData = new MutableLiveData<>();

    private GetNewsUseCase mGetNewsUseCase;

    @ViewModelInject
    public NewsViewModel(GetNewsUseCase getNewsUseCase) {
        this.mGetNewsUseCase = getNewsUseCase;
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
