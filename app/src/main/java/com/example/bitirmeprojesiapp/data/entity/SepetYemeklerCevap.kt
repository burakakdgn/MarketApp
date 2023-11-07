package com.example.bitirmeprojesiapp.data.entity

import com.google.gson.annotations.SerializedName

data class SepetYemeklerCevap (@SerializedName("sepet_yemekler")
                          var yemekler: List<SepetYemekler>,
                          var success:Int) {

}
