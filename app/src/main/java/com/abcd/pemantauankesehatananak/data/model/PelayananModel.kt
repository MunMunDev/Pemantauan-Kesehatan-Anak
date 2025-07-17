package com.abcd.pemantauankesehatananak.data.model

import com.google.gson.annotations.SerializedName

class PelayananModel (
    @SerializedName("id_pelayanan")
    val id_pelayanan: Int? = null ,

    @SerializedName("id_user")
    var id_user: Int? = null,

    @SerializedName("pelayanan")
    var pelayanan: String? = null,

    @SerializedName("hasil")
    var hasil: String? = null,

    @SerializedName("keterangan")
    var keterangan: String? = null,

    @SerializedName("catatan")
    var catatan: String? = null,

    @SerializedName("tanggal")
    var tanggal: String? = null,

    @SerializedName("waktu")
    var waktu: String? = null,

    @SerializedName("user")
    var user: UserModel? = null,
)