package cc.buddies.cleanarch.domain.request;

import java.io.Serializable;

public class LoginParams implements Serializable {

    private String account;

    private String password;

    public LoginParams() {
    }

    public LoginParams(String account, String password) {
        this.account = account;
        this.password = password;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
