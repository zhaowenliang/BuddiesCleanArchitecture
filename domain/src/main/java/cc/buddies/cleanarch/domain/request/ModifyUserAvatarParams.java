package cc.buddies.cleanarch.domain.request;

import java.io.Serializable;

public class ModifyUserAvatarParams implements Serializable {

    private long uid;
    private String avatar;

    public ModifyUserAvatarParams() {
    }

    public ModifyUserAvatarParams(long uid, String avatar) {
        this.uid = uid;
        this.avatar = avatar;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
