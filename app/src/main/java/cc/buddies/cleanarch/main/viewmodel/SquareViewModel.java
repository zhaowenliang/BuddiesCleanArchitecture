package cc.buddies.cleanarch.main.viewmodel;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import cc.buddies.cleanarch.common.base.BaseViewModel;
import cc.buddies.cleanarch.common.result.DataResult;
import cc.buddies.cleanarch.data.db.dao.PostDao;
import cc.buddies.cleanarch.data.db.relation.PostWithDetail;
import cc.buddies.cleanarch.data.manager.UserManager;
import cc.buddies.cleanarch.domain.interactor.PraisePostUseCase;
import cc.buddies.cleanarch.domain.model.UserModel;
import cc.buddies.cleanarch.domain.request.PraisePostParams;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SquareViewModel extends BaseViewModel {

    private PraisePostUseCase mPraisePostUseCase;

    public final LiveData<PagedList<PostWithDetail>> postsPagedListLiveData;

    public final MutableLiveData<DataResult<Boolean>> praisePostLiveData = new MutableLiveData<>();

    @ViewModelInject
    public SquareViewModel(PostDao postDao, PraisePostUseCase praisePostUseCase) {
        this.mPraisePostUseCase = praisePostUseCase;

        UserModel userInfo = UserManager.getInstance().getUserInfo();
        long uid = userInfo == null ? 0 : userInfo.getUid();
        postsPagedListLiveData = new LivePagedListBuilder<>(postDao.getPostWithAuthorPaging(uid), 20)
                .build();
    }

    /**
     * 点赞/取消赞
     *
     * @param postId      帖子id
     * @param addOrCancel 点赞或取消
     */
    public void praisePost(long postId, long userId, boolean addOrCancel) {
        PraisePostParams clickPostGoodParams = new PraisePostParams(postId, userId, addOrCancel);
        Disposable subscribe = mPraisePostUseCase.execute(clickPostGoodParams)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> praisePostLiveData.setValue(DataResult.result(addOrCancel)),
                        throwable -> praisePostLiveData.setValue(DataResult.error(throwable)));

        addDisposable(subscribe);
    }
}
