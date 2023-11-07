package com.example.bitirmeprojesiapp.data.repo

import com.example.bitirmeprojesiapp.data.datasource.YemeklerDataSource
import com.example.bitirmeprojesiapp.data.entity.SepetYemekler
import com.example.bitirmeprojesiapp.data.entity.Yemekler

class YemeklerRepository(var yds : YemeklerDataSource) {



    suspend fun yemekleriYukle() : List<Yemekler> = yds.yemekleriYukle()

    suspend fun sepeteEkle(yemek_adi:String,yemek_resim_adi:String,yemek_fiyat:Int,yemek_siparis_adet: Int,kullanici_adi: String) = yds.sepeteEkle(yemek_adi,yemek_resim_adi,yemek_fiyat,yemek_siparis_adet,kullanici_adi)



    suspend fun SepetYemekler(kullanici_adi: String) : List<SepetYemekler> = yds.yemekleriGetir(kullanici_adi)


    suspend fun yemekSilSepet(sepet_yemek_id: String, kullanici_adi: String) = yds.yemekSilSepet(sepet_yemek_id,kullanici_adi)
}