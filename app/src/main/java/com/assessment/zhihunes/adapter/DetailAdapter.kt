package com.assessment.zhihunes.adapter

import android.annotation.SuppressLint
import android.media.RouteListingPreference.Item
import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.assessment.zhihunes.databinding.ItemWebBinding
import com.assessment.zhihunes.view.DetailActivity
import com.assessment.zhihunes.viewmodel.DetailViewModel


/**
 * description ： Detail页面的Adapter(VP2)
 * author : HI-IR
 * email : qq2420226433@outlook.com
 * date : 2025/5/3 00:52
 */
class DetailAdapter(private val viewModel: DetailViewModel): RecyclerView.Adapter<DetailAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = ItemWebBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        val viewHolder = ViewHolder(binding)
        return viewHolder
    }

    override fun getItemCount(): Int {

        return viewModel.BeforeNewsList.value?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //当目前显示的数据是最后一个数据的时候，通过网络访问加载新的新闻
        if (position == itemCount-1){
            viewModel.loadNews()
        }

        viewModel.BeforeNewsList.value?.get(position)?.let { holder.bind(it.url) }

    }



    inner class ViewHolder(private val binding: ItemWebBinding): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetJavaScriptEnabled")
        fun bind(mUrl: String){
            with(binding.webview){
                settings.cacheMode = WebSettings.LOAD_NO_CACHE
                settings.javaScriptEnabled = true
                webViewClient = WebViewClient()
                loadUrl(mUrl)
            }
        }
    }
}


