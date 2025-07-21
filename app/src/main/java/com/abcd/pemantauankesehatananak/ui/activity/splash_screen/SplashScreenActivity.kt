package com.abcd.pemantauankesehatananak.ui.activity.splash_screen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.abcd.pemantauankesehatananak.R
import com.abcd.pemantauankesehatananak.ui.activity.login.LoginActivity
import com.abcd.pemantauankesehatananak.ui.activity.user.main.MainActivity
import com.abcd.pemantauankesehatananak.utils.SharedPreferencesLogin

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val sharedPreferencesLogin = SharedPreferencesLogin(this@SplashScreenActivity)

        Handler(Looper.getMainLooper()).postDelayed({
            if(sharedPreferencesLogin.getIdUser().toString() != ""){
                if(sharedPreferencesLogin.getSebagai() == "user"){
                    // To User
                    startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
                    finish()
                } else{
                    startActivity(Intent(this@SplashScreenActivity, LoginActivity::class.java))
                    finish()
                }
            }
            else{
                startActivity(Intent(this@SplashScreenActivity, LoginActivity::class.java))
                finish()
            }
        }, 3000)
    }
}