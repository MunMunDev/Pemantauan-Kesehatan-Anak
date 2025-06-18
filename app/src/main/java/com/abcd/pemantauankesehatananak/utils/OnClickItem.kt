package com.abcd.pemantauankesehatananak.utils

import android.view.View
import com.abcd.pemantauankesehatananak.data.model.BeritaModel
import com.abcd.pemantauankesehatananak.data.model.BerkasModel
import com.abcd.pemantauankesehatananak.data.model.UserModel

interface OnClickItem {
    interface ClickBerita{
        fun clickBerita(
            berita: BeritaModel
        )
    }

    interface ClickBerkas{
        fun clickBerkas(
            berita: BerkasModel
        )
    }

    interface ClickRiwayatPesanan{
        fun clickRiwayatPesanan(
            idPemesanan: Int
        )
    }

    interface ClickAkun{
        fun clickItemSetting(akun: UserModel, it: View)
        fun clickItemAlamat(alamat: String, it: View)
    }


    interface ClickAdminUser{
        fun clickItemNama(title:String, nama: String)
        fun clickItemAlamat(title:String, alamat: String)
        fun clickItemUsername(title:String, username: String)
        fun clickItemSetting(wo: UserModel, it: View)
    }

}