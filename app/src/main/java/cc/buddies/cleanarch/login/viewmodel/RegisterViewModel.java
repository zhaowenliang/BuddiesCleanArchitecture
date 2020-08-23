package cc.buddies.cleanarch.login.viewmodel;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;

import cc.buddies.cleanarch.common.base.BaseViewModel;
import cc.buddies.cleanarch.common.result.DataResult;
import cc.buddies.cleanarch.domain.interactor.RegisterUseCase;
import cc.buddies.cleanarch.domain.model.UserModel;
import cc.buddies.cleanarch.domain.request.RegisterParams;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RegisterViewModel extends BaseViewModel {

    private RegisterUseCase mRegisterUseCase;

    public MutableLiveData<DataResult<UserModel>> registerLiveData = new MutableLiveData<>();

    @ViewModelInject
    public RegisterViewModel(RegisterUseCase registerUseCase) {
        this.mRegisterUseCase = registerUseCase;
    }

    public void register(RegisterParams params) {
        Disposable subscribe = mRegisterUseCase.execute(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userModel -> registerLiveData.setValue(DataResult.result(userModel)),
                        throwable -> registerLiveData.setValue(DataResult.error(throwable)));

        addDisposable(subscribe);
    }

}
