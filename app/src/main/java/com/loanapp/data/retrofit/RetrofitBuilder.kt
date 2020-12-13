package com.loanapp.data.retrofit

import android.app.Activity
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File


object RetrofitBuilder {
    fun retrofitBuilder():Retrofit{
        val gson = GsonBuilder()
                .setLenient()
                .create()
        return Retrofit.Builder()
                .baseUrl("http://focusapp-env.eba-xm2atk2z.eu-central-1.elasticbeanstalk.com")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
    }
    fun retrofitBuilderWithToken(token: String, activity: Activity):Retrofit{
        val httpCacheDirectory = File(activity.cacheDir, "cache_file")
        val client = OkHttpClient.Builder()
            .cache(Cache(httpCacheDirectory, 10 * 1024 * 1024))
            .addInterceptor { chain ->
            val newRequest: Request = chain.request().newBuilder()
                    .addHeader("Authorization", token)
                    .build()
            chain.proceed(newRequest)
        }.build()

        return Retrofit.Builder()
                .client(client)
                .baseUrl("http://focusapp-env.eba-xm2atk2z.eu-central-1.elasticbeanstalk.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()


    }
}