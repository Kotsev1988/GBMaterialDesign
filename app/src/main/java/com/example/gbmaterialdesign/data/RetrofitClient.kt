package com.example.gbmaterialdesign.data

import com.example.gbmaterialdesign.model.PictureOfTheDay
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class RetrofitClient {
    private val baseURL = "https://api.nasa.gov/"

    var serviceApi: RetrofitApi = Retrofit.Builder()
        .baseUrl(baseURL)
        .addConverterFactory(GsonConverterFactory.create(
            GsonBuilder().setLenient().create()
        ))
        .client(createOkHttpClient(PictureOfTheDayApiInterceptor()))
        .build().create(RetrofitApi::class.java)


    fun getPictureOfTheDay(date: String, callback: Callback<PictureOfTheDay>){
        return serviceApi.getPictureOfTheDay(date = date).enqueue(callback)
    }



    private fun createOkHttpClient(interceptor: Interceptor): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(interceptor)
        httpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))

        return httpClient.build()
    }

    inner class PictureOfTheDayApiInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            return chain.proceed(chain.request())
        }
    }
}