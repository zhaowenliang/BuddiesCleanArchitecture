package cc.buddies.cleanarch.data.http.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ResponseModel<T> implements Serializable {

    private int code;
    private String message;

    @SerializedName(value = "data", alternate = {"result"})
    private T data;

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
