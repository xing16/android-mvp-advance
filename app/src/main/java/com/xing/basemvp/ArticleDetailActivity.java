package com.xing.basemvp;

import android.app.ProgressDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.xing.basemvp.recycler.BaseRecyclerAdapter;
import com.xing.basemvp.recycler.BaseViewHolder;
import com.xing.mvplib.base.BaseMVPActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 一个 View 层对应一个 presenter 层
 */
public class ArticleDetailActivity extends BaseMVPActivity<ArticleDetailPresenter>
        implements ArticleDetailContract.View {

    private RecyclerView recyclerView;
    private List<Article> dataList = new ArrayList<>();
    private ProgressDialog progressDialog;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_article_detail;
    }

    @Override
    protected ArticleDetailPresenter createPresenter() {
        return new ArticleDetailPresenter();
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
    }

    @Override
    protected void initData() {
        super.initData();
        presenter.getArticleList(0);
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
}
