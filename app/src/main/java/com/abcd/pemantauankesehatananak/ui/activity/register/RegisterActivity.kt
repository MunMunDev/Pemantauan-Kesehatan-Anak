package com.abcd.pemantauankesehatananak.ui.activity.register

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.abcd.pemantauankesehatananak.R
import com.abcd.pemantauankesehatananak.data.model.ResponseModel
import com.abcd.pemantauankesehatananak.databinding.ActivityRegisterBinding
import com.abcd.pemantauankesehatananak.databinding.AlertDialogLoadingBinding
import com.abcd.pemantauankesehatananak.utils.LoadingAlertDialog
import com.abcd.pemantauankesehatananak.utils.network.UIState
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()
    private var selectedDate: Date? = null
    private var selectedGender: String? = null
    private var loading = LoadingAlertDialog()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupButton()
        getRegister()
    }

    private fun setupButton() {
        binding.apply {
            rgGender.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.rbLakiLaki -> selectedGender = "Laki-laki"
                    R.id.rbPerempuan -> selectedGender = "Perempuan"
                }
            }

            btnTanggalLahir.setOnClickListener {
                showDatePicker()
            }

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

    private fun showDatePicker() {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Pilih Tanggal Lahir")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()

        datePicker.addOnPositiveButtonClickListener { selection ->
            selectedDate = Date(selection)
            val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
            binding.btnTanggalLahir.text = dateFormat.format(selectedDate!!)
        }

        datePicker.show(supportFragmentManager, "DATE_PICKER")
    }

    private fun validateHasFilled(): Boolean {
        binding.apply {
            if (etNoKtp.text.toString().isEmpty()) {
                etNoKtp.error = "Nama harus diisi"
                return false
            }
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
            if (etNamaAnak.text.toString().isEmpty()) {
                etNamaAnak.error = "Nomor HP harus diisi"
                return false
            }
            if (btnTanggalLahir.text.toString() == resources.getString(R.string.ket_klik_tanggal)) {
                btnTanggalLahir.error = "Tanggal harus diisi"
                return false
            }
            if (selectedDate == null) {
                Toast.makeText(this@RegisterActivity, "Pilih Jenis Kelamin Anak", Toast.LENGTH_SHORT).show()
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
            val noKtp = etNoKtp.text.toString()
            val nama = etNama.text.toString()
            val alamat = etAlamat.text.toString()
            val nomorHp = etNomorHp.text.toString()
            val namaAnak = etNamaAnak.text.toString()
            val tanggaLahir = btnTanggalLahir.text.toString()
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()

            postRegister(
                noKtp, nama, alamat, nomorHp, namaAnak, tanggaLahir, selectedGender!!, username, password
            )
        }
    }

    private fun postRegister(
       noKtp: String, nama: String, alamat: String, nomorHp: String, namaAnak: String,
       tanggalLahir: String, jk: String, username: String, password: String,
    ){
        viewModel.postRegister(
            noKtp, nama, alamat, nomorHp, namaAnak, tanggalLahir, jk, username, password
        )
    }

    private fun getRegister(){
        viewModel.getRegister().observe(this@RegisterActivity){result->
            when(result){
                is UIState.Loading-> loading.alertDialogLoading(this@RegisterActivity)
                is UIState.Success-> setSuccessRegister(result.data)
                is UIState.Failure-> setFailureRegister(result.message)
            }
        }
    }

    private fun setSuccessRegister(data: ResponseModel) {
        loading.alertDialogCancel()
        if(data.status == "0") {
            Toast.makeText(this@RegisterActivity, "Berhasil Daftar", Toast.LENGTH_SHORT).show()
            finish()
        } else{
            Toast.makeText(this@RegisterActivity, data.message_response, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setFailureRegister(message: String) {
        loading.alertDialogCancel()
        Toast.makeText(this@RegisterActivity, message, Toast.LENGTH_SHORT).show()
    }

}