package com.assessment.zhihunes.domain

/**
 * description ： 短评论
 * author : HI-IR
 * email : qq2420226433@outlook.com
 * date : 2025/5/2 00:06
 */
data class ShortComments(
    val comments: List<CommentShort>
)

data class CommentShort(
    val author: String,
    val avatar: String,
    val content: String,
    val id: Int,
    val likes: Int,
    val reply_to: ReplyToShort,
    val time: Int
)

data class ReplyToShort(
    val author: String,
    val content: String,
    val id: Int,
    val status: Int
)