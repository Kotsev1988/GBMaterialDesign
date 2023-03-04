package com.example.gbmaterialdesign.data.Retrofits.EarthRetrofit


import com.example.gbmaterialdesign.BuildConfig
import com.example.gbmaterialdesign.model.EarthPictures.EarthPicture
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class RetrofitEarthClient {

    private val baseURL = "https://epic.gsfc.nasa.gov/"

    private fun apiRetrofit(baseUrl1: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl1)
            .addConverterFactory(GsonConverterFactory.create(
                GsonBuilder().setLenient().create()
            ))
            .client(createOkHttpClient(MarsApiInterceptor()))
            .build()
    }
    private val serviceApi: EarthAPI = apiRetrofit(baseURL).create(EarthAPI::class.java)

    fun getEarthPicture(date: String, callback: Callback<EarthPicture>){
        return serviceApi.getEarthPicture(date = date).enqueue(callback)
    }

    suspend fun getEarthPictureRecycler(date: String): Response<EarthPicture>{
        return serviceApi.getEarthPictureRecycler(date, BuildConfig.NASA_API_KEY)
    }

    private fun createOkHttpClient(interceptor: Interceptor): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(interceptor)
        httpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        return httpClient.build()
    }

    inner class MarsApiInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            return chain.proceed(chain.request())
        }
    }
}