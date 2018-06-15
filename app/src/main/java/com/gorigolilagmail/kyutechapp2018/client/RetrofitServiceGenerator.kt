package com.gorigolilagmail.kyutechapp2018.client

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.*


object RetrofitServiceGenerator {
    // emosi.herokuapp.comで指定したところに設定
    // Retrofitの設定
    private fun createRetrofit(): Retrofit = Retrofit.Builder()
            .baseUrl("https://kyutechapp2018.planningdev.com/")
            .client(getClient())
//            .baseUrl("https://ec2-18-221-237-112.us-east-2.compute.amazonaws.com/")
//            .client(getUnsafeClient())
            .addConverterFactory(createGson())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    // 呼び出し対象のAPIをインスタンス化
    fun createService(): ApiClient = createRetrofit().create(ApiClient::class.java)

    // Gsonのインスタンス化
    private fun createGson(): GsonConverterFactory {
        val gson = GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()
        return GsonConverterFactory.create(gson)
    }

    // OkHttp3のクライアントを作成
    private fun getClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build()
    }

    // Todo DEPRECATE
    // オレオレ証明書におけるエラーを防ぐOkHttpClientを返す
    private fun getUnsafeClient(): OkHttpClient {
        try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {

                @Throws(CertificateException::class)
                override fun checkClientTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
            })

            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, java.security.SecureRandom())
            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.socketFactory

            // Loggerを設定
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val builder = OkHttpClient.Builder()
            builder.sslSocketFactory(sslSocketFactory)
            builder.hostnameVerifier({_,_ ->
                true // ホスト名の検証を行わない
            })

            return builder
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .addInterceptor(interceptor)
                    .build()

        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }
}