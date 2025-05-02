package com.assessment.zhihunes.adapter

import android.content.Context
import android.provider.MediaStore.Images
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat.NestedScrollType
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import com.assessment.zhihunes.R
import com.assessment.zhihunes.databinding.ItemFooterLoadingBinding
import com.assessment.zhihunes.databinding.ItemRvNewsBinding
import com.assessment.zhihunes.domain.BeforeNews
import com.assessment.zhihunes.domain.StoryBefore
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

/**
 * description ： Home页Rv的Adapter
 * author : HI-IR
 * email : qq2420226433@outlook.com
 * date : 2025/5/2 15:40
 */
class HomeRvAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val dataList = mutableListOf<StoryBefore?>()
    //用null表示 footer的加载中


    companion object {
        val NEWS_VIEW = 0
        val LOADING_VIEW = 1
        val BANNER_VIEW =2
    }



    /**
     * 添加显示的新闻
     */
    fun addNews(news: List<StoryBefore>) {
        val start = dataList.size
        dataList.addAll(news)
        notifyItemRangeInserted(start, news.size)
    }

    /**
     * 添加加载
     */
    fun addLoadingFooter() {
        dataList.add(null)//加载项 用null表示加载
        notifyItemInserted(dataList.size - 1)

    }

    /**
     * 移除加载
     */
    fun removeLoadingFooter() {
        val index = dataList.indexOfLast { it == null }//获取加载项在list的位置
        if (index != -1) {
            dataList.removeAt(index)//在列表中移除加载项
            notifyItemRemoved(index)//通知更新
        }

    }

    override fun getItemViewType(position: Int): Int {

        //如果获取到的元素是null则说明是loading图，否则则是新闻
        return if (dataList[position] == null) LOADING_VIEW else NEWS_VIEW
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //根据viewType创建对应的ViewHolder
        return if (viewType == NEWS_VIEW){
            NewsViewHolder(ItemRvNewsBinding.inflate(LayoutInflater.from(parent.context),parent,false))}
        else{
            LoadingViewHolder(ItemFooterLoadingBinding.inflate(LayoutInflater.from(parent.context),parent,false))
        }
    }

    override fun getItemCount(): Int =dataList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is NewsViewHolder){
            holder.bind(dataList[position]!!)
        }
    }


    inner class NewsViewHolder(binding: ItemRvNewsBinding): RecyclerView.ViewHolder(binding.root){
        val mImage = binding.imageRvImage
        val mTitle = binding.tvRvTitle
        val mHint = binding.tvRvHint
        val mItem = binding.itemRv

        //定义点击事件
        fun initClick(){
            mItem.setOnClickListener{
                //留下一个接口，待完善跳转功能
                //TODO
            }
        }

        //绑定信息
        fun bind(new: StoryBefore){
            mHint.text = new.hint
            mTitle.text = new.title
            //加载网络图片
            val requestOptions: RequestOptions = RequestOptions().placeholder(R.drawable.placeholder)
                .fallback(R.drawable.placeholder)
            Glide.with(context).load(new.images[0]).apply(requestOptions).into(mImage)
        }

        //init里面注册点击事件，防止多次注册点击事件
        init {
            initClick()
        }

    }

    inner class LoadingViewHolder(binding: ItemFooterLoadingBinding): RecyclerView.ViewHolder(binding.root){

    }


}