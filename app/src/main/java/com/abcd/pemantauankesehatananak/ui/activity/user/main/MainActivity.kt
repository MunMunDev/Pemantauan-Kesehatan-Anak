package com.abcd.pemantauankesehatananak.ui.activity.user.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.abcd.pemantauankesehatananak.R
import com.abcd.pemantauankesehatananak.databinding.ActivityMainBinding
import com.abcd.pemantauankesehatananak.ui.fragment.user.aktivitas.AktivitasFragment
import com.abcd.pemantauankesehatananak.ui.fragment.user.home.HomeFragment
import com.abcd.pemantauankesehatananak.ui.fragment.user.pemeriksaan.PemeriksaanFragment
import com.abcd.pemantauankesehatananak.ui.fragment.user.perkembangan.MilestoneFragment
import com.abcd.pemantauankesehatananak.ui.fragment.user.profile.ProfileFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, HomeFragment())
            .commit()

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> {
                    setHomeFragment()
                    true
                }
                R.id.menu_aktivitas -> {
                    setAktivitasFragment()
                    true
                }
                R.id.menu_milestone -> {
                    setMilestoneFragment()
                    true
                }
                R.id.menu_milestone -> {
                    setMilestoneFragment()
                    true
                }
                R.id.menu_pemeriksaan->{
                    setPemeriksaanFragment()
                    true
                }
                R.id.menu_profile -> {
                    setProfileFragment()
                    true
                }
                else -> false
            }
        }
    }

    private fun setHomeFragment(){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, HomeFragment())
            .commit()
    }

    fun setAktivitasFragment(){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, AktivitasFragment())
            .commit()
    }

    fun setMilestoneFragment(){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, MilestoneFragment())
            .commit()
    }

    fun setPemeriksaanFragment(){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, PemeriksaanFragment())
            .commit()
    }

    fun setProfileFragment(){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, ProfileFragment())
            .commit()
    }
}