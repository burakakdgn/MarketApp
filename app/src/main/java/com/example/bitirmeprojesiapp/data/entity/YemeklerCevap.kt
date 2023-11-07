package com.example.bitirmeprojesiapp.data.entity

import com.google.gson.annotations.SerializedName

data class YemeklerCevap(@SerializedName("yemekler")
                         var yemekler: List<Yemekler>,
                         var success:Int) {
}