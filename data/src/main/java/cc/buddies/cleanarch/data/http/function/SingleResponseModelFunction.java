package cc.buddies.cleanarch.data.http.function;

import cc.buddies.cleanarch.data.exception.ResponseException;
import cc.buddies.cleanarch.data.http.model.ResponseModel;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.functions.Function;

/**
 * 转换RxJava观察者Function
 * <p>该方法使用Single.error抛出了自定义异常，所以在订阅的时候需要订阅error处理方法</p>
 *
 * @param <T> 观察数据泛型
 */
public class SingleResponseModelFunction<T> implements Function<ResponseModel<T>, SingleSource<T>> {

    @Override
    public SingleSource<T> apply(final ResponseModel<T> responseModel) {
        if (responseModel == null) {
            return Single.error(new Throwable("数据格式不正确"));
        }

        if (responseModel.getErrorCode() != 0) {
            int code = responseModel.getErrorCode();
            String message = responseModel.getErrorMsg();
            return Single.error(new ResponseException(code, message));
        }

        return Single.just(responseModel.getData());
    }
}
