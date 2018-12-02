package com.xing.basemvp;


import com.xing.basemvp.http.BaseObserver;
import com.xing.basemvp.mvp.BasePresenter;

public class ArticleDetailPresenter extends BasePresenter<ArticleDetailContract.View>
        implements ArticleDetailContract.Presenter {

    @Override
    public void getArticleList(int curPage) {
        addSubscribe(apiService.getArticleList(curPage), new BaseObserver<ArticleResponse>(getView()) {

            @Override
            protected void onSuccess(ArticleResponse response) {
                if (isViewAttached()) {
                    getView().showCommentDetails(response);
                }
            }
        });
    }
}
