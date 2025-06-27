package com.abcd.pemantauankesehatananak.data.repository

import com.abcd.pemantauankesehatananak.data.database.api.ApiService
import com.abcd.pemantauankesehatananak.data.model.KategoriModel
import com.abcd.pemantauankesehatananak.data.model.ResponseModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KategoriRepository @Inject constructor(
    private val api: ApiService
) {
    suspend fun getKategori(
    ):ArrayList<KategoriModel>{
        return api.getKategori("")
    }

    suspend fun postTambahKategori(
        kategori: String, deskripsi: String
    ):ResponseModel{
        return api.postTambahKategori("", kategori, deskripsi)
    }

    suspend fun postUpdateKategori(
        idUserKategori: Int, kategori: String, deskripsi: String
    ):ResponseModel{
        return api.postUpdateKategori("", idUserKategori, kategori, deskripsi)
    }

}