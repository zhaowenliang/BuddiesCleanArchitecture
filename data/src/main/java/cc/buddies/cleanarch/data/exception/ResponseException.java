package cc.buddies.cleanarch.data.exception;

public class ResponseException extends Exception {

    private int code;

    public ResponseException(final int code, final String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
