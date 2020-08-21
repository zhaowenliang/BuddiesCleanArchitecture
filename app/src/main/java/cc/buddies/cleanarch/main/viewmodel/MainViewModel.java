package cc.buddies.cleanarch.main.viewmodel;

import androidx.lifecycle.MutableLiveData;

import cc.buddies.cleanarch.common.base.BaseViewModel;

public class MainViewModel extends BaseViewModel {

    public MutableLiveData<Boolean> isMainSceneLiveData = new MutableLiveData<>();

    // 更新当前是否时主页场景（隐藏/显示底部导航栏）
    public void notifyMainScene(boolean isMainScene) {
        this.isMainSceneLiveData.setValue(isMainScene);
    }

}
