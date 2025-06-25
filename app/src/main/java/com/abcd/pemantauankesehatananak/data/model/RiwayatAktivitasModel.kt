package com.abcd.pemantauankesehatananak.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class RiwayatAktivitasModel(

    @SerializedName("id_riwayat_aktivitas")
    var id_riwayat_aktivitas: Int? = null,

    @SerializedName("id_user")
    var id_user: String? = null,

    @SerializedName("id_aktivitas")
    var id_aktivitas: String? = null,

    @SerializedName("tanggal")
    var tanggal: String? = null,

    @SerializedName("waktu")
    var waktu: String? = null,

    @SerializedName("aktivitas")
    var aktivitas: AktivitasModel? = null,

)