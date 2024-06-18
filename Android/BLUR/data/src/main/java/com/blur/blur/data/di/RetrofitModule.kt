package com.blur.blur.data.di

import android.content.Context
import com.blur.blur.data.retrofit.FileService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import com.blur.blur.data.retrofit.UserService
import okhttp3.JavaNetCookieJar
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.net.CookieManager


val FC_HOST = "https://jj.system32.kr"

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @Provides
    fun provideOkHttpClient(context: Context): OkHttpClient {

        // 로그를 출력하기 위한 인터셉터 생성
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .addInterceptor(logging) // 인터셉터를 OkHttpClient에 추가
            .addInterceptor { chain ->
                // 세션 쿠키를 가져와서 헤더에 추가
                val currentSession = SharedPreferencesManager.getCookie(context)
                val request = chain.request().newBuilder()
                    .addHeader("Cookie", currentSession ?: "")
                    .build()
                chain.proceed(request)
            }
            .cookieJar(JavaNetCookieJar(CookieManager()))
            .build()
    }

    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        val converterFactory = Json {
            ignoreUnknownKeys = true
        }.asConverterFactory("application/json; charset=UTF-8".toMediaType())

        return Retrofit.Builder()
            .baseUrl("${FC_HOST}/api/")
            .addConverterFactory(converterFactory)
            .client(client)
            .build()
    }

    @Provides
    fun provideUserService(retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }

    @Provides
    fun provideFileService(retrofit: Retrofit): FileService {
        return retrofit.create(FileService::class.java)
    }
}