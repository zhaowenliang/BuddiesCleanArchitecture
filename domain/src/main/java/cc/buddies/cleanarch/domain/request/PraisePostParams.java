package cc.buddies.cleanarch.domain.request;

import java.io.Serializable;

public class PraisePostParams implements Serializable {

    private long postId;

    private long userId;

    private boolean addOrCancel;

    public PraisePostParams() {
    }

    public PraisePostParams(long postId, long userId, boolean addOrCancel) {
        this.postId = postId;
        this.userId = userId;
        this.addOrCancel = addOrCancel;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public boolean isAddOrCancel() {
        return addOrCancel;
    }

    public void setAddOrCancel(boolean addOrCancel) {
        this.addOrCancel = addOrCancel;
    }
}
