package com.example.bitirmeprojesiapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bitirmeprojesiapp.data.entity.Kullanici
import com.example.bitirmeprojesiapp.data.entity.SepetYemekler
import com.example.bitirmeprojesiapp.databinding.CardSepetTasarimBinding
import com.example.bitirmeprojesiapp.databinding.FragmentAnasayfaBinding
import com.example.bitirmeprojesiapp.databinding.SepetTasarimBinding
import com.example.bitirmeprojesiapp.ui.viewmodel.SepetViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SepetAdapter(var mContext: Context,var sepetListesi:List<SepetYemekler>,var viewModel: SepetViewModel) :
RecyclerView.Adapter<SepetAdapter.SepetTasarimTutucu>(){

    inner class SepetTasarimTutucu(var tasarim: CardSepetTasarimBinding) : RecyclerView.ViewHolder(tasarim.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SepetTasarimTutucu {
       val binding = CardSepetTasarimBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return SepetTasarimTutucu(binding)
    }
    fun updateData(newData: List<SepetYemekler>) {
        sepetListesi = newData
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return sepetListesi.size
    }


    override fun onBindViewHolder(holder: SepetTasarimTutucu, position: Int) {
        if (position < sepetListesi.size) {
            val cart = sepetListesi[position]
            val c = holder.tasarim
            c.tvYemekName.text = cart.yemek_adi
            c.tvAdet.text = cart.yemek_siparis_adet.toString()
            val tbPrice = cart.yemek_fiyat
            val totalPrice = cart.yemek_siparis_adet * tbPrice
            c.tvFiyat.text = "${totalPrice}₺"


            c.imageView3.setOnClickListener {
                Snackbar.make(it, "Sepetinizden silmek istediğinize emin misiniz?", Snackbar.LENGTH_LONG)
                    .setAction("EVET") {
                        deleteFromCart(cart, position)
                    }.show()
            }
        }

        val food = sepetListesi[position]
        val url = "http://kasimadalan.pe.hu/yemekler/resimler/${food.yemek_resim_adi}"
        Glide.with(mContext)
            .load(url)
            .override(300, 300)
            .into(holder.tasarim.ivYemek)
    }
    fun deleteFromCart(cart: SepetYemekler, position: Int) {
        val itemId = cart.sepet_yemek_id
        val username = Kullanici.username

        viewModel.delete(itemId.toString(), username) {
            CoroutineScope(Dispatchers.Main).launch {
                val updatedCartList = viewModel.foodsCartList.value?.toMutableList()
                updatedCartList?.removeAt(position)
                viewModel.foodsCartList.value = updatedCartList
                notifyItemRemoved(position)
                viewModel.updateCart()
            }
        }
    }





}