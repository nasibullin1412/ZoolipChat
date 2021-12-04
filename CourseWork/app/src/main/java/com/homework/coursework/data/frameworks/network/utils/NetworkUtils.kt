package com.homework.coursework.data.frameworks.network.utils

import com.homework.coursework.BuildConfig
import com.homework.coursework.data.frameworks.database.dao.AuthDao
import com.homework.coursework.data.frameworks.database.entities.AuthEntity
import com.homework.coursework.data.frameworks.network.utils.NetworkConstants.APPLICATION_JSON_TYPE
import com.homework.coursework.data.frameworks.network.utils.NetworkConstants.AUTHORIZATION
import com.homework.coursework.data.frameworks.network.utils.NetworkConstants.CONNECTION_TIMEOUT
import com.homework.coursework.data.frameworks.network.utils.NetworkConstants.READ_TIMEOUT
import com.homework.coursework.data.frameworks.network.utils.NetworkConstants.WRITE_TIMEOUT
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

fun Retrofit.Builder.setClient(authDao: AuthDao) = apply {
    val okHttpClient = OkHttpClient.Builder()
        .addQueryInterceptor(authDao)
        .addHttpLoggingInterceptor()
        .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        .build()
    this.client(okHttpClient)
}

private fun OkHttpClient.Builder.addQueryInterceptor(authDao: AuthDao) = apply {
    val interceptor = Interceptor { chain ->
        var request = chain.request()
        val authEntity: AuthEntity = authDao.getApiKey() ?: AuthEntity(0, "", "")
        request = request.newBuilder().header(
            AUTHORIZATION,
            Credentials.basic(
                username = authEntity.email,
                password = authEntity.apiKey
            )
        ).build()
        chain.proceed(request)
    }
    this.addInterceptor(interceptor)
}

private fun OkHttpClient.Builder.addHttpLoggingInterceptor() = apply {
    if (BuildConfig.DEBUG) {
        val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        this.addNetworkInterceptor(interceptor)
    }
}

@Suppress("EXPERIMENTAL_API_USAGE")
fun Retrofit.Builder.addJsonConverter() = apply {
    val json = Json { ignoreUnknownKeys = true }
    val contentType = APPLICATION_JSON_TYPE.toMediaType()
    this.addConverterFactory(json.asConverterFactory(contentType))
}
