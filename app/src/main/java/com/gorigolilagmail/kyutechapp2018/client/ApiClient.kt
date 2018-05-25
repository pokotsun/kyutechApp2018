package com.gorigolilagmail.kyutechapp2018.client

import com.gorigolilagmail.kyutechapp2018.model.ApiRequest
import com.gorigolilagmail.kyutechapp2018.model.News
import com.gorigolilagmail.kyutechapp2018.model.NewsHeading
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface ApiClient {

    @GET("/api/news-headings")
    fun listNewsHeadings(): Observable<ApiRequest<NewsHeading>>

    @GET("/api/news/code-{newsHeadingCode}")
    fun listNewsByNewsHeadingCode(
            @Path("newsHeadingCode") newsHeadingCode: Int
    ): Observable<ApiRequest<News>>

    @GET
    fun getNextNewsList(@Url url: String): Observable<ApiRequest<News>>


}