package com.abcd.pemantauankesehatananak.data.repository

import com.abcd.pemantauankesehatananak.data.database.api.ApiService
import com.abcd.pemantauankesehatananak.data.model.KategoriModel
import com.abcd.pemantauankesehatananak.data.model.MilestoneModel
import com.abcd.pemantauankesehatananak.data.model.ResponseModel
import com.abcd.pemantauankesehatananak.data.model.YoutubeResultModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class YoutubeRepository @Inject constructor(
    private val api: ApiService
) {
    suspend fun getYoutube(
        id: String,
    ): YoutubeResultModel {
        return api.getYoutube(
            "snippet",
            id, "AIzaSyCRf2CYgVGWMig8sLklMHFitzLMSdTv6kc"
        )
    }

}