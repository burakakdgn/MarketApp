package com.example.bitirmeprojesiapp.ui.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bitirmeprojesiapp.R
import com.example.bitirmeprojesiapp.data.entity.Kullanici
import com.example.bitirmeprojesiapp.data.entity.Yemekler
import com.example.bitirmeprojesiapp.databinding.CardTasarimBinding
import com.example.bitirmeprojesiapp.databinding.FragmentAnasayfaBinding
import com.example.bitirmeprojesiapp.retrofit.RetrofitClient
import com.example.bitirmeprojesiapp.retrofit.YemeklerDao
import com.example.bitirmeprojesiapp.ui.fragment.AnasayfaFragmentDirections
import com.example.bitirmeprojesiapp.ui.viewmodel.AnasayfaViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class YemeklerAdapter(private var mContext: Context,
                      var yemeklerListesi:List<Yemekler>,
                      private val viewModel: AnasayfaViewModel)
    : RecyclerView.Adapter<YemeklerAdapter.CardTasarimTutucu>(){

    inner class CardTasarimTutucu(var tasarim:CardTasarimBinding) : RecyclerView.ViewHolder(tasarim.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardTasarimTutucu {
       val binding = CardTasarimBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return CardTasarimTutucu(binding)
    }


    fun updateData(newData: List<Yemekler>) {
        yemeklerListesi = newData
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = yemeklerListesi.size

    override fun onBindViewHolder(holder: CardTasarimTutucu, position: Int) {
        val yemek = yemeklerListesi.get(position)
        val card = holder.tasarim

        setupFoodInfo(card, yemek)
        setupFoodInfo2(card,yemek)
        setAddToCartListener(card, yemek)
        setDetailClickListener(card, yemek)

        loadImage(mContext, yemek, card)
    }

    private fun setupFoodInfo(card: CardTasarimBinding, yemek: Yemekler) {
        val priceText = mContext.getString(R.string.price, yemek.yemek_fiyat.toString())
        card.tvPrice.text = priceText
    }
    private fun setupFoodInfo2(card: CardTasarimBinding, yemek: Yemekler) {
        val food_isim = mContext.getString(R.string.food_name, yemek.yemek_adi.toString())
        card.tvFoodName.text = food_isim
    }

    private fun setAddToCartListener(card: CardTasarimBinding, yemek: Yemekler) {
        card.ibAdd.setOnClickListener {
            addToCart(yemek)
        }
    }

    private fun setDetailClickListener(card: CardTasarimBinding, yemek: Yemekler) {
        card.ivFood.setOnClickListener {
            val bundle = Bundle().apply {
                putString("yemek_adi", yemek.yemek_adi)
                putString("yemek_fiyat", yemek.yemek_fiyat.toString())
                putString("yemek_resim_adi",yemek.yemek_resim_adi)
            }
            it.findNavController().navigate(R.id.detayGecis, bundle)
        }
    }



    private fun loadImage(context: Context, food: Yemekler, card: CardTasarimBinding) {
        val imageUrl = "http://kasimadalan.pe.hu/yemekler/resimler/${food.yemek_resim_adi}"
        Glide.with(context)
            .load(imageUrl)
            .override(300, 300)
            .into(card.ivFood)
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun addToCart(food: Yemekler) {
        GlobalScope.launch(Dispatchers.IO) {
            val response = RetrofitClient.getClient("http://kasimadalan.pe.hu/")
                .create(YemeklerDao::class.java)
                .sepeteEkle(food.yemek_adi, food.yemek_resim_adi, food.yemek_fiyat.toInt(), 1, Kullanici.username)

            if (response.success == 1) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(mContext, "Ürün sepete başarıyla eklendi", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}