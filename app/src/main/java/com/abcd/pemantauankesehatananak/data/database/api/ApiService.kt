package com.abcd.pemantauankesehatananak.data.database.api

import com.abcd.pemantauankesehatananak.data.model.ResponseModel
import com.abcd.pemantauankesehatananak.data.model.UserModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

    //Register
    @Multipart
    @POST("pemantauan-kesehatan-anak/api/post.php")
    suspend fun postRegister(
        @Part("register_user") register_user: String,
        @Part("nama") nama: String,
        @Part("alamat") alamat: String,
        @Part("nomor_hp") nomor_hp: String,
        @Part("username") username: String,
        @Part("password") password: String,
    ): ResponseModel

}