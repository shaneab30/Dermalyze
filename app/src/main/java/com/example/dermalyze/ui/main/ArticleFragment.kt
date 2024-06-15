package com.example.dermalyze.ui.main

import ArticleAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dermalyze.R
import com.example.dermalyze.databinding.FragmentArticleBinding
import com.example.dermalyze.ui.main.data.ArticlesData

class ArticleFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding
    private lateinit var articleAdapter: ArticleAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentArticleBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolBar()
        setUpRecyclerView()

    }

    private fun setUpToolBar() {
        (activity as AppCompatActivity).apply {
            setSupportActionBar(binding?.toolbar)
            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(true)
                title = getString(R.string.artikel)
            }
        }

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {}

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                findNavController().navigateUp()
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.CREATED)
    }

    private fun setUpRecyclerView() {
        val article = ArticlesData.dummyArticles

        articleAdapter = ArticleAdapter { article ->
            val action = ArticleFragmentDirections.actionArticleFragmentToDetailArticleActivity(article)
            findNavController().navigate(action)
        }

        binding?.rvArticle?.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = articleAdapter
        }

        articleAdapter.submitList(article)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}