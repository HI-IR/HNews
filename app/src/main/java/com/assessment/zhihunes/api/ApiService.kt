package com.assessment.zhihunes.api

import retrofit2.http.GET
import retrofit2.http.Path

/**
 * description ： 网络访问的接口
 * author : HI-IR
 * email : qq2420226433@outlook.com
 * date : 2025/5/1 20:14
 */
interface ApiService {
    companion object{
        val BASE_URL= "https://news-at.zhihu.com/api/4/"
    }

    /**
     * 获取最新消息
     */
    @GET("news/latest")
    suspend fun getLatestNews()


    /**
     * 过往消息
     * @param date 时间
     */
    @GET("before/{date}")
    suspend fun getNewsByDate(@Path("date") date:String)


    /**
     * 获取额外信息
     * @param id 文章id
     */
    @GET("story-extra/{id}")
    suspend fun getExtraStory(@Path("id") id:Long)

    /**
     * 获取长评论
     * @param id 文章id
     */
    @GET("story/{id}/long-comments")
    suspend fun getLongComments(@Path("id") id:Long)


    /**
     * 获取短评论
     * @param id 文章id
     */
    @GET("story/{id}/short-comments")
    suspend fun getShortComments(@Path("id") id:Long)



}