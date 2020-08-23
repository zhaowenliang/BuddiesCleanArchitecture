package cc.buddies.cleanarch.login.viewmodel;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;

import cc.buddies.cleanarch.common.base.BaseViewModel;
import cc.buddies.cleanarch.common.result.DataResult;
import cc.buddies.cleanarch.data.manager.UserManager;
import cc.buddies.cleanarch.domain.interactor.LoginUseCase;
import cc.buddies.cleanarch.domain.request.LoginParams;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginViewModel extends BaseViewModel {

    public final MutableLiveData<DataResult<Boolean>> authenticationState = new MutableLiveData<>();

    private LoginUseCase mLoginUseCase;

    @ViewModelInject
    public LoginViewModel(LoginUseCase loginUseCase) {
        // In this example, the user is always unauthenticated when MainActivity is launched
        authenticationState.setValue(DataResult.result(false));

        this.mLoginUseCase = loginUseCase;
    }

    public void authenticate(String username, String password) {
        Disposable subscribe = mLoginUseCase.execute(new LoginParams(username, password))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userModel -> {
                    UserManager.getInstance().saveUserInfo(userModel);
                    authenticationState.setValue(DataResult.result(true));
                }, throwable -> {
                    authenticationState.setValue(DataResult.error(throwable));
                });

        addDisposable(subscribe);
    }

}
