package com.xing.basemvp;


import com.xing.basemvp.mvp.BasePresenter;

public class MePresenter extends BasePresenter<MeContract.View> implements MeContract.Presenter {
    @Override
    public void getCount() {
        if (isViewAttached()) {
            getView().showCount();
        }
    }
}
