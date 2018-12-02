package com.xing.basemvp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.btn_article_detail:
                startActivity(new Intent(this, ArticleDetailActivity.class));
                break;

            case R.id.btn_article_and_count:
                startActivity(new Intent(this, ArticleMultiActivity.class));
                break;

            case R.id.btn_me:
                startActivity(new Intent(this, MeActivity.class));
                break;
        }
    }
}
