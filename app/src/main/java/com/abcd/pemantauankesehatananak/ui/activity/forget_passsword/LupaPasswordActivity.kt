package com.abcd.pemantauankesehatananak.ui.activity.forget_passsword

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.abcd.pemantauankesehatananak.R
import com.abcd.pemantauankesehatananak.data.database.api.ApiService
import com.abcd.pemantauankesehatananak.data.model.ResponseModel
import com.abcd.pemantauankesehatananak.data.model.UserModel
import com.abcd.pemantauankesehatananak.databinding.ActivityLupaPasswordBinding
import com.abcd.pemantauankesehatananak.utils.network.UIState
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat

@AndroidEntryPoint
class LupaPasswordActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLupaPasswordBinding
    private val viewModel: LupaPasswordViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLupaPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setButton()
        getLupaPassword()
        getKirimUsernamePassword()
    }
    private fun setButton() {
        binding.apply {
            btnKirim.setOnClickListener {
                if(etEmail.text.toString().isEmpty()){
                    etEmail.error = "Tidak boleh kosong"
                } else{
                    hitungMundur()
                    fetchLupaPassword(etEmail.text.toString().trim())
                }
            }
        }
    }

    private fun fetchLupaPassword(email: String){
        viewModel.fetchLupaPassword(email)
    }

    private fun getLupaPassword(){
        viewModel.getLupaPassword().observe(this@LupaPasswordActivity){result->
            when(result){
                is UIState.Loading-> {}
                is UIState.Success-> setSuccessLupaPassword(result.data)
                is UIState.Failure-> setFailureLupaPassword(result.message)
            }
        }
    }

    private fun setSuccessLupaPassword(data: UserModel) {
        data.apply {
            try {
                postKirimUsernamePassword(
                    email!!, username!!, password!!
                )
            } catch (ex: Exception){
                Toast.makeText(this@LupaPasswordActivity, ex.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setFailureLupaPassword(message: String) {
        Toast.makeText(this@LupaPasswordActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun postKirimUsernamePassword(email: String, username: String, password: String) {
        viewModel.postKirimUsernamePassword(email, username, password)
    }

    private fun getKirimUsernamePassword(){
        viewModel.getKirimUsernamePassword().observe(this@LupaPasswordActivity){result->
            when(result){
                is UIState.Loading-> {}
                is UIState.Success-> setSuccessKirimUsernamePassword(result.data)
                is UIState.Failure-> setFailureKirimUsernamePassword(result.message)
            }
        }
    }

    private fun setSuccessKirimUsernamePassword(data: ResponseModel) {
        if(data.status == "0"){
            Toast.makeText(this@LupaPasswordActivity, "Berhasil Kirim response", Toast.LENGTH_SHORT).show()
        } else{
            Toast.makeText(this@LupaPasswordActivity, data.message_response, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setFailureKirimUsernamePassword(message: String) {
        Toast.makeText(this@LupaPasswordActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun hitungMundur(){
        binding.btnKirim.isEnabled = false
        val countDown = object : CountDownTimer(60000, 1000){
            override fun onTick(p0: Long) {
                val numberFormat = DecimalFormat("00")

                val number = numberFormat.format(p0 / 1000 % 60)
                binding.btnKirim.text = number.toString()
            }

            override fun onFinish() {
                binding.btnKirim.text = "KIRIM"
                binding.btnKirim.isEnabled = true
            }

        }

        countDown.start()
    }
}