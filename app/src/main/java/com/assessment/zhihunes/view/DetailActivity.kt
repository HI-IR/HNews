package com.assessment.zhihunes.view

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.assessment.zhihunes.adapter.DetailAdapter
import com.assessment.zhihunes.databinding.ActivityDetailBinding
import com.assessment.zhihunes.viewmodel.DetailViewModel


class DetailActivity : AppCompatActivity() {
    private var page:Int = -1
    private var id: Long = -1
    private val binding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }
    private val viewModel: DetailViewModel by lazy {
        ViewModelProvider(this)[DetailViewModel::class.java]
    }
    private var adapter: DetailAdapter? = null

    companion object {
        fun startActivity(context: Context,id:Long) {
            //TODO("带实现跳转")
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("id",id)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        enableEdgeToEdge()
        initViewModel()
        initEvent()
        getData()

        id = intent.getLongExtra("id",0)
    }


    private fun getData() {
        viewModel.loadNews()
    }

    private fun initEvent() {
        binding.bottomtoolbar.setOnBackClickListener { finish() }
        binding.bottomtoolbar.setOnShareClickListener {
            //TODO(分享等待实现))
        }
        binding.bottomtoolbar.setOnCommentsClickListener {
            //TODO(评论等待)
        }
        binding.bottomtoolbar.setOnPopularityClickListener{
            //TODO(点赞)
        }
        binding.bottomtoolbar.setOnCollectClickListener{
            //TODO(收藏)
        }

        binding.vp2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewModel.getExtraStory(viewModel.BeforeNewsList.value!![position].id)
            }

        })

    }



    private fun initViewModel() {
        viewModel.BeforeNewsList.observe(this) {
            if (page == -1){
                it.forEachIndexed { index, storyBefore ->
                    if (storyBefore.id == id) page = viewModel.BeforeNewsList.value!!.size - it.size + index
                }
                viewModel.loadNews()
                return@observe
            }else{
                //如果没有创建Adapter则说明是第一次刷新,或者页面数-1，创建对应的adpter（页面数等于-1说明还未加载到对应的新闻）
                if (adapter == null) {
                    adapter = DetailAdapter(viewModel)
                    binding.vp2.adapter = adapter
                    binding.vp2.adapter?.notifyDataSetChanged()
                    binding.vp2.setCurrentItem(page,false)
                } else {
                    adapter!!.notifyItemRangeChanged(
                        viewModel.count.value!!,
                        viewModel.BeforeNewsList.value!!.size - viewModel.count.value!!
                    )
                    viewModel.count.value = viewModel.BeforeNewsList.value!!.size
                }
            }

        }

        viewModel.currentExtraStory.observe(this){
            binding.bottomtoolbar.setData(it.comments,it.popularity,)
        }

    }


}