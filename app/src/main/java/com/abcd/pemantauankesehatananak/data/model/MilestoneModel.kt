package com.abcd.pemantauankesehatananak.data.model

import com.google.gson.annotations.SerializedName

class MilestoneModel (
    @SerializedName("id_milestone")
    val id_milestone: Int? = null ,

    @SerializedName("deskripsi")
    var deskripsi: String? = null,

    @SerializedName("usia_ideal")
    var usia_ideal: Int? = null,

    @SerializedName("id_kategori")
    var id_kategori: String? = null,

    @SerializedName("sudah_tercapai")
    var sudah_tercapai: Int? = null,

    @SerializedName("kategori")
    var kategori: KategoriModel? = null,
)