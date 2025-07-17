package com.abcd.pemantauankesehatananak.data.repository

import com.abcd.pemantauankesehatananak.data.database.api.ApiService
import com.abcd.pemantauankesehatananak.data.model.KategoriModel
import com.abcd.pemantauankesehatananak.data.model.MilestoneModel
import com.abcd.pemantauankesehatananak.data.model.PelayananModel
import com.abcd.pemantauankesehatananak.data.model.ResponseModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PemeriksaanRepository @Inject constructor(
    private val api: ApiService
) {
    suspend fun getPemeriksaan(): ArrayList<PelayananModel> {
        return api.getPemeriksaan("")
    }
}