package com.xing.basemvp;

import android.widget.TextView;

import com.xing.mvplib.base.BaseMVPActivity;

/**
 * 不同的 view 层复用同一个 presenter
 * 当一个界面所需的 presenter 逻辑已经在其他页面中定义过了，这时这个页面就可以复用已经定义的 presenter 中的接口
 */
public class MeActivity extends BaseMVPActivity<ArticleCountPresenter>
        implements ArticleCountContract.View {

    private TextView countTxtView;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_me;
    }

    @Override
    protected ArticleCountPresenter createPresenter() {
        return new ArticleCountPresenter();
    }

    @Override
    protected void initView() {
        countTxtView = findViewById(R.id.tv_article_count);
    }

    @Override
    protected void initData() {
        super.initData();
        presenter.getArticleCount();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showArticleCount(int count) {
        countTxtView.setText(String.valueOf(count));
    }
}
