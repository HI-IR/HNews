package com.assessment.zhihunes.utils
import java.text.SimpleDateFormat
import java.util.*
/**
 * description ： 关于日期的工具类
 * author : HI-IR
 * email : qq2420226433@outlook.com
 * date : 2025/5/1 22:53
 */
object DateUtils {
    fun getCurrentDate(): String {
        val formatter = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
        return formatter.format(Date())
    }
}