package com.abcd.pemantauankesehatananak.ui.activity.register

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.abcd.pemantauankesehatananak.databinding.ActivityRegisterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupButton()
    }

    private fun setupButton() {
        binding.apply {
            ivBack.setOnClickListener {
                onBackPressed()
            }

            btnRegistrasi.setOnClickListener {
                if (validateHasFilled()) {
                    // Complete registration
                    setRegistration()
                }
            }
        }
    }

    private fun validateHasFilled(): Boolean {
        binding.apply {
            if (etNama.text.toString().isEmpty()) {
                etNama.error = "Nama harus diisi"
                return false
            }
            if (etAlamat.text.toString().isEmpty()) {
                etAlamat.error = "Alamat harus diisi"
                return false
            }
            if (etNomorHp.text.toString().isEmpty()) {
                etNomorHp.error = "Nomor HP harus diisi"
                return false
            }
            if (etUsername.text.toString().isEmpty()) {
                etUsername.error = "Username harus diisi"
                return false
            }
            if (etPassword.text.toString().isEmpty()) {
                etPassword.error = "Password harus diisi"
                return false
            }
        }
        return true
    }

    private fun setRegistration() {
        binding.apply {
            val nama = etNama.text.toString()
            val alamat = etAlamat.text.toString()
            val nomorHp = etNomorHp.text.toString()
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()

            postRegister(
                nama, alamat, nomorHp, username, password,
            )
        }
    }

    private fun postRegister(
       nama: String, alamat: String, nomorHp: String, username: String, password: String,
    ){
        viewModel.postRegister(
            nama, alamat, nomorHp, username, password
        )
    }

}