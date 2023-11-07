package com.example.bitirmeprojesiapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.bitirmeprojesiapp.R
import com.example.bitirmeprojesiapp.data.entity.Kullanici
import com.example.bitirmeprojesiapp.databinding.FragmentDetayBinding
import com.example.bitirmeprojesiapp.ui.viewmodel.SepetViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetayFragment : Fragment() {

    private lateinit var binding: FragmentDetayBinding

    private val viewModel: SepetViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDetayBinding.inflate(inflater, container, false)
        initializeViews()
        setListeners()
        return binding.root
    }




    private fun initializeViews() {
        val foodName = arguments?.getString("yemek_adi")
        val foodPrice = arguments?.getString("yemek_fiyat")
        val foodImage = arguments?.getString("yemek_resim_adi")

        if (foodName != null && foodPrice != null && foodImage != null) {
            val foodPhoto = "http://kasimadalan.pe.hu/yemekler/resimler/$foodImage"
            loadImage(foodPhoto)
            displayFoodDetails(foodName, foodPrice)
        }
    }

    private fun setListeners() {
        binding.chipMinus.setOnClickListener { updateQuantity(-1) }
        binding.chipPlus.setOnClickListener { updateQuantity(1) }
        binding.chipAddCart.setOnClickListener { addToCart() }
        binding.tbCart3.setOnClickListener { navigateToCartFragment() }
        binding.tbHome3.setOnClickListener { navigateToHomeFragment() }

    }

    private fun loadImage(imageUrl: String) {
        Glide.with(this)
            .load(imageUrl)
            .into(binding.ivFood)
    }

    private fun displayFoodDetails(name: String, price: String) {
        binding.tvFoodName.text = getString(R.string.food_name, name)
        binding.tvPrice.text = getString(R.string.price, price)
    }

    private fun updateQuantity(amount: Int) {
        val tvQuantity = binding.tvQuantity
        var quantity = tvQuantity.text.toString().toIntOrNull() ?: 0

        quantity += amount

        if (quantity in 1..9) {
            tvQuantity.text = quantity.toString()
        }
    }

    private fun addToCart() {
        val quantity = binding.tvQuantity.text.toString().toInt()
        val foodName = arguments?.getString("yemek_adi")
        val foodImage = arguments?.getString("yemek_resim_adi")
        val foodPrice = arguments?.getString("yemek_fiyat")?.toInt() ?: 0
        val username = Kullanici.username

        viewModel.kaydet(foodName!!, foodImage!!, foodPrice, quantity, username)

        Navigation.findNavController(binding.chipAddCart).navigate(R.id.sepetGecis)
    }

    private fun navigateToCartFragment() {
        Navigation.findNavController(binding.chipAddCart).navigate(R.id.sepetGecis)
    }

    private fun navigateToHomeFragment() {
        Navigation.findNavController(binding.tbHome3).navigate(R.id.anasayfaFragment)
    }

    private fun navigateBack() {
        view?.let { Navigation.findNavController(it).popBackStack() }
    }

}