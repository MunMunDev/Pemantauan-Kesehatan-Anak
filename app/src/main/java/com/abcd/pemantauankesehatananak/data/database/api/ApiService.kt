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
    @GET("pelayanan-kantor-kelurahan/api/get.php")
    suspend fun getUser(
        @Query("get_user") getUser: String,
        @Query("no_ktp") no_ktp: String,
        @Query("password") password: String
    ): UserModel

    //Register
    @Multipart
    @POST("pelayanan-kantor-kelurahan/api/post.php")
    suspend fun postRegister(
        @Part("register_user") register_user: RequestBody,
        @Part("nama") nama: RequestBody,
        @Part("alamat") alamat: RequestBody,
        @Part("nomor_hp") nomor_hp: RequestBody,
        @Part("no_ktp") no_ktp: RequestBody,
        @Part("no_kk") no_kk: RequestBody,
        @Part("tempat_lahir") tempat_lahir: RequestBody,
        @Part("tanggal_lahir") tanggal_lahir: RequestBody,
        @Part("jenis_kelamin") jenis_kelamin: RequestBody,
        @Part("password") password: RequestBody,
        @Part ktp: MultipartBody.Part,
        @Part kk: MultipartBody.Part,
        @Part fotoDiri: MultipartBody.Part,
    ): ResponseModel

}