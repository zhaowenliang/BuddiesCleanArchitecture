package cc.buddies.cleanarch.domain.request;

import java.io.Serializable;

public class ModifyUserNicknameParams implements Serializable {

    private long uid;
    private String nickname;

    public ModifyUserNicknameParams() {
    }

    public ModifyUserNicknameParams(long uid, String nickname) {
        this.uid = uid;
        this.nickname = nickname;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
