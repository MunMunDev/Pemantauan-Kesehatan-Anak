package com.abcd.pemantauankesehatananak.ui.fragment.user.profile

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import com.abcd.pemantauankesehatananak.R
import com.abcd.pemantauankesehatananak.data.model.ResponseModel
import com.abcd.pemantauankesehatananak.data.model.UserModel
import com.abcd.pemantauankesehatananak.databinding.AlertDialogAkunBinding
import com.abcd.pemantauankesehatananak.databinding.FragmentProfileBinding
import com.abcd.pemantauankesehatananak.ui.activity.login.LoginActivity
import com.abcd.pemantauankesehatananak.utils.LoadingAlertDialog
import com.abcd.pemantauankesehatananak.utils.SharedPreferencesLogin
import com.abcd.pemantauankesehatananak.utils.TanggalDanWaktu
import com.abcd.pemantauankesehatananak.utils.network.UIState
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val viewModel : ProfileViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferencesLogin
    private lateinit var tanggalDanWaktu: TanggalDanWaktu
    private var selectedDate: Date? = null
    private lateinit var tempUser: UserModel
    @Inject lateinit var loading: LoadingAlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = SharedPreferencesLogin(requireContext())
        tanggalDanWaktu = TanggalDanWaktu()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTopAppNavDrawer()
        setViewData()
        setButton()
        getUpdateDiri()
    }

    @SuppressLint("SetTextI18n")
    private fun setTopAppNavDrawer() {
        binding.navTopBar.apply {
            ivBack.visibility = View.GONE
            ivNavDrawer.visibility = View.GONE
            tvTitle.text = "Profile Anda"
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setViewData(){
        binding.apply {
            tvNoKtp.text = sharedPreferences.getNoKtp()
            tvNama.text = sharedPreferences.getNama()
            tvNomorHp.text = sharedPreferences.getNomorHp()
            tvAlamat.text = sharedPreferences.getAlamat()
            tvNamaAnak.text = sharedPreferences.getNamaAnak()
            tvTanggalLahir.text = sharedPreferences.getTanggalLahir()
            tvTanggalLahir.text = sharedPreferences.getTanggalLahir()
            tvUsia.text = "${tanggalDanWaktu.hitungUsiaDalamBulan(sharedPreferences.getTanggalLahir())} Bulan"
            tvJenisKelamin.text = sharedPreferences.getJenisKelamin()
            tvUsername.text = sharedPreferences.getUsername()
            tvPassword.text = sharedPreferences.getPassword()
        }
    }

    private fun setButton() {
        binding.apply {
            btnUbahData.setOnClickListener {
                setShowEditProfile()
            }
            btnLogout.setOnClickListener {
                sharedPreferences.setLogout()
                startActivity(Intent(requireActivity(), LoginActivity::class.java))
                requireActivity().finish()
            }
        }
    }

    private fun setShowEditProfile() {
        val view = AlertDialogAkunBinding.inflate(layoutInflater)
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setView(view.root)
            .setCancelable(false)
        val dialogInputan = alertDialog.create()
        dialogInputan.show()

        view.apply {
            etEditNoKtp.setText(sharedPreferences.getNoKtp())
            etEditNama.setText(sharedPreferences.getNama())
            etEditNomorHp.setText(sharedPreferences.getNomorHp())
            etEditAlamat.setText(sharedPreferences.getAlamat())
            etEditNamaAnak.setText(sharedPreferences.getNamaAnak())
            etEditTanggalLahir.text = sharedPreferences.getTanggalLahir()
            etEditUsername.setText(sharedPreferences.getUsername())
            etEditPassword.setText(sharedPreferences.getPassword())
            when(sharedPreferences.getJenisKelamin()){
                "Laki-laki"-> rbLakiLaki.isChecked = true
                "Perempuan"-> rbPerempuan.isChecked = true
            }

            etEditTanggalLahir.setOnClickListener {
                showDatePicker(view, sharedPreferences.getTanggalLahir())
            }

            var selectedGender = sharedPreferences.getJenisKelamin()
            rgGender.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.rbLakiLaki -> selectedGender = "Laki-laki"
                    R.id.rbPerempuan -> selectedGender = "Perempuan"
                }
            }

            btnSimpan.setOnClickListener {
                var cek = false
                if(etEditNoKtp.toString().isEmpty()){
                    etEditNoKtp.error = "Tidak Boleh Kosong"
                    cek = true
                }
                if(etEditNama.toString().isEmpty()){
                    etEditNama.error = "Tidak Boleh Kosong"
                    cek = true
                }
                if(etEditAlamat.toString().isEmpty()){
                    etEditAlamat.error = "Tidak Boleh Kosong"
                    cek = true
                }
                if(etEditNomorHp.toString().isEmpty()){
                    etEditNomorHp.error = "Tidak Boleh Kosong"
                    cek = true
                }
                if(etEditNamaAnak.toString().isEmpty()){
                    etEditNamaAnak.error = "Tidak Boleh Kosong"
                    cek = true
                }
                if(etEditTanggalLahir.text.toString() == resources.getString(R.string.ket_klik_tanggal)){
                    etEditTanggalLahir.error = "Tidak Boleh Kosong"
                    cek = true
                }
                if(etEditUsername.toString().isEmpty()){
                    etEditUsername.error = "Tidak Boleh Kosong"
                    cek = true
                }
                if(etEditPassword.toString().isEmpty()){
                    etEditPassword.error = "Tidak Boleh Kosong"
                    cek = true
                }

                if(!cek){
                    val noKtp = etEditNoKtp.text.toString()
                    val nama = etEditNama.text.toString()
                    val nomorHp = etEditNomorHp.text.toString()
                    val alamat = etEditAlamat.text.toString()
                    val namaAnak = etEditNamaAnak.text.toString()
                    val tanggalLahir = etEditTanggalLahir.text.toString()
                    val jenisKelamin = selectedGender
                    val username = etEditUsername.text.toString()
                    val password = etEditPassword.text.toString()
                    val usernameLama = sharedPreferences.getUsername()

                    tempUser = UserModel(
                        sharedPreferences.getIdUser(), noKtp,
                        nama, alamat, nomorHp, namaAnak, jenisKelamin, tanggalLahir,
                        username, password, usernameLama
                    )
                    postUpdateData(
                        sharedPreferences.getIdUser(), noKtp, nama, nomorHp, alamat,
                        namaAnak, tanggalLahir, jenisKelamin, username, password, usernameLama
                    )
                }
                dialogInputan.dismiss()
            }
            btnBatal.setOnClickListener {
                dialogInputan.dismiss()
            }
        }
    }

    private fun postUpdateData(
        idUser: Int,
        noKtp: String,
        nama: String,
        nomorHp: String,
        alamat: String,
        namaAnak: String,
        tanggalLahir: String,
        jenisKelamin: String,
        username: String,
        password: String,
        usernameLama: String
    ) {
        viewModel.postUpdateDataDiri(
            idUser, noKtp, nama, nomorHp, alamat, namaAnak, tanggalLahir,
            jenisKelamin, username, password, usernameLama
        )
    }

    private fun getUpdateDiri(){
        viewModel.getResponseUpdateDataDiri.observe(viewLifecycleOwner){result->
            when(result){
                is UIState.Loading-> loading.alertDialogLoading(requireContext())
                is UIState.Success-> setSuccessUpdateDiri(result.data)
                is UIState.Failure-> setFailureUpdateDiri(result.message)
            }
        }
    }

    private fun setSuccessUpdateDiri(data: ResponseModel) {
        if(data.status == "0"){
            Toast.makeText(requireContext(), "Berhasil Update", Toast.LENGTH_SHORT).show()
            tempUser.apply {
                sharedPreferences.setLogin(
                    idUser!!, no_ktp!!, nama!!, alamat!!, nomorHp!!, nama_anak!!, jk!!, tanggal_lahir!!, username!!, password!!, "user"
                )
                setViewData()
            }
        } else{
            Toast.makeText(requireContext(), data.message_response, Toast.LENGTH_SHORT).show()
        }
        loading.alertDialogCancel()
        tempUser = UserModel()
    }

    private fun setFailureUpdateDiri(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        loading.alertDialogCancel()
        tempUser = UserModel()
    }

    private fun showDatePicker(view: AlertDialogAkunBinding, tanggal: String) {
        var vTahun = 2025
        var vBulan = 1
        var vTanggal = 1
        try{
            val listTanggal = tanggal.split("-")
            vTahun = listTanggal[0].trim().toInt()
            vBulan = listTanggal[1].trim().toInt()-1 // 0=jan, 1= feb, dst
            vTanggal = listTanggal[2].trim().toInt()
        } catch (ex: Exception){
            Log.d("TAG", "showDatePicker: ${ex.message}")
        }

        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        calendar.set(vTahun, vBulan, vTanggal) // Tahun, Bulan, Tanggal

        val selectedDateInMillis = calendar.timeInMillis

        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Pilih Tanggal Lahir")
            .setSelection(selectedDateInMillis)
            .build()

        datePicker.addOnPositiveButtonClickListener { selection ->
            selectedDate = Date(selection)
            val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            view.etEditTanggalLahir.text = dateFormat.format(selectedDate!!)
        }

        datePicker.show(childFragmentManager, "DATE_PICKER")
    }
}