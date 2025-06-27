package com.abcd.pemantauankesehatananak.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.ImageView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.abcd.pemantauankesehatananak.R
import com.abcd.pemantauankesehatananak.ui.activity.admin.aktivitas.AdminAktivitasActivity
import com.abcd.pemantauankesehatananak.ui.activity.admin.akun.AdminAkunActivity
import com.abcd.pemantauankesehatananak.ui.activity.admin.kategori.AdminKategoriActivity
import com.abcd.pemantauankesehatananak.ui.activity.admin.main.AdminMainActivity
import com.abcd.pemantauankesehatananak.ui.activity.admin.milestone.AdminMilestoneActivity
import com.abcd.pemantauankesehatananak.ui.activity.login.LoginActivity

//import com.abcd.e_kalontong.ui.activity.login.LoginActivity

class KontrolNavigationDrawer(var context: Context) {
    var sharedPreferences = SharedPreferencesLogin(context)
    fun cekSebagai(navigation: com.google.android.material.navigation.NavigationView){
        if(sharedPreferences.getSebagai() == "admin"){
            navigation.menu.clear()
            navigation.inflateMenu(R.menu.nav_menu_admin)
        }
    }

    @SuppressLint("ResourceAsColor")
    fun onClickItemNavigationDrawer(navigation: com.google.android.material.navigation.NavigationView, navigationLayout: DrawerLayout, igNavigation:ImageView, activity: Activity){
        navigation.setNavigationItemSelectedListener {
            if(sharedPreferences.getSebagai() == "admin"){
                when(it.itemId){
                    R.id.adminNavDrawerHome -> {
                        val intent = Intent(context, AdminMainActivity::class.java)
                        context.startActivity(intent)
                        activity.finish()
                    }
                    R.id.adminNavDrawerKategori -> {
                        val intent = Intent(context, AdminKategoriActivity::class.java)
                        context.startActivity(intent)
                        activity.finish()
                    }
                    R.id.adminNavDrawerAktivitas -> {
                        val intent = Intent(context, AdminAktivitasActivity::class.java)
                        context.startActivity(intent)
                        activity.finish()
                    }
                    R.id.adminNavDrawerMilestone-> {
                        val intent = Intent(context, AdminMilestoneActivity::class.java)
                        context.startActivity(intent)
                        activity.finish()
                    }
                    R.id.adminNavDrawerAkun -> {
                        val intent = Intent(context, AdminAkunActivity::class.java)
                        context.startActivity(intent)
                        activity.finish()
                    }
                    R.id.adminBtnKeluar ->{
                        logoutAdmin(activity)
                    }
                }
            }
            navigationLayout.setBackgroundColor(R.color.white)
            navigationLayout.closeDrawer(GravityCompat.START)
            true
        }
        // garis 3 navigasi
        igNavigation.setOnClickListener {
            navigationLayout.openDrawer(GravityCompat.START)
        }
    }

    fun logoutAdmin(activity: Activity){
        sharedPreferences.setLogout()
        context.startActivity(Intent(context, LoginActivity::class.java))
        activity.finish()
    }

//    fun logoutAdmin(activity: Activity){
//        sharedPreferences.setLogin(0, "", "","", "","")
//        context.startActivity(Intent(context, LoginActivity::class.java))
//        activity.finish()
//    }
}