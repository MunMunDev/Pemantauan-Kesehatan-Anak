package com.abcd.pemantauankesehatananak.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class UserModel (
    @SerializedName("id_user")
    var idUser: Int? = null,

    @SerializedName("no_bpjs")
    var no_bpjs: String? = null,

    @SerializedName("nama")
    var nama: String? = null,

    @SerializedName("alamat")
    var alamat: String? = null,

    @SerializedName("nomor_hp")
    var nomorHp: String? = null,

    @SerializedName("nama_anak")
    var nama_anak: String? = null,

    @SerializedName("jk")
    var jk: String? = null,

    @SerializedName("tanggal_lahir")
    var tanggal_lahir: String? = null,

    @SerializedName("username")
    var username: String? = null,

    @SerializedName("password")
    var password: String? = null,

    @SerializedName("sebagai")
    var sebagai: String? = null,
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(idUser)
        parcel.writeString(no_bpjs)
        parcel.writeString(nama)
        parcel.writeString(alamat)
        parcel.writeString(nomorHp)
        parcel.writeString(nama_anak)
        parcel.writeString(jk)
        parcel.writeString(tanggal_lahir)
        parcel.writeString(username)
        parcel.writeString(password)
        parcel.writeString(sebagai)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserModel> {
        override fun createFromParcel(parcel: Parcel): UserModel {
            return UserModel(parcel)
        }

        override fun newArray(size: Int): Array<UserModel?> {
            return arrayOfNulls(size)
        }
    }
}