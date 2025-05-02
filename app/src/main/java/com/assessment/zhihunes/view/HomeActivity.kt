package com.assessment.zhihunes.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.assessment.zhihunes.databinding.ActivityHomeBinding
import com.assessment.zhihunes.viewmodel.HomeViewModel

class HomeActivity : AppCompatActivity() {
    private var adapter: HomeAdapter? = null

    private val binding: ActivityHomeBinding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViewModel()
        initData()
    }

    private fun getData() {
        viewModel.doGetLatestNews()
    }

    private fun initViewModel() {
        viewModel.LatestNews.observe(this) {
            if (adapter == null) {
                //如果适配器没被初始化则初始化并且设置适配器
                adapter = HomeAdapter(it.top_stories, this@HomeActivity)

                binding.bannerHome.setData(adapter!!)
            } else {
                adapter!!.notifyDataSetChanged()
            }
        }
    }

    private fun initData() {
        getData()
    }
}