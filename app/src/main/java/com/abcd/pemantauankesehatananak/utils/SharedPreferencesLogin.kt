package com.abcd.pemantauankesehatananak.utils

import android.content.Context

class SharedPreferencesLogin(val context: Context) {
    val keyIdUser = "keyIdUser"
    val keyNoBpjs = "keyNoBpjs"
    val keyNama = "keyNama"
    val keyNomorHp = "keyNomorHp"
    val keyAlamat = "keyAlamat"
    val keyNamaAnak = "keyNamaAnak"
    val keyTanggalLahir = "keyTanggalLahir"
    val keyJk = "keyJk"
    val keyUsername = "keyUsername"
    val keyPassword = "keyPassword"
    val keySebagai = "keySebagai"

    var sharedPref = context.getSharedPreferences("sharedpreference_login", Context.MODE_PRIVATE)
    var editPref = sharedPref.edit()

    fun setLogin(
        id_user:Int, noBpjs:String, nama:String, nomorHp:String, alamat:String, namaAnak:String,
        tanggalLahir:String, jenisKelamin:String, username:String,
        password:String, sebagai:String
    ){
        editPref.apply{
            putInt(keyIdUser, id_user)
            putString(keyNoBpjs, noBpjs)
            putString(keyNama, nama)
            putString(keyNomorHp, nomorHp)
            putString(keyAlamat, alamat)
            putString(keyNamaAnak, namaAnak)
            putString(keyTanggalLahir, tanggalLahir)
            putString(keyJk, jenisKelamin)
            putString(keyUsername, username)
            putString(keyPassword, password)
            putString(keySebagai, sebagai)

            apply()
        }
    }

    fun getIdUser(): Int{
        return sharedPref.getInt(keyIdUser, 0)
    }
    fun keyNoBpjs():String{
        return sharedPref.getString(keyNoBpjs, "").toString()
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
    fun getNamAnak():String{
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
    fun getPassword(): String{
        return sharedPref.getString(keyPassword, "").toString()
    }
    fun getSebagai(): String{
        return sharedPref.getString(keySebagai, "").toString()
    }
}