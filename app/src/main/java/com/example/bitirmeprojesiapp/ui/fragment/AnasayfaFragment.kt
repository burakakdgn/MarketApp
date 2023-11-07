package com.example.bitirmeprojesiapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.bitirmeprojesiapp.R
import com.example.bitirmeprojesiapp.data.entity.Yemekler
import com.example.bitirmeprojesiapp.databinding.FragmentAnasayfaBinding
import com.example.bitirmeprojesiapp.ui.adapter.YemeklerAdapter
import com.example.bitirmeprojesiapp.ui.viewmodel.AnasayfaViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnasayfaFragment : Fragment() {
    private lateinit var binding: FragmentAnasayfaBinding

    private lateinit var foodsAdapter: YemeklerAdapter
    private var isCartSelected = false

    private var isHomeSelected = false
    private val viewModel: AnasayfaViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAnasayfaBinding.inflate(inflater, container, false)
        setupRecyclerView()
        observeFilteredFoods()

        setupClickListeners()
        return binding.root
    }
    private fun setupRecyclerView() {
        binding.rvHomeCardDesign.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        foodsAdapter = YemeklerAdapter(requireContext(), mutableListOf(), viewModel)
        binding.rvHomeCardDesign.adapter = foodsAdapter
    }
    private fun observeFilteredFoods() {
        viewModel.filteredFoods.observe(viewLifecycleOwner) { foods ->
            foodsAdapter.updateData(foods)
        }
    }


    private fun setupClickListeners() {
        binding.tbCart.setOnClickListener { toggleCartSelection() }
        binding.tbHome.setOnClickListener { toggleHomeSelection() }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.searchFoods(newText.orEmpty())
                return true
            }
        })


    }

    private fun toggleCartSelection() {
        isCartSelected = !isCartSelected
        updateToggleButtonState(binding.tbCart, isCartSelected)
        Navigation.findNavController(requireView()).navigate(R.id.sepetFragment)
    }

    private fun toggleHomeSelection() {
        isHomeSelected = !isHomeSelected
        updateToggleButtonState(binding.tbHome, isHomeSelected)
        Navigation.findNavController(requireView()).navigate(R.id.anasayfaFragment)
    }



    private fun updateToggleButtonState(button: Button, isSelected: Boolean) {
        button.setBackgroundResource(if (isSelected) R.drawable.button_background else R.drawable.button_background)
    }








}