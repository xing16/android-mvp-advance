package com.xing.basemvp.http;


import com.xing.basemvp.ArticleResponse;
import com.xing.basemvp.base.BaseResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * 接口服务类
 * <p>
 * Created by Administrator on 2018/9/15.
 */

public interface ApiService {

    @GET("article/list/{page}/json")
    Observable<BaseResponse<ArticleResponse>> getArticleList(@Path("page") int page);
}
