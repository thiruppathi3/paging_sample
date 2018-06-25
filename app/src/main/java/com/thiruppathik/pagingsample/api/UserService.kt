package com.thiruppathik.pagingsample.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Thiruppathi.K on 6/23/2018.
 */

private const val TAG = "UserService"

interface UserService {
    @GET("users")
    fun getUsers(@Query("page") page: Int): Call<UserResponse>

    companion object {
        private const val BASE_URL = "https://reqres.in/api/"

        fun create(): UserService {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                    .addInterceptor(logger)
                    .build()
            return Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(UserService::class.java)
        }
    }
}