package com.abcd.pemantauankesehatananak.utils

import android.view.View
import com.abcd.pemantauankesehatananak.data.model.AktivitasModel
import com.abcd.pemantauankesehatananak.data.model.KategoriModel
import com.abcd.pemantauankesehatananak.data.model.MilestoneModel
import com.abcd.pemantauankesehatananak.data.model.UserModel

interface OnClickItem {
    interface ClickAktivitas{
        fun clickAktivitas(
            aktivitas: AktivitasModel
        )
    }
    interface ClickMilestone{
        fun clickMilestone(
            milestone: MilestoneModel
        )
    }
    interface ClickKategori{
        fun clickKategori(
            kategori: KategoriModel
        )
    }

    // Admin
    // Kategori
    interface ClickAdminKategori{
        fun clickDeskripsi(
            deskripsi: String
        )
        fun clickSetting(
            kategori: KategoriModel, it: View
        )
    }

}