package cc.buddies.cleanarch.data.http.model;

import java.io.Serializable;

public class NewsResponseModel<T> implements Serializable {

    private int error_code;
    private String reason;
    private ContentModel<T> result;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ContentModel<T> getResult() {
        return result;
    }

    public void setResult(ContentModel<T> result) {
        this.result = result;
    }

    public static class ContentModel<T> implements Serializable {
        private String stat;
        private T data;

        public String getStat() {
            return stat;
        }

        public void setStat(String stat) {
            this.stat = stat;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }
    }

}
