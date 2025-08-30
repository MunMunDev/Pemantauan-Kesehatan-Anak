package com.abcd.pemantauankesehatananak.data.repository

import com.abcd.pemantauankesehatananak.data.database.api.ApiService
import com.abcd.pemantauankesehatananak.data.model.AktivitasModel
import com.abcd.pemantauankesehatananak.data.model.KategoriModel
import com.abcd.pemantauankesehatananak.data.model.ResponseModel
import com.abcd.pemantauankesehatananak.data.model.UserModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LupaPasswordRepository @Inject constructor(
    private val api: ApiService
) {
    suspend fun getLupaPassword(
        email: String
    ):UserModel{
        return api.getLupaPassword("", email)
    }

    suspend fun postKirimUsernamePassword(
        email: String, username: String, password: String
    ):ResponseModel{
        return api.postKirimUsernamePassword("", email, username, password)
    }

}