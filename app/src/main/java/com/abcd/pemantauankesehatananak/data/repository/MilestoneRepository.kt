package com.abcd.pemantauankesehatananak.data.repository

import com.abcd.pemantauankesehatananak.data.database.api.ApiService
import com.abcd.pemantauankesehatananak.data.model.AktivitasModel
import com.abcd.pemantauankesehatananak.data.model.MilestoneModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MilestoneRepository @Inject constructor(
    private val api: ApiService
) {
    suspend fun getMilestone(
        idUser: Int
    ): ArrayList<MilestoneModel> {
        return api.getMilestone("", idUser)
    }
}