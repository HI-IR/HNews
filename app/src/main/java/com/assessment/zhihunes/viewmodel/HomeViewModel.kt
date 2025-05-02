package com.assessment.zhihunes.viewmodel

import LatestNews
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.assessment.zhihunes.model.HomeModel
import kotlinx.coroutines.launch

/**
 * description ： TODO:类的作用
 * author : HI-IR
 * email : qq2420226433@outlook.com
 * date : 2025/5/1 23:31
 */
class HomeViewModel: ViewModel() {
    private val model by lazy { HomeModel() }

    private val _LatestNews = MutableLiveData<LatestNews>()
    val LatestNews:LiveData<LatestNews> = _LatestNews

    private val _LatestNewsError = MutableLiveData<String>()
    val LatestNewsError:LiveData<String> = _LatestNewsError

    fun doGetLatestNews(){
        try {
            viewModelScope.launch {
                model.doGetLatestNews({_LatestNews.value = it},{_LatestNewsError.value = it})
            }
        } catch (e: Exception) {
            _LatestNewsError.value = "网络访问失败"
        }
    }


}