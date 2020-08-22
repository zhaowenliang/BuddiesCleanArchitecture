package cc.buddies.cleanarch.common.result;

public class DataResult<T> {

    public T data;

    public Throwable throwable;

    public DataResult() {
    }

    public DataResult(T data, Throwable throwable) {
        this.data = data;
        this.throwable = throwable;
    }

    public static <T> DataResult<T> empty() {
        return new DataResult<>(null, null);
    }

    public static <T> DataResult<T> result(T model) {
        return new DataResult<>(model, null);
    }

    public static <T> DataResult<T> error(Throwable throwable) {
        return new DataResult<>(null, throwable);
    }

}
