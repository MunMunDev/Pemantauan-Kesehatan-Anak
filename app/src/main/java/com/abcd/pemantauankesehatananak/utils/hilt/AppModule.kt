package com.abcd.pemantauankesehatananak.utils.hilt

import com.abcd.pemantauankesehatananak.data.database.api.ApiService
import com.abcd.pemantauankesehatananak.utils.Constant
import com.abcd.pemantauankesehatananak.utils.KataAcak
import com.abcd.pemantauankesehatananak.utils.KonversiRupiah
import com.abcd.pemantauankesehatananak.utils.LoadingAlertDialog
import com.abcd.pemantauankesehatananak.utils.TanggalDanWaktu
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun apiConfig(): ApiService = Retrofit.Builder()
        .baseUrl(Constant.BASE_URL)
        .client(
            OkHttpClient().newBuilder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build()
        )
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()
        .create(ApiService::class.java)

    @Provides
    fun tanggalDanWaktu(): TanggalDanWaktu = TanggalDanWaktu()

    @Provides
    fun loading(): LoadingAlertDialog = LoadingAlertDialog()

    @Provides
    fun kataAcak(): KataAcak = KataAcak()

    @Provides
    @Singleton
    fun rupiah(): KonversiRupiah = KonversiRupiah()

//    @Provides
//    @Singleton
//    fun kotaKab(): KotaKabProvIndonesia = KotaKabProvIndonesia()
}