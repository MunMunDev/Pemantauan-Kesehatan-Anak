package com.abcd.pemantauankesehatananak.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class AktivitasModel(

    @SerializedName("id_aktivitas")
    var id_aktivitas: Int? = null,

    @SerializedName("judul")
    var judul: String? = null,

    @SerializedName("deskripsi")
    var deskripsi: String? = null,

    @SerializedName("id_kategori")
    var id_kategori: String? = null,

    @SerializedName("usia_minimal")
    var usia_minimal: String? = null,

    @SerializedName("video_url")
    var video_url: String? = null,

    @SerializedName("kategori")
    var kategori: KategoriModel? = null,

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(KategoriModel::class.java.classLoader)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id_aktivitas)
        parcel.writeString(judul)
        parcel.writeString(deskripsi)
        parcel.writeString(id_kategori)
        parcel.writeString(usia_minimal)
        parcel.writeString(video_url)
        parcel.writeParcelable(kategori, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AktivitasModel> {
        override fun createFromParcel(parcel: Parcel): AktivitasModel {
            return AktivitasModel(parcel)
        }

        override fun newArray(size: Int): Array<AktivitasModel?> {
            return arrayOfNulls(size)
        }
    }
}