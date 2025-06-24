package com.abcd.pemantauankesehatananak.ui.activity.user.detail_aktivitas

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.abcd.pemantauankesehatananak.data.model.AktivitasModel
import com.abcd.pemantauankesehatananak.databinding.ActivityDetailAktivitasBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
@AndroidEntryPoint
class DetailAktivitasActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailAktivitasBinding
    private lateinit var aktivitas: AktivitasModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailAktivitasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setDataSebelumnya()
        setButton()
    }

    private fun setButton() {
        binding.apply {
            btnTandai.setOnClickListener {
                Toast.makeText(this@DetailAktivitasActivity, "Ditandai selesai", Toast.LENGTH_SHORT).show()
                // Kirim ke server atau simpan lokal
            }
            myAppBar.ivBack.setOnClickListener {

                finish()
            }
        }
    }

    private fun setDataSebelumnya() {
        val i = intent
        if(i!=null){
            aktivitas = i.getParcelableExtra("aktivitas")!!
            loadDetailAktivitas(aktivitas)
            setYoutubVideo(aktivitas.video_url!!)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun loadDetailAktivitas(aktivitas: AktivitasModel) {
        lifecycleScope.launch {
            binding.apply {
                setTopAppBar()
                tvJudul.text = aktivitas.judul
                tvUsia.text = "Usia Minimal : ${aktivitas.usia_minimal} Bulan"
                tvDeskripsi.text = aktivitas.deskripsi
                tvKategori.text = aktivitas.kategori!!.kategori
            }
        }
    }

    private fun setTopAppBar() {
        binding.myAppBar.apply {
            ivNavDrawer.visibility = View.GONE
            ivBack.visibility = View.VISIBLE

            tvTitle.text = aktivitas.judul
        }
    }

    private fun setYoutubVideo(videoUrl: String) {
        val videoId = searchIdUrlVideo(videoUrl)
        lifecycle.addObserver(binding.youtubePlayerView)

        binding.youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(videoId, 0f)
            }
        })
    }

    private fun searchIdUrlVideo(urlVideo: String): String {
        var url = ""
        try {
            val arrayUrlIdVideo = urlVideo.split("v=")
            url = arrayUrlIdVideo[1]

            try {
                val arraySearchUrlSymbol = url.split("&")
                url = arraySearchUrlSymbol[0]
            } catch (_: Exception){
                url = arrayUrlIdVideo[1]
            }
        } catch (ex: Exception){
            try {
                val arrayUrlIdVideo = urlVideo.split("si=")
                url = arrayUrlIdVideo[1]
            } catch (ex: Exception){
                url = "0"
            }
        }
        return url
    }
}