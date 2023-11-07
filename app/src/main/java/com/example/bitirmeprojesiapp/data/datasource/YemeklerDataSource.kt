package com.example.bitirmeprojesiapp.data.datasource

import android.util.Log
import com.example.bitirmeprojesiapp.data.entity.SepetYemekler
import com.example.bitirmeprojesiapp.data.entity.Yemekler
import com.example.bitirmeprojesiapp.retrofit.YemeklerDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.EOFException

class YemeklerDataSource(var ydao:YemeklerDao) {
    suspend fun yemekleriYukle() : List<Yemekler> =
        withContext(Dispatchers.IO){


            return@withContext ydao.yemekleriYukle().yemekler
        }

    suspend fun sepeteEkle(yemek_adi:String,yemek_resim_adi:String,yemek_fiyat:Int,yemek_siparis_adet: Int,kullanici_adi: String){
        val answer = ydao.sepeteEkle(yemek_adi,yemek_resim_adi,yemek_fiyat,yemek_siparis_adet,kullanici_adi)
        if (answer.success == 1){
            Log.i("Yemek Sepete Ekle","Başarı : ${answer.success} - Mesaj : ${answer.message}")
        } else {
            Log.e("Yemek Sepete Ekle","Başarı : ${answer.success} - Mesaj : ${answer.message}")
        }
    }

     suspend fun yemekleriGetir(kullanici_adi: String) : List<SepetYemekler> = withContext(Dispatchers.IO) {
         try {
             val call = ydao.yemekleriGetir(kullanici_adi)
             val response = call.execute()  // This is a synchronous call
             if (response.isSuccessful && response.body() != null) {
                 return@withContext response.body()!!.yemekler
             } else {
                 return@withContext emptyList()
             }
         } catch (e: EOFException) {
             return@withContext emptyList()
         } catch (e: Exception) {
             Log.e("FoodsDataSource", "Error fetching cart foods: ", e)
             return@withContext emptyList()
         }
     }



    suspend fun yemekSilSepet(sepet_yemek_id: String, kullanici_adi: String){
        val answer = ydao.yemekSilSepet(sepet_yemek_id,kullanici_adi)
        Log.i("Yemek Sepetten Sil","Başarı : ${answer.success} - Mesaj : ${answer.message}")
    }
}
