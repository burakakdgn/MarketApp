package com.example.bitirmeprojesiapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bitirmeprojesiapp.data.entity.Yemekler
import com.example.bitirmeprojesiapp.data.repo.YemeklerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnasayfaViewModel @Inject constructor(var yrepo : YemeklerRepository): ViewModel() {
    private val yemeklerListesi = MutableLiveData<List<Yemekler>>()
    val filteredFoods = MutableLiveData<List<Yemekler>>()



    init {
        yemekleriYukle()
    }


    fun yemekleriYukle(){
        viewModelScope.launch {
            try {
                val allFoods = yrepo.yemekleriYukle()
                yemeklerListesi.value = allFoods
                filteredFoods.value = allFoods
            } catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
    fun searchFoods(query: String) {
        val allFoods = yemeklerListesi.value ?: emptyList()
        val filteredResults = allFoods.filter { food ->
            food.yemek_adi.contains(query, ignoreCase = true)
        }
        filteredFoods.value = filteredResults
    }
}