package com.gorigolilagmail.kyutechapp2018.client

import com.gorigolilagmail.kyutechapp2018.model.ApiRequest
import com.gorigolilagmail.kyutechapp2018.model.NewsHeading
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET

interface ApiClient {

    @GET("/api/news-headings")
    fun listNewsHeadings(): Observable<ApiRequest<NewsHeading>>
}