package com.abcd.pemantauankesehatananak.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class KategoriModel (
    @SerializedName("id_kategori")
    val id_kategori: Int? = null,

    @SerializedName("kategori")
    var kategori: String? = null,

    @SerializedName("deskripsi")
    var deskripsi: String? = null,
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id_kategori)
        parcel.writeString(kategori)
        parcel.writeString(deskripsi)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<KategoriModel> {
        override fun createFromParcel(parcel: Parcel): KategoriModel {
            return KategoriModel(parcel)
        }

        override fun newArray(size: Int): Array<KategoriModel?> {
            return arrayOfNulls(size)
        }
    }
}