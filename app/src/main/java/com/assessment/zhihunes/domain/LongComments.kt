package com.assessment.zhihunes.domain

/**
 * description ： 长评论
 * author : HI-IR
 * email : qq2420226433@outlook.com
 * date : 2025/5/2 00:05
 */
data class LongComments(
    val comments: List<CommentLong>
)

data class CommentLong(
    val author: String,
    val avatar: String,
    val content: String,
    val id: Int,
    val likes: Int,
    val reply_to: ReplyToLong,
    val time: Int
)

data class ReplyToLong(
    val author: String,
    val content: String,
    val id: Int,
    val status: Int
)