package com.example.bitirmeprojesiapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.bitirmeprojesiapp.R
import com.example.bitirmeprojesiapp.databinding.FragmentSepetBinding
import com.example.bitirmeprojesiapp.ui.adapter.SepetAdapter
import com.example.bitirmeprojesiapp.ui.viewmodel.SepetViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SepetFragment : Fragment() {
    private lateinit var binding: FragmentSepetBinding
    private val viewModel: SepetViewModel by viewModels()
    private lateinit var cartAdapter: SepetAdapter
    private var isCartSelected = false
    private var isHomeSelected = false

    override fun onCreateView(


        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding= FragmentSepetBinding.inflate(inflater, container, false)

        setupRecyclerView()
        observeTotalPrice()
        setClickListeners()


        return binding.root
    }
    private fun setupRecyclerView() {
        cartAdapter = SepetAdapter(requireContext(), mutableListOf(), viewModel)
        binding.rvSepet.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        binding.rvSepet.adapter = cartAdapter
    }
    private fun observeTotalPrice() {
        viewModel.foodsCartList.observe(viewLifecycleOwner) { cartItems ->
            cartAdapter.updateData(cartItems)
        }

        viewModel.mTotalPrice.observe(viewLifecycleOwner) { totalPrice ->
            binding.textViewTotal.text = getString(R.string.total_price, totalPrice)
        }
    }
    private fun setClickListeners() {
        binding.buttonOnayla.setOnClickListener {
            if (viewModel.mTotalPrice.value == 0) {
                showToast(R.string.cart_empty)
            } else {

                showToast(R.string.order_confirmed)
            }
        }

        binding.ivBackk.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.ivSepet2.setOnClickListener {
            updateToggleButtonState(true, false)
            navigateToFragment(R.id.sepetFragment)
        }

        binding.ivHome2.setOnClickListener {
            updateToggleButtonState(false, true)
            navigateToFragment(R.id.anasayfaFragment)
        }
    }


    private fun updateToggleButtonState(isCartSelected: Boolean, isHomeSelected: Boolean) {
        this.isCartSelected = isCartSelected
        this.isHomeSelected = isHomeSelected
        updateButtonBackground(binding.ivSepet2, isCartSelected)
        updateButtonBackground(binding.ivHome2, isHomeSelected)
    }
    private fun updateButtonBackground(view: View, isSelected: Boolean) {
        view.setBackgroundResource(if (isSelected) R.drawable.button_background else R.drawable.button_background)
    }



    private fun showToast(message: Int) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun navigateToFragment(destination: Int) {
        Navigation.findNavController(binding.buttonOnayla).navigate(destination)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.updateCart()
    }






}