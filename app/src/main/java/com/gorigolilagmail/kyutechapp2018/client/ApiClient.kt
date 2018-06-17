package com.gorigolilagmail.kyutechapp2018.client

import com.google.gson.JsonObject
import com.gorigolilagmail.kyutechapp2018.model.*
import io.reactivex.Observable
import retrofit2.Response
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

    @Headers("Accept: application/json",
            "Content-Type: application/json")
    @PUT("/api/users/{userId}/")
    fun updateUser(@Path("userId") userId: Int): Observable<User>

    // Syllabus
    @GET("/api/syllabuses/day-{day}/period-{period}/")
    fun listSyllabusByDayAndPeriod(
            @Path("day") day: String,
            @Path("period") period: Int
    ): Observable<ApiRequest<Syllabus>>

    @GET
    fun getNextSyllabusList(@Url url: String): Observable<ApiRequest<Syllabus>>

    @GET("/api/user-schedules/user-{userId}/quarter-{quarter}/")
    fun listUserScheduleByQuarter(
            @Path("userId") userId: Int,
            @Path("quarter") quarter: Int
    ): Observable<ApiRequest<UserSchedule>>

    @Headers("Accept: application/json",
            "Content-Type: application/json")
    @POST("/api/user-schedules/")
    fun createUserSchedule(@Body body: JsonObject): Observable<UserSchedule>

    @Headers("Accept: application/json",
            "Content-Type: application/json")
    @PUT("/api/user-schedules/{userScheduleId}/")
    fun updateUserSchedule(
            @Path("userScheduleId") userScheduleId: Int,
            @Body body: JsonObject): Observable<UserSchedule>

    @DELETE("/api/user-schedules/{userScheduleId}/")
    fun deleteUserSchedule(
            @Path("userScheduleId") userScheduleId: Int
    ): Observable<Response<Void>>

}