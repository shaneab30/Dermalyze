package com.example.dermalyze.ui.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dermalyze.databinding.FragmentProductBinding
import com.example.dermalyze.ui.main.data.ObatData
import com.example.dermalyze.ui.main.data.SkincareData
import com.example.dermalyze.ui.product.drug.DrugAdapter
import com.example.dermalyze.ui.product.skincare.SkincareAdapter


class ProductFragment : Fragment() {

    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding
    private lateinit var skincareAdapter: SkincareAdapter
    private lateinit var drugAdapter: DrugAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProductBinding.inflate(layoutInflater, container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        skincareAdapter = SkincareAdapter { skincare ->

        }
        drugAdapter = DrugAdapter { obat ->

        }

        setUpRecyclerView()
        setUpDrugRecyclerView()
    }


    private fun setUpRecyclerView() {
        val skincare = SkincareData.dummySkincare

        binding?.rvSectionOne?.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = skincareAdapter
        }

        skincareAdapter.submitList(skincare)
    }

    private fun setUpDrugRecyclerView() {
        val obat = ObatData.dummyObat

        binding?.rvSectionTwo?.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = drugAdapter
        }

        drugAdapter.submitList(obat)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}