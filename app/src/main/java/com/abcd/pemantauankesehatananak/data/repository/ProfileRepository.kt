package com.abcd.pemantauankesehatananak.data.repository

import com.abcd.pemantauankesehatananak.data.database.api.ApiService
import com.abcd.pemantauankesehatananak.data.model.ResponseModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepository  @Inject constructor(
    private val api: ApiService
){
    suspend fun postDataDiri(
        idUser:Int, noKtp: String, nama: String, nomorHp: String, alamat: String,
        namaAnak: String, tanggalLahir: String, jenisKelamin: String,
        username: String, password: String, usernameLama: String
    ): ResponseModel {
        val data = api.postUpdateDataDiri(
            "", idUser, noKtp, nama, nomorHp, alamat, namaAnak,
            tanggalLahir, jenisKelamin, username, password, usernameLama
        )
        return data
    }
}