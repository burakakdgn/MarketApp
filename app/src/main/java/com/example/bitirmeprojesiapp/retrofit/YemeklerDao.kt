package com.example.bitirmeprojesiapp.retrofit

import com.example.bitirmeprojesiapp.data.datasource.CRUDCevap
import com.example.bitirmeprojesiapp.data.entity.SepetYemeklerCevap
import com.example.bitirmeprojesiapp.data.entity.YemeklerCevap
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface YemeklerDao {
    // http://kasimadalan.pe.hu/yemekler/tumYemekleriGetir.php
    // http://kasimadalan.pe.hu  -> base url
    //yemekler/tumYemekleriGetir.php -> webservis url

    @GET("yemekler/tumYemekleriGetir.php")
    suspend fun yemekleriYukle() : YemeklerCevap

    @POST("yemekler/sepeteYemekEkle.php")
    @FormUrlEncoded

    suspend fun sepeteEkle(@Field("yemek_adi") yemek_adi:String,
                           @Field("yemek_resim_adi") yemek_resim_adi:String,
                           @Field("yemek_fiyat") yemek_fiyat:Int,
                           @Field("yemek_siparis_adet") yemek_siparis_adet:Int,
                           @Field("kullanici_adi") kullanici_adi:String) : CRUDCevap

    @POST("yemekler/sepettekiYemekleriGetir.php")
    @FormUrlEncoded
    fun yemekleriGetir(@Field("kullanici_adi") kullanici_adi: String) : Call<SepetYemeklerCevap>

    @POST("yemekler/sepettenYemekSil.php")
    @FormUrlEncoded
    suspend fun yemekSilSepet(@Field("sepet_yemek_id") sepet_yemek_id: String,
                                   @Field("kullanici_adi") kullanici_adi: String) : CRUDCevap

}