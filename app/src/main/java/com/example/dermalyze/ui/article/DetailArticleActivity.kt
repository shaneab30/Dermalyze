package com.example.dermalyze.ui.article

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.navArgs
import com.example.dermalyze.databinding.ActivityDetailArticleBinding
import com.example.dermalyze.ui.main.utils.Extensions.showImageInto

class DetailArticleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailArticleBinding
    private val navArgs: DetailArticleActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setNavigateBack()
        setUpView()
    }

    private fun setNavigateBack() {
        binding.contentDetail.btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setUpView() {
        val article = navArgs.article

        binding.contentDetail.apply {
            ivCover.showImageInto(this@DetailArticleActivity, article.imageUrl)
            tvTitle.text = article.title
            tvPublisher.text = article.publisher
            tvDatePublish.text = article.datePublish
            tvContent.text = article.content
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.contentDetail
    }
}
