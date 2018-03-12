package com.ragaisis.newsreader.dagger.module

import android.content.Context
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ragaisis.newsreader.R
import com.ragaisis.newsreader.api.NewsApi
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.security.KeyStore
import java.security.cert.CertificateFactory
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager


@Module
class ApiModule {

    @Provides
    internal fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
        return gsonBuilder.create()
    }

    @Provides
    internal fun provideOkHttpClient(context: Context): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val apiKeyInterceptor = Interceptor { chain ->
            val original = chain.request()
            val originalHttpUrl = original.url()
            val url = originalHttpUrl.newBuilder()
                    .addQueryParameter("apiKey", "57a79eac5a8f44efa2bd3408139b83f3")
                    .build()
            chain.proceed(original.newBuilder().url(url).build())
        }


        // Load CAs from an InputStream
        val certificateFactory = CertificateFactory.getInstance("X.509")
        val inputStream = context.getResources().openRawResource(R.raw.gandi_standard_ssl_ca2)
        val certificate = certificateFactory.generateCertificate(inputStream)
        inputStream.close()

        // Create a KeyStore containing our trusted CAs
        val keyStoreType = KeyStore.getDefaultType()
        val keyStore = KeyStore.getInstance(keyStoreType)
        keyStore.load(null, null)
        keyStore.setCertificateEntry("ca", certificate)

        // Create a TrustManager that trusts the CAs in our KeyStore.
        val tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm()
        val trustManagerFactory = TrustManagerFactory.getInstance(tmfAlgorithm)
        trustManagerFactory.init(keyStore)
        val trustManagers = trustManagerFactory.trustManagers
        val x509TrustManager = trustManagers[0] as X509TrustManager

        // Create an SSLSocketFactory that uses our TrustManager
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, arrayOf<TrustManager>(x509TrustManager), null)

        return OkHttpClient.Builder()
                .addNetworkInterceptor(interceptor)
                .sslSocketFactory(sslContext.socketFactory, x509TrustManager)
                .addInterceptor(apiKeyInterceptor)
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .hostnameVerifier { hostname, session -> true }
                .build()
    }

    @Provides
    fun provideRetrofit(client: OkHttpClient, gson: Gson): Retrofit {
        val baseUrl = "https://newsapi.org/v2/"
        return Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build()
    }

    @Provides
    fun provideApi(retrofit: Retrofit): NewsApi {
        return retrofit.create(NewsApi::class.java)
    }

}
