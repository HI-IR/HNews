package com.assessment.zhihunes.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assessment.zhihunes.domain.BeforeNews
import com.assessment.zhihunes.domain.ExtraStory
import com.assessment.zhihunes.domain.StoryBefore
import com.assessment.zhihunes.model.DetailModel
import com.assessment.zhihunes.utils.DateUtils
import kotlinx.coroutines.launch

/**
 * description ： Detail页的viewModel
 * author : HI-IR
 * email : qq2420226433@outlook.com
 * date : 2025/5/3 00:41
 */
class DetailViewModel: ViewModel() {
    private val model by lazy {
        DetailModel()
    }

    //用于缓存我获得的最近消息
    private val _BeforeNewsList:MutableLiveData<MutableList<StoryBefore>> = MutableLiveData(mutableListOf())
    val BeforeNewsList:LiveData<MutableList<StoryBefore>> = _BeforeNewsList
    //用于
    val count: MutableLiveData<Int> = MutableLiveData(0)

    //下一次加载的日期回退天数（-1代表今天，0代表昨天，1代表前天）
    private val nextLoadDayAgo:MutableLiveData<Int> = MutableLiveData(-1)
    //下一次加载的日期(yyyyMMdd格式)
    private val nextLoadDateNews:MutableLiveData<String> = MutableLiveData(DateUtils.getDateAgo(nextLoadDayAgo.value!!))

    //当前页的额外消息
    private val _currentExtraStory: MutableLiveData<ExtraStory> = MutableLiveData()
    val currentExtraStory:LiveData<ExtraStory> = _currentExtraStory
    private val _currentPageId: MutableLiveData<Long> = MutableLiveData()

    //加载新闻
    fun loadNews(){
        viewModelScope.launch {
            model.doGetBeforeNews(nextLoadDateNews.value!!,{
                val currentList = _BeforeNewsList.value ?: mutableListOf()
                currentList.addAll(it.stories)
                _BeforeNewsList.value = currentList

                Log.d("ld","${it}");
                nextLoadDayAgo.value = nextLoadDayAgo.value?.plus(1)
                nextLoadDateNews.value = DateUtils.getDateAgo(nextLoadDayAgo.value!!)
                Log.d("ld","${nextLoadDateNews.value}");
            },{})
        }
    }

    //获取对应新闻的额外消息
    fun getExtraStory(id: Long){
        viewModelScope.launch {
            model.doGetExtraStory(id,{
                _currentPageId.value = id
                _currentExtraStory.value = it
            },{})
        }
    }

}