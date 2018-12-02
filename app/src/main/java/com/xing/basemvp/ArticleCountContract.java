package com.xing.basemvp;


import com.xing.basemvp.mvp.IView;

public interface ArticleCountContract {

    interface View extends IView {
        void showArticleCount(int count);
    }

    interface Presenter {
        void getArticleCount();
    }
}
