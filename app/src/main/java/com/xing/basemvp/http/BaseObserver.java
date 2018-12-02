package com.xing.basemvp.http;

import com.xing.basemvp.base.BaseResponse;
import com.xing.basemvp.mvp.IView;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by xinxing.tao on 2018/9/16.
 */

public abstract class BaseObserver<T> extends DisposableObserver<BaseResponse<T>> {

    private IView baseView;

    public BaseObserver(IView baseView) {
        this.baseView = baseView;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (baseView != null) {
            baseView.showLoading();
        }
    }

    @Override
    public void onNext(BaseResponse<T> baseResponse) {
        int errcode = baseResponse.getErrcode();
        String errmsg = baseResponse.getErrmsg();
        if (errcode == 0 || errcode == 200) {
            if (baseView != null) {
                baseView.hideLoading();
            }
            T data = baseResponse.getData();
            // 将服务端获取到的正常数据传递给上层调用方
            onSuccess(data);
        } else {
            onError(new ApiException(errcode, errmsg));
        }
    }

    /**
     * 回调正常数据
     *
     * @param data
     */
    protected abstract void onSuccess(T data);

    /**
     * 异常处理，包括两方面数据：
     * (1) 服务端没有没有返回数据，HttpException，如网络异常，连接超时;
     * (2) 服务端返回了数据，但 errcode!=0,即服务端返回的data为空，如 密码错误,App登陆超时,token失效
     */
    @Override
    public void onError(Throwable e) {
        // 隐藏 loading
        if (baseView != null) {
            baseView.hideLoading();
        }
        ExceptionHandler.handleException(e);
    }

    @Override
    public void onComplete() {
        if (baseView != null) {
            baseView.hideLoading();
        }
    }
}
