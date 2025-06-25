package com.abcd.pemantauankesehatananak.data.repository

import com.abcd.pemantauankesehatananak.data.database.api.ApiService
import com.abcd.pemantauankesehatananak.data.model.KategoriModel
import com.abcd.pemantauankesehatananak.data.model.MilestoneModel
import com.abcd.pemantauankesehatananak.data.model.ResponseModel
import com.abcd.pemantauankesehatananak.data.model.RiwayatAktivitasModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RiwayatAktivitasRepository @Inject constructor(
    private val api: ApiService
) {
    suspend fun getRiwayatAktivitas(
        idUser: Int, idAktivitas: Int
    ): ArrayList<RiwayatAktivitasModel> {
        return api.getRiwayatAktivitas("", idUser, idAktivitas)
    }

    suspend fun postUpdateCheck(
        idUser: Int,
        idAktivitas: Int,
    ): ResponseModel {
        return api.postUpdateCheckAktivitas("", idUser, idAktivitas)
    }
}