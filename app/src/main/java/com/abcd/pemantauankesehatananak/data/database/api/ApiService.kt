package com.abcd.pemantauankesehatananak.data.database.api

import com.abcd.pemantauankesehatananak.data.model.AktivitasModel
import com.abcd.pemantauankesehatananak.data.model.KategoriModel
import com.abcd.pemantauankesehatananak.data.model.MilestoneModel
import com.abcd.pemantauankesehatananak.data.model.PelayananModel
import com.abcd.pemantauankesehatananak.data.model.ResponseModel
import com.abcd.pemantauankesehatananak.data.model.RiwayatAktivitasModel
import com.abcd.pemantauankesehatananak.data.model.UserModel
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET("pemantauan-kesehatan-anak/api/get.php")
    suspend fun getUser(
        @Query("get_user") getUser: String,
        @Query("username") username: String,
        @Query("password") password: String
    ): UserModel

    @GET("pemantauan-kesehatan-anak/api/get.php")
    suspend fun getKategori(
        @Query("get_kategori") get_kategori: String,
    ): ArrayList<KategoriModel>

    @GET("pemantauan-kesehatan-anak/api/get.php")
    suspend fun getAktivitas(
        @Query("get_aktivitas") get_aktivitas: String,
        @Query("id_user") id_user: Int,
        @Query("umur") umur: Int
    ): ArrayList<AktivitasModel>

    @GET("pemantauan-kesehatan-anak/api/get.php")
    suspend fun getRiwayatAktivitas(
        @Query("get_riwayat_aktivitas") get_riwayat_aktivitas: String,
        @Query("id_user") id_user: Int,
        @Query("id_aktivitas") id_aktivitas: Int
    ): ArrayList<RiwayatAktivitasModel>

    @GET("pemantauan-kesehatan-anak/api/get.php")
    suspend fun getMilestone(
        @Query("get_milestone") get_milestone: String,
        @Query("id_user") id_user: Int,
    ): ArrayList<MilestoneModel>

    @GET("pemantauan-kesehatan-anak/api/get.php")
    suspend fun getPemeriksaan(
        @Query("get_pelayanan") get_pelayanan: String,
        @Query("id_user") id_user: Int,
    ): ArrayList<PelayananModel>


    // post
    //Register
    @FormUrlEncoded
    @POST("pemantauan-kesehatan-anak/api/post.php")
    suspend fun postRegister(
        @Field("register_user") register_user: String,
        @Field("nama") nama: String,
        @Field("alamat") alamat: String,
        @Field("nomor_hp") nomor_hp: String,
        @Field("username") username: String,
        @Field("password") password: String,
    ): ResponseModel

    @FormUrlEncoded
    @POST("pemantauan-kesehatan-anak/api/post.php")
    suspend fun postUpdateCheckAktivitas(
        @Field("post_update_check_aktivitas") post_update_check_aktivitas: String,
        @Field("id_user") id_user: Int,
        @Field("id_aktivitas") id_aktivitas: Int
    ): ResponseModel

    @FormUrlEncoded
    @POST("pemantauan-kesehatan-anak/api/post.php")
    suspend fun postUpdateCheckMilestone(
        @Field("post_update_check_milestone") post_update_check_milestone: String,
        @Field("id_user") id_user: Int,
        @Field("id_milestone") id_milestone: Int,
    ): ResponseModel

    @FormUrlEncoded
    @POST("pemantauan-kesehatan-anak/api/post.php")
    suspend fun postUpdateDataDiri(
        @Field("post_update_data") post_update_data: String,
        @Field("id_user") id_user: Int,
        @Field("no_ktp") no_ktp: String,
        @Field("nama") nama: String,
        @Field("nomor_hp") nomor_hp: String,
        @Field("alamat") alamat: String,
        @Field("nama_anak") nama_anak: String,
        @Field("tanggal_lahir") tanggal_lahir: String,
        @Field("jk") jk: String,
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("username_lama") username_lama: String,
    ): ResponseModel

    // Admin

    // Kategori
    @FormUrlEncoded
    @POST("pemantauan-kesehatan-anak/api/post.php")
    suspend fun postTambahKategori(
        @Field("post_tambah_kategori") post_tambah_kategori: String,
        @Field("kategori") kategori: String,
        @Field("deskripsi") deskripsi: String,
    ): ResponseModel
    @FormUrlEncoded
    @POST("pemantauan-kesehatan-anak/api/post.php")
    suspend fun postUpdateKategori(
        @Field("post_update_kategori") post_update_kategori: String,
        @Field("id_kategori") id_kategori: Int,
        @Field("kategori") kategori: String,
        @Field("deskripsi") deskripsi: String,
    ): ResponseModel

}