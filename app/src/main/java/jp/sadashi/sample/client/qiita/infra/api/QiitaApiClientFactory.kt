package jp.sadashi.sample.client.qiita.infra.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

@ExperimentalSerializationApi
object QiitaApiClientFactory {

    fun create(): QiitaApiClient {
        return provideRetrofit().create(QiitaApiClient::class.java)
    }

    private fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://qiita.com")
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }
}