package com.abcd.pemantauankesehatananak.data.model

import android.os.Parcel
import android.os.Parcelable
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

    @SerializedName("penanggung_jawab")
    var penanggung_jawab: String? = null,

    @SerializedName("jabatan")
    var jabatan: String? = null,

    @SerializedName("tanggal")
    var tanggal: String? = null,

    @SerializedName("waktu")
    var waktu: String? = null,

    @SerializedName("user")
    var user: UserModel? = null,
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(UserModel::class.java.classLoader)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id_pelayanan)
        parcel.writeValue(id_user)
        parcel.writeString(pelayanan)
        parcel.writeString(hasil)
        parcel.writeString(keterangan)
        parcel.writeString(catatan)
        parcel.writeString(penanggung_jawab)
        parcel.writeString(jabatan)
        parcel.writeString(tanggal)
        parcel.writeString(waktu)
        parcel.writeParcelable(user, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PelayananModel> {
        override fun createFromParcel(parcel: Parcel): PelayananModel {
            return PelayananModel(parcel)
        }

        override fun newArray(size: Int): Array<PelayananModel?> {
            return arrayOfNulls(size)
        }
    }
}