package cc.buddies.cleanarch.data.http;

public enum ErrorEnum {

    LOGIN_FAILED(1000, "账号或密码错误"),

    ACCOUNT_EXISTS(1100, "当前账号已存在"),

    REGISTER_FAILED(1101, "注册失败"),

    ACCOUNT_NOT_EXISTS(1102, "账号不存在"),

    USER_MODIFY_FAILED(1200, "用户修改失败"),

    POST_RELEASE_FAILED(1300, "发布失败"),

    ;

    ErrorEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
