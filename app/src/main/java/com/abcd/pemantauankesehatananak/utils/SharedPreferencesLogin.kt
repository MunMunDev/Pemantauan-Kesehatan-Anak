package com.abcd.pemantauankesehatananak.utils

import android.content.Context

class SharedPreferencesLogin(val context: Context) {
    val keyIdUser = "keyIdUser"
    val keyNoKtp = "keyNoKtp"
    val keyNama = "keyNama"
    val keyNomorHp = "keyNomorHp"
    val keyAlamat = "keyAlamat"
    val keyNamaAnak = "keyNamaAnak"
    val keyTanggalLahir = "keyTanggalLahir"
    val keyJk = "keyJk"
    val keyEmail = "keyEmail"
    val keyUsername = "keyUsername"
    val keyPassword = "keyPassword"
    val keySebagai = "keySebagai"

    var sharedPref = context.getSharedPreferences("sharedpreference_login", Context.MODE_PRIVATE)
    var editPref = sharedPref.edit()

    fun setLogin(
        id_user:Int, noKtp:String, nama:String, alamat:String, nomorHp:String, namaAnak:String,
        jenisKelamin:String, tanggalLahir:String, email:String, username:String,
        password:String, sebagai:String
    ){
        editPref.apply{
            putInt(keyIdUser, id_user)
            putString(keyNoKtp, noKtp)
            putString(keyNama, nama)
            putString(keyNomorHp, nomorHp)
            putString(keyAlamat, alamat)
            putString(keyNamaAnak, namaAnak)
            putString(keyTanggalLahir, tanggalLahir)
            putString(keyJk, jenisKelamin)
            putString(keyEmail, email)
            putString(keyUsername, username)
            putString(keyPassword, password)
            putString(keySebagai, sebagai)

            apply()
        }
    }

    fun setLogout(
    ){
        editPref.apply{
            putInt(keyIdUser, 0)
            putString(keyNoKtp, "")
            putString(keyNama, "")
            putString(keyNomorHp, "")
            putString(keyAlamat, "")
            putString(keyNamaAnak, "")
            putString(keyTanggalLahir, "")
            putString(keyJk, "")
            putString(keyEmail, "")
            putString(keyUsername, "")
            putString(keyPassword, "")
            putString(keySebagai, "")

            apply()
        }
    }

    fun getIdUser(): Int{
        return sharedPref.getInt(keyIdUser, 0)
    }
    fun getNoKtp():String{
        return sharedPref.getString(keyNoKtp, "").toString()
    }
    fun getNama():String{
        return sharedPref.getString(keyNama, "").toString()
    }
    fun getNomorHp():String{
        return sharedPref.getString(keyNomorHp, "").toString()
    }
    fun getAlamat():String{
        return sharedPref.getString(keyAlamat, "").toString()
    }
    fun getNamaAnak():String{
        return sharedPref.getString(keyNamaAnak, "").toString()
    }
    fun getTanggalLahir(): String{
        return sharedPref.getString(keyTanggalLahir, "").toString()
    }
    fun getJenisKelamin():String{
        return sharedPref.getString(keyJk, "").toString()
    }
    fun getUsername():String{
        return sharedPref.getString(keyUsername, "").toString()
    }
    fun getEmail():String{
        return sharedPref.getString(keyEmail, "").toString()
    }
    fun getPassword(): String{
        return sharedPref.getString(keyPassword, "").toString()
    }
    fun getSebagai(): String{
        return sharedPref.getString(keySebagai, "").toString()
    }
}