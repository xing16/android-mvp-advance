package com.xing.basemvp;


import android.app.ProgressDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.xing.basemvp.recycler.BaseRecyclerAdapter;
import com.xing.basemvp.recycler.BaseViewHolder;
import com.xing.mvplib.base.BaseMVPActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 一个 view 层 对应多个 presenter 层
 */
public class ArticleMultiActivity extends BaseMVPActivity<ArticleMultiPresenter>
        implements ArticleDetailContract.View, ArticleCountContract.View {

    private ArticleCountPresenter countPresenter;
    private ArticleDetailPresenter detailPresenter;
    private RecyclerView recyclerView;
    private TextView countTxtView;
    private ProgressDialog progressDialog;
    private List<Article> dataList = new ArrayList<>();

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_article_multi;
    }

    @Override
    protected ArticleMultiPresenter createPresenter() {
        ArticleMultiPresenter presenter = new ArticleMultiPresenter(this);
        countPresenter = new ArticleCountPresenter();
        detailPresenter = new ArticleDetailPresenter();
        presenter.addPresenter(countPresenter);
        presenter.addPresenter(detailPresenter);
        return presenter;
    }

    @Override
    protected void initView() {
        recyclerView = findViewById(R.id.rv_article_detail);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        LinearItemDecoration decoration = new LinearItemDecoration.Builder(this)
                .dividerColor(getResources().getColor(R.color.colorDivider))
                .dividerHeight(1)
                .margin(10, 10)
                .orientation(LinearLayoutManager.VERTICAL)
                .build();
        recyclerView.addItemDecoration(decoration);
        countTxtView = findViewById(R.id.tv_count);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
    }

    @Override
    protected void initData() {
        super.initData();
        countPresenter.getArticleCount();
        detailPresenter.getArticleList(0);
    }

    @Override
    public void showLoading() {
        if (progressDialog == null) {
            progressDialog = ProgressDialog.show(this, null, "loading...");
        } else {
            progressDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if (progressDialog != null) {
            progressDialog.hide();
        }
    }

    @Override
    public void showCommentDetails(ArticleResponse response) {
        if (response == null) {
            return;
        }
        List<Article> articles = response.getDatas();
        if (articles == null || articles.size() == 0) {
            return;
        }
        dataList.addAll(articles);
        BaseRecyclerAdapter adapter = new BaseRecyclerAdapter<Article>(dataList, R.layout.item_article_details) {

            @Override
            protected void bind(BaseRecyclerAdapter<Article> adapter, BaseViewHolder baseViewHolder, Article data, int position) {
                baseViewHolder.setText(R.id.tv_title, data.getTitle())
                        .setText(R.id.tv_author, data.getAuthor())
                        .setText(R.id.tv_date, data.getNiceDate());
            }
        };
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showArticleCount(int count) {
        countTxtView.setText("文章(" + count + ")");
    }
}
