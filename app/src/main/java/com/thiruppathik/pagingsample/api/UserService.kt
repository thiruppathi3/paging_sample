package com.thiruppathik.pagingsample.api

import android.util.Log
import com.thiruppathik.pagingsample.model.User
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val TAG = "UserService"

fun searchUsers(
        service: UserService,
        page: Int,
        onSuccess: (repos: List<User>) -> Unit,
        onError: (error: String) -> Unit) {
    Log.d(TAG, " Requested Page: $page")

    service.getUsers(page).enqueue(
            object : Callback<UserResponse> {
                override fun onFailure(call: Call<UserResponse>?, t: Throwable) {
                    Log.d(TAG, "fail to get data")
                    onError(t.message ?: "unknown error")
                }

                override fun onResponse(
                        call: Call<UserResponse>?,
                        response: Response<UserResponse>
                ) {
                    Log.d(TAG, "got a response $response")
                    if (response.isSuccessful) {
                        val users = response.body()?.data ?: emptyList()
                        users.forEach {
                            it.page = response.body()?.page!!
                        }
                        onSuccess(users)
                    } else {
                        onError(response.errorBody()?.string() ?: "Unknown error")
                    }
                }
            }
    )
}

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