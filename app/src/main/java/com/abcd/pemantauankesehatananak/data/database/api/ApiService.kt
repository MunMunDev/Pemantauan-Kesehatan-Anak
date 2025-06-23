package com.abcd.pemantauankesehatananak.data.database.api

import com.abcd.pemantauankesehatananak.data.model.AktivitasModel
import com.abcd.pemantauankesehatananak.data.model.MilestoneModel
import com.abcd.pemantauankesehatananak.data.model.ResponseModel
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
        @Field("id_aktivitas") id_aktivitas: Int,
        @Field("check") check: Int
    ): ResponseModel

    @FormUrlEncoded
    @POST("pemantauan-kesehatan-anak/api/post.php")
    suspend fun postUpdateCheckMilestone(
        @Field("post_update_check_milestone") post_update_check_milestone: String,
        @Field("id_user") id_user: Int,
        @Field("id_milestone") id_milestone: Int,
    ): ResponseModel

}