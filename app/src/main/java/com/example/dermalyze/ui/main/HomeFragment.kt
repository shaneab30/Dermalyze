package com.example.dermalyze.ui.main

import ArticleAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dermalyze.databinding.FragmentHomeBinding
import com.example.dermalyze.ui.main.data.ArticlesData
import com.example.dermalyze.ui.main.models.Article


class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpView()
        showListArticle()
    }

    private fun setUpView() {
        binding?.contentHome?.apply {
            analysCard.btnAnalys.setOnClickListener {
                navigateToArticle()
            }
            tvArticle.setOnClickListener {
                navigateToArticle()
            }

        }
    }

    private fun navigateToArticle() {
        val action = HomeFragmentDirections.actionHomeFragmentToArticleFragment()
        findNavController().navigate(action)
    }


    private fun showListArticle() {
        val articlesData = ArticlesData.dummyArticles

        val adapter = ArticleAdapter { article ->
            navigateToDetailArticle(article)
        }

        binding?.contentHome?.apply {
            rvArticle.layoutManager = LinearLayoutManager(
                context, LinearLayoutManager.VERTICAL, false
            )
            rvArticle.adapter = adapter
            adapter.submitList(articlesData)
        }
    }

    private fun navigateToDetailArticle(article: Article) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailArticleActivity(article)
        findNavController().navigate(action)
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}