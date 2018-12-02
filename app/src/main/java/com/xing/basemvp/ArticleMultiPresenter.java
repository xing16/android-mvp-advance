package com.xing.basemvp;


import com.xing.basemvp.mvp.BasePresenter;
import com.xing.basemvp.mvp.IPresenter;
import com.xing.basemvp.mvp.IView;

import java.util.ArrayList;
import java.util.List;

public class ArticleMultiPresenter<V extends IView> extends BasePresenter<V> {

    private V view;
    private List<IPresenter> presenterList = new ArrayList<>();

    public ArticleMultiPresenter(V view) {
        this.view = view;
    }

    public final <P extends IPresenter<V>> void addPresenter(P... presenter) {
        for (P p : presenter) {
            p.attachView(view);
            presenterList.add(p);
        }
    }

    @Override
    public void detachView() {
        super.detachView();
        for (IPresenter p : presenterList) {
            p.detachView();
        }
    }
}
