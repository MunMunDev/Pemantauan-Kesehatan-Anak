package com.abcd.pemantauankesehatananak.ui.activity.admin.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.abcd.pemantauankesehatananak.R
import com.abcd.pemantauankesehatananak.databinding.ActivityAdminMainBinding
import com.abcd.pemantauankesehatananak.ui.activity.admin.aktivitas.AdminAktivitasActivity
import com.abcd.pemantauankesehatananak.ui.activity.admin.akun.AdminAkunActivity
import com.abcd.pemantauankesehatananak.ui.activity.admin.kategori.AdminKategoriActivity
import com.abcd.pemantauankesehatananak.ui.activity.admin.milestone.AdminMilestoneActivity
import com.abcd.pemantauankesehatananak.utils.KontrolNavigationDrawer

class AdminMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminMainBinding
    private lateinit var kontrolNavigationDrawer: KontrolNavigationDrawer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTopAppBar()
        setButton()
    }

    private fun setTopAppBar() {
        this.binding.apply {
            myAppBar.tvTitle.text = "Halaman Admin"
            kontrolNavigationDrawer = KontrolNavigationDrawer(this@AdminMainActivity)
            kontrolNavigationDrawer.cekSebagai(navView)
            kontrolNavigationDrawer.onClickItemNavigationDrawer(navView, drawerLayoutMain, myAppBar.ivNavDrawer, this@AdminMainActivity)
        }
    }

    private fun setButton() {
        binding.apply {
            cvKategori.setOnClickListener {
                startActivity(Intent(this@AdminMainActivity, AdminKategoriActivity::class.java))
                finish()
            }
            cvAktivitas.setOnClickListener {
                startActivity(Intent(this@AdminMainActivity, AdminAktivitasActivity::class.java))
                finish()
            }
            cvMilestone.setOnClickListener {
                startActivity(Intent(this@AdminMainActivity, AdminMilestoneActivity::class.java))
                finish()
            }
            cvAkun.setOnClickListener {
                startActivity(Intent(this@AdminMainActivity, AdminAkunActivity::class.java))
                finish()
            }
        }
    }
}