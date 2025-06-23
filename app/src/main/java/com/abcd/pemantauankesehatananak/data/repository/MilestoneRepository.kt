package com.abcd.pemantauankesehatananak.data.repository

import com.abcd.pemantauankesehatananak.data.database.api.ApiService
import com.abcd.pemantauankesehatananak.data.model.KategoriModel
import com.abcd.pemantauankesehatananak.data.model.MilestoneModel
import com.abcd.pemantauankesehatananak.data.model.ResponseModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MilestoneRepository @Inject constructor(
    private val api: ApiService
) {
    suspend fun getKategori(): ArrayList<KategoriModel> {
        return api.getKategori("")
    }

    suspend fun getMilestone(
        idUser: Int
    ): ArrayList<MilestoneModel> {
        return api.getMilestone("", idUser)
    }

    suspend fun postUpdateCheck(
        idUser: Int,
        idAktivitas: Int,
    ): ResponseModel {
        return api.postUpdateCheckMilestone("", idUser, idAktivitas)
    }
}