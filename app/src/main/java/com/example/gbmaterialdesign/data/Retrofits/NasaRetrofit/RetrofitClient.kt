package com.example.gbmaterialdesign.data.Retrofits.NasaRetrofit

import com.example.gbmaterialdesign.model.EarthPictures.EarthPicture
import com.example.gbmaterialdesign.model.MarsPictures.MarsPicture
import com.example.gbmaterialdesign.model.PictureOfTheDay
import com.example.gbmaterialdesign.model.SolarSystemWeather.SolarSystemWeather
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


    private fun apiRetrofit(baseUrl1: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl1)
            .addConverterFactory(GsonConverterFactory.create(
                GsonBuilder().setLenient().create()
            ))
            .client(createOkHttpClient(PictureOfTheDayApiInterceptor()))
            .build()
    }
    private val serviceApi: RetrofitApi = apiRetrofit(baseURL).create(RetrofitApi::class.java)



    fun getPictureOfTheDay(date: String, callback: Callback<PictureOfTheDay>){
        return serviceApi.getPictureOfTheDay(date = date).enqueue(callback)
    }



    fun getMarsPicture(callback: Callback<MarsPicture>){
        return serviceApi.getMarsPicture().enqueue(callback)
    }

    fun getSolarSystemWeather(startDate: String, endDate: String, callback: Callback<SolarSystemWeather>){
        return serviceApi.SolarSystemWeather(startDate = startDate, endDate = endDate).enqueue(callback)
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

// Если будут разные API для приложения так мы должны создавать ретрофит? Или для каждого API свой класс клиент, интерфес?
//fun apiRetrofitEarth(earthURL: String): Retrofit {
//        return Retrofit.Builder()
//            .baseUrl(earthURL)
//            .addConverterFactory(GsonConverterFactory.create(
//                GsonBuilder().setLenient().create()
//            ))
//            .client(createOkHttpClient(PictureOfTheDayApiInterceptor()))
//            .build()
//    }
//
//    fun apiRetrofitMars(marsURL: String): Retrofit {
//        return Retrofit.Builder()
//            .baseUrl(marsURL)
//            .addConverterFactory(GsonConverterFactory.create(
//                GsonBuilder().setLenient().create()
//            ))
//            .client(createOkHttpClient(PictureOfTheDayApiInterceptor()))
//            .build()
//    }
//
//    fun apiRetrofitSolarSystem(solarSystemURL: String): Retrofit {
//        return Retrofit.Builder()
//            .baseUrl(solarSystemURL)
//            .addConverterFactory(GsonConverterFactory.create(
//                GsonBuilder().setLenient().create()
//            ))
//            .client(createOkHttpClient(PictureOfTheDayApiInterceptor()))
//            .build()
//    }