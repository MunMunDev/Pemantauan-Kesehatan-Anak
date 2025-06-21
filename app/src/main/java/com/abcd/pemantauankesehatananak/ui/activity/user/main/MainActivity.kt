package com.abcd.pemantauankesehatananak.ui.activity.user.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.abcd.pemantauankesehatananak.R
import com.abcd.pemantauankesehatananak.databinding.ActivityMainBinding
import com.abcd.pemantauankesehatananak.ui.fragment.user.panduan.PanduanFragment
import com.abcd.pemantauankesehatananak.ui.fragment.user.perkembangan.PerkembanganFragment
import com.abcd.pemantauankesehatananak.ui.fragment.user.rekomendasi.RekomendasiFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, RekomendasiFragment())
            .commit()

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_rekomendasi -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, RekomendasiFragment())
                        .commit()
                    true
                }

                R.id.menu_panduan -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, PanduanFragment())
                        .commit()
                    true
                }

                R.id.menu_perkembangan -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, PerkembanganFragment())
                        .commit()
                    true
                }

                else -> false
            }
        }
    }
}