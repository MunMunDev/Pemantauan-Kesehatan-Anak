package com.abcd.pemantauankesehatananak.data.repository

import com.abcd.pemantauankesehatananak.data.database.api.ApiService
import com.abcd.pemantauankesehatananak.data.model.AktivitasModel
import com.abcd.pemantauankesehatananak.data.model.KategoriModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AktivitasRepository @Inject constructor(
    private val api: ApiService
) {
    suspend fun getAktivitas(
        idUser: Int, umur: Int
    ):ArrayList<AktivitasModel>{
        return api.getAktivitas("", idUser, umur)
    }

    suspend fun getKategori(
    ):ArrayList<KategoriModel>{
        return api.getKategori("")
    }

}