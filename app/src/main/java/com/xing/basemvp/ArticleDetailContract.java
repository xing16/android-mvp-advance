package com.xing.basemvp;


import com.xing.basemvp.mvp.IView;

public interface ArticleDetailContract {

    interface View extends IView {
        void showCommentDetails(ArticleResponse response);
    }

    interface Presenter {
        void getArticleList(int curPage);
    }
}
