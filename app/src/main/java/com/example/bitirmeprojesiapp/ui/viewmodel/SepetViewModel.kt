package com.example.bitirmeprojesiapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bitirmeprojesiapp.data.entity.Kullanici
import com.example.bitirmeprojesiapp.data.entity.SepetYemekler
import com.example.bitirmeprojesiapp.data.repo.YemeklerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SepetViewModel @Inject constructor(var yrepo: YemeklerRepository ) : ViewModel() {
    var foodsCartList = MutableLiveData<List<SepetYemekler>>()
    var mTotalPrice = MutableLiveData<Int>()

    fun updateCart() {
        val kullanici_adi = Kullanici.username
        viewModelScope.launch(Dispatchers.IO) {
            val cartList = yrepo.SepetYemekler(kullanici_adi)

            var totalPrice = 0
            for (cartItem in cartList) {
                totalPrice += cartItem.yemek_fiyat * cartItem.yemek_siparis_adet
            }

            foodsCartList.postValue(cartList)
            mTotalPrice.postValue(totalPrice)
        }
    }

    fun kaydet(yemek_adi: String, yemek_resim_adi:String, yemek_fiyat:Int, yemek_siparis_adet:Int, kullanici_adi: String){
        viewModelScope.launch {
            yrepo.sepeteEkle(yemek_adi, yemek_resim_adi, yemek_fiyat, yemek_siparis_adet, kullanici_adi)
        }
    }

    fun delete(sepet_yemek_id: String, kullanici_adi: String, callback: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            yrepo.yemekSilSepet(sepet_yemek_id, kullanici_adi)
            callback()
        }
    }
}


