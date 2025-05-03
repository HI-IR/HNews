package com.assessment.zhihunes.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.assessment.zhihunes.adapter.TopDetailAdapter
import com.assessment.zhihunes.databinding.ActivityDetailBinding
import com.assessment.zhihunes.viewmodel.TopDetailViewModel

class TopDetailActivity : AppCompatActivity() {
    private var adapter: TopDetailAdapter? =null
    private var id:Long =-1
    private val binding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }
    private val viewModel by lazy {
        ViewModelProvider(this)[TopDetailViewModel::class.java]
    }
    companion object{
        fun startActivity(context: Context, id: Int){
            val intent = Intent(context,TopDetailActivity::class.java)
            intent.putExtra("id",id)
            context.startActivity(intent)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        id = intent.getLongExtra("id",-1)
        initViewModel()
        initEvent()
    }

    private fun initEvent() {
        binding.vp2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewModel.getExtraStory(viewModel.TopStory.value!![position]!!.id)
            }
        })

    }

    private fun initViewModel() {
        viewModel.getTopInfo()
        viewModel.TopStory.observe(this){
            if (adapter==null){
                adapter = TopDetailAdapter(viewModel)
                binding.vp2.adapter = adapter
                viewModel.TopStory.value!!.forEachIndexed { index, topStory ->
                    if (topStory.id.toLong() == id){
                        binding.vp2.setCurrentItem(index)
                    }
                }
            }
        }


        viewModel.currentExtraStory.observe(this) {
            binding.bottomtoolbar.setData(it.comments, it.popularity)
        }
    }


}