package com.abcd.pemantauankesehatananak.data.database.api

import com.abcd.pemantauankesehatananak.data.model.AktivitasModel
import com.abcd.pemantauankesehatananak.data.model.MilestoneModel
import com.abcd.pemantauankesehatananak.data.model.ResponseModel
import com.abcd.pemantauankesehatananak.data.model.UserModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {
    @GET("pemantauan-kesehatan-anak/api/get.php")
    suspend fun getUser(
        @Query("get_user") getUser: String,
        @Query("username") username: String,
        @Query("password") password: String
    ): UserModel

    @GET("pemantauan-kesehatan-anak/api/get.php")
    suspend fun getAktivitas(
        @Query("get_aktivitas") get_aktivitas: String,
        @Query("id_user") id_user: Int,
        @Query("umur") umur: Int
    ): ArrayList<AktivitasModel>

    @GET("pemantauan-kesehatan-anak/api/get.php")
    suspend fun getMilestone(
        @Query("get_milestone") get_milestone: String,
        @Query("id_user") id_user: Int,
    ): ArrayList<MilestoneModel>

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
    @POST("simpan_riwayat.php")
    suspend fun simpanRiwayat(
        @Field("user_id") userId: Int,
        @Field("anak_id") anakId: Int,
        @Field("aktivitas_id") aktivitasId: Int
    ): ResponseModel

}