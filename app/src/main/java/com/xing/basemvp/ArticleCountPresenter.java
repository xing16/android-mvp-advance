package com.xing.basemvp;


import com.xing.basemvp.mvp.BasePresenter;

public class ArticleCountPresenter extends BasePresenter<ArticleCountContract.View>
        implements ArticleCountContract.Presenter {

    @Override
    public void getArticleCount() {
        if (isViewAttached()) {
            getView().showArticleCount(22);
        }
    }
}
