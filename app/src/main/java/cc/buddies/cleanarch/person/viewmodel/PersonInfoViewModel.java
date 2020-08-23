package cc.buddies.cleanarch.person.viewmodel;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;

import cc.buddies.cleanarch.common.base.BaseViewModel;
import cc.buddies.cleanarch.common.result.DataResult;
import cc.buddies.cleanarch.data.manager.UserManager;
import cc.buddies.cleanarch.domain.interactor.ModifyUserAvatarUseCase;
import cc.buddies.cleanarch.domain.interactor.ModifyUserNicknameUseCase;
import cc.buddies.cleanarch.domain.model.UserModel;
import cc.buddies.cleanarch.domain.request.ModifyUserAvatarParams;
import cc.buddies.cleanarch.domain.request.ModifyUserNicknameParams;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PersonInfoViewModel extends BaseViewModel {

    public MutableLiveData<DataResult<String>> modifyUserAvatarLiveData = new MutableLiveData<>();
    public MutableLiveData<DataResult<String>> modifyUserNicknameLiveData = new MutableLiveData<>();

    private ModifyUserAvatarUseCase mModifyUserAvatarUseCase;
    private ModifyUserNicknameUseCase mModifyUserNicknameUseCase;

    @ViewModelInject
    public PersonInfoViewModel(ModifyUserAvatarUseCase modifyUserAvatarUseCase, ModifyUserNicknameUseCase modifyUserNicknameUseCase) {
        this.mModifyUserAvatarUseCase = modifyUserAvatarUseCase;
        this.mModifyUserNicknameUseCase = modifyUserNicknameUseCase;
    }

    // 修改用户头像
    public void modifyUserAvatar(String url) {
        UserModel userInfo = UserManager.getInstance().getUserInfo();
        long uid = userInfo != null ? userInfo.getUid() : 0;

        ModifyUserAvatarParams params = new ModifyUserAvatarParams(uid, url);
        Disposable subscribe = this.mModifyUserAvatarUseCase.execute(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> modifyUserAvatarLiveData.setValue(DataResult.result(result)),
                        throwable -> modifyUserAvatarLiveData.setValue(DataResult.error(throwable))
                );

        addDisposable(subscribe);
    }

    // 修改用户昵称
    public void modifyUserNickname(String nickname) {
        UserModel userInfo = UserManager.getInstance().getUserInfo();
        long uid = userInfo != null ? userInfo.getUid() : 0;

        ModifyUserNicknameParams params = new ModifyUserNicknameParams(uid, nickname);
        Disposable subscribe = this.mModifyUserNicknameUseCase.execute(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> modifyUserNicknameLiveData.setValue(DataResult.result(result)),
                        throwable -> modifyUserNicknameLiveData.setValue(DataResult.error(throwable))
                );

        addDisposable(subscribe);
    }
}
