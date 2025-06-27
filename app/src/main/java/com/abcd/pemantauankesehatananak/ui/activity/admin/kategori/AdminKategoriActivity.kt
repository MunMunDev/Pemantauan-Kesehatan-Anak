package com.abcd.pemantauankesehatananak.ui.activity.admin.kategori

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.abcd.pemantauankesehatananak.R
import com.abcd.pemantauankesehatananak.adapter.AdminKategoriAdapter
import com.abcd.pemantauankesehatananak.data.model.KategoriModel
import com.abcd.pemantauankesehatananak.data.model.ResponseModel
import com.abcd.pemantauankesehatananak.databinding.ActivityAdminKategoriBinding
import com.abcd.pemantauankesehatananak.databinding.AlertDialogAdminKategoriBinding
import com.abcd.pemantauankesehatananak.utils.KontrolNavigationDrawer
import com.abcd.pemantauankesehatananak.utils.LoadingAlertDialog
import com.abcd.pemantauankesehatananak.utils.OnClickItem
import com.abcd.pemantauankesehatananak.utils.network.UIState
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

@AndroidEntryPoint
class AdminKategoriActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminKategoriBinding
    private val viewModel: AdminKategoriViewModel by viewModels()
    private lateinit var kontrolNavigationDrawer: KontrolNavigationDrawer
    private var loading = LoadingAlertDialog()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminKategoriBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTopAppBar()
        setButton()
        fetchKategori()
        getKategori()
        getTambahKategori()
        getUpdateKategori()
    }

    private fun setTopAppBar() {
        this.binding.apply {
            myAppBar.tvTitle.text = "Halaman Admin"
            kontrolNavigationDrawer = KontrolNavigationDrawer(this@AdminKategoriActivity)
            kontrolNavigationDrawer.cekSebagai(navView)
            kontrolNavigationDrawer.onClickItemNavigationDrawer(navView, drawerLayoutMain, myAppBar.ivNavDrawer, this@AdminKategoriActivity)
        }
    }

    private fun setButton() {
        binding.apply {
            btnTambah.setOnClickListener {
                setShowDialogTambah()
            }
        }
    }

    private fun setShowDialogTambah() {
        val view = AlertDialogAdminKategoriBinding.inflate(layoutInflater)

        val alertDialog = AlertDialog.Builder(this@AdminKategoriActivity)
        alertDialog.setView(view.root)
            .setCancelable(true)
        val dialogInputan = alertDialog.create()
        dialogInputan.show()

        view.apply {
            btnSimpan.setOnClickListener {
                var cek = true
                if(etKategori.text.toString().trim().isEmpty()){
                    etKategori.error = "Tidak Boleh Kosong"
                    cek = false
                }
                if(etDeskripsi.text.toString().trim().isEmpty()){
                    etDeskripsi.error = "Tidak Boleh Kosong"
                    cek = false
                }

                if(cek){
                    val kategori = etKategori.text.toString().trim()
                    val deskripsi = etDeskripsi.text.toString().trim()
                    postTambahKategori(kategori, deskripsi)

                    dialogInputan.dismiss()
                }
            }

            btnBatal.setOnClickListener {
                dialogInputan.dismiss()
            }
        }
    }

    private fun postTambahKategori(kategori: String, deskripsi: String) {
        viewModel.postTambahKategori(kategori, deskripsi)
    }

    private fun getTambahKategori(){
        viewModel.getTambahKategori.observe(this@AdminKategoriActivity){result->
            when(result){
                is UIState.Loading-> loading.alertDialogLoading(this@AdminKategoriActivity)
                is UIState.Failure-> setFailureTambahKategori(result.message)
                is UIState.Success-> setSuccessTambahKategori(result.data)
            }
        }
    }

    private fun setFailureTambahKategori(message: String) {
        Toast.makeText(this@AdminKategoriActivity, message, Toast.LENGTH_SHORT).show()
        loading.alertDialogCancel()
    }

    private fun setSuccessTambahKategori(data: ResponseModel) {
        loading.alertDialogCancel()
        if(data.status=="0"){
            Toast.makeText(this@AdminKategoriActivity, "Berhasil", Toast.LENGTH_SHORT).show()
            fetchKategori()
        } else{
            Toast.makeText(this@AdminKategoriActivity, data.message_response, Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchKategori() {
        viewModel.fetchKategori()
    }

    private fun getKategori(){
        viewModel.getKategori.observe(this@AdminKategoriActivity){result->
            when(result){
                is UIState.Loading-> {}
                is UIState.Failure-> setFailureFetchKategori(result.message)
                is UIState.Success-> setSuccessFetchKategori(result.data)
            }
        }
    }

    private fun setFailureFetchKategori(message: String) {
        Toast.makeText(this@AdminKategoriActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun setSuccessFetchKategori(data: ArrayList<KategoriModel>) {
        if(data.isNotEmpty()){
            setAdapter(data)
        } else{
            Toast.makeText(this@AdminKategoriActivity, "Tidak ada data", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setAdapter(data: ArrayList<KategoriModel>) {
        val adapterKategori = AdminKategoriAdapter(data, object : OnClickItem.ClickAdminKategori {
            override fun clickDeskripsi(deskripsi: String) {

            }

            override fun clickSetting(kategori: KategoriModel, it: View) {
                val popupMenu = PopupMenu(this@AdminKategoriActivity, it)
                popupMenu.inflate(R.menu.popup_edit_hapus)
                popupMenu.setOnMenuItemClickListener(object :
                    PopupMenu.OnMenuItemClickListener {
                    override fun onMenuItemClick(menuItem: MenuItem?): Boolean {
                        when (menuItem!!.itemId) {
                            R.id.edit -> {
                                setShowDialogUpdate(kategori)
                                return true
                            }
                            R.id.hapus -> {
                                Toast.makeText(this@AdminKategoriActivity, "Tidak dapat dihapus", Toast.LENGTH_SHORT).show()
                                return true
                            }
                        }
                        return true
                    }

                })
                popupMenu.show()
            }

        })
        binding.rvKategori.apply {
            adapter = adapterKategori
            layoutManager = LinearLayoutManager(this@AdminKategoriActivity, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun setShowDialogUpdate(kategori: KategoriModel) {
        val view = AlertDialogAdminKategoriBinding.inflate(layoutInflater)

        val alertDialog = AlertDialog.Builder(this@AdminKategoriActivity)
        alertDialog.setView(view.root)
            .setCancelable(true)
        val dialogInputan = alertDialog.create()
        dialogInputan.show()

        view.apply {
            etKategori.setText(kategori.kategori)
            etDeskripsi.setText(kategori.deskripsi)

            btnSimpan.setOnClickListener {
                var cek = true
                if(etKategori.text.toString().trim().isEmpty()){
                    etKategori.error = "Tidak Boleh Kosong"
                    cek = false
                }
                if(etDeskripsi.text.toString().trim().isEmpty()){
                    etDeskripsi.error = "Tidak Boleh Kosong"
                    cek = false
                }

                if(cek){
                    val kategori = etKategori.text.toString().trim()
                    val deskripsi = etDeskripsi.text.toString().trim()
                    postTambahKategori(kategori, deskripsi)

                    dialogInputan.dismiss()
                }
            }

            btnBatal.setOnClickListener {
                dialogInputan.dismiss()
            }
        }
    }

    private fun getUpdateKategori() {
        viewModel.getUpdateKategori.observe(this@AdminKategoriActivity){result->
            when(result){
                is UIState.Loading-> loading.alertDialogLoading(this@AdminKategoriActivity)
                is UIState.Failure-> setFailureUpdateKategori(result.message)
                is UIState.Success-> setSuccessUpdateKategori(result.data)
            }
        }
    }

    private fun setSuccessUpdateKategori(data: ResponseModel) {
        loading.alertDialogCancel()
        if(data.status=="0"){
            Toast.makeText(this@AdminKategoriActivity, "Berhasil Update", Toast.LENGTH_SHORT).show()
            fetchKategori()
        } else{
            Toast.makeText(this@AdminKategoriActivity, data.message_response, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setFailureUpdateKategori(message: String) {
        loading.alertDialogCancel()
        Toast.makeText(this@AdminKategoriActivity, message, Toast.LENGTH_SHORT).show()
    }
}
