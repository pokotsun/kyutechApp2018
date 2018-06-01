package com.gorigolilagmail.kyutechapp2018.client

import com.google.gson.JsonObject
import com.gorigolilagmail.kyutechapp2018.model.ApiRequest
import com.gorigolilagmail.kyutechapp2018.model.News
import com.gorigolilagmail.kyutechapp2018.model.NewsHeading
import com.gorigolilagmail.kyutechapp2018.model.User
import io.reactivex.Observable
import retrofit2.http.*

interface ApiClient {

    @GET("/api/news-headings")
    fun listNewsHeadings(): Observable<ApiRequest<NewsHeading>>

    @GET("/api/news/code-{newsHeadingCode}")
    fun listNewsByNewsHeadingCode(
            @Path("newsHeadingCode") newsHeadingCode: Int
    ): Observable<ApiRequest<News>>

    @GET
    fun getNextNewsList(@Url url: String): Observable<ApiRequest<News>>

    @Headers("Accept: application/json",
            "Content-Type: application/json")
    @POST("/api/users/")
    fun createUser(@Body body: JsonObject): Observable<User>



}