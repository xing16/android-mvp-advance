package com.xing.mvplib.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.xing.basemvp.mvp.IPresenter;
import com.xing.basemvp.mvp.IView;


/**
 * 需要使用 MVP 架构的 Activity 类，继承该 BaseMVPActivity
 *
 * @param <P>
 */
public abstract class BaseMVPActivity<P extends IPresenter> extends AppCompatActivity implements IView {
    protected Context mContext;
    protected P presenter;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        mContext = this;
        presenter = createPresenter();
        // presenter 绑定 view
        if (presenter != null) {
            presenter.attachView(this);
        }
        initView();
        initData();
    }

    protected abstract int getLayoutResId();

    protected abstract P createPresenter();

    protected abstract void initView();

    protected void initData() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Activity 销毁时取消所有的订阅
        if (presenter != null) {
            presenter.detachView();
            presenter = null;
        }
    }


}
