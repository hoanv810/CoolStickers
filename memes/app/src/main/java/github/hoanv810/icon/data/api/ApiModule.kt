package github.hoanv810.icon.data.api

import android.app.Application
import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import github.hoanv810.icon.BuildConfig
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * @author hoanv
 * @since 4/4/18.
 */

@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideOkHttpCache(app: Application): Cache {
        val size: Long = 100 * 1024 * 1024
        return Cache(app.cacheDir, size)
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor().apply {
            level =
                    if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.HEADERS
                    else HttpLoggingInterceptor.Level.BASIC
        }
    }

    @Provides
    @Singleton
    fun provideHttpClient(cache: Cache, logger: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .cache(cache)
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(logger)
                .addNetworkInterceptor(StethoInterceptor())
                .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(httpClient: OkHttpClient): Retrofit {
//        val gson = GsonBuilder()
//        gson.excludeFieldsWithoutExposeAnnotation()

        return Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(httpClient)
                .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        if (BASE_URL.isEmpty()) {
            throw IllegalArgumentException("Base url is empty")
        }
        return retrofit.create(ApiService::class.java)
    }

    companion object {
        const val TIME_OUT: Long = 30
        const val BASE_URL: String = "https://github.com"
    }
}