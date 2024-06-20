package com.example.dermalyze.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dermalyze.R
import com.example.dermalyze.databinding.FragmentHomeBinding
import com.example.dermalyze.datastore.Injection
import com.example.dermalyze.ui.article.ArticleAdapter
import com.example.dermalyze.ui.main.data.ArticlesData
import com.example.dermalyze.ui.main.models.Article
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        retrieveUserName()
        setUpView()
        showListArticle()
    }

    private fun setUpView() {
        binding?.contentHome?.apply {
            analysCard.btnAnalys.setOnClickListener {
                navigateToAnalyze()
            }
            tvArticle.setOnClickListener {
                navigateToArticle()
            }
            topBar.apply {
                ibNotification.setOnClickListener {
                    navigateToNotification()
                }
            }
        }
    }

    private fun retrieveUserName() {
        lifecycleScope.launch {
            val userPreference = Injection.provideUserRepository(requireContext()).getUserPreference()
            val firstName = userPreference.getFirstName().firstOrNull() ?: "User"
            binding?.contentHome?.topBar?.tvUsername?.text = getString(R.string.hi_user, firstName)
        }
    }

    private fun navigateToNotification() {
        val action = HomeFragmentDirections.actionHomeFragmentToNotificationFragment()
        findNavController().navigate(action)
    }

    private fun navigateToArticle() {
        val action = HomeFragmentDirections.actionHomeFragmentToArticleFragment()
        findNavController().navigate(action)
    }

    private fun navigateToAnalyze() {
        val action = HomeFragmentDirections.actionHomeFragmentToCameraActivity()
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
