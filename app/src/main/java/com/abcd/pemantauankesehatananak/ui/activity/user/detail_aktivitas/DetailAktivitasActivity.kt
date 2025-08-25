package com.abcd.pemantauankesehatananak.ui.activity.user.detail_aktivitas

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.abcd.pemantauankesehatananak.adapter.RiwayatAktivitasAdapter
import com.abcd.pemantauankesehatananak.data.model.AktivitasModel
import com.abcd.pemantauankesehatananak.data.model.ResponseModel
import com.abcd.pemantauankesehatananak.data.model.RiwayatAktivitasModel
import com.abcd.pemantauankesehatananak.data.model.YoutubeResultModel
import com.abcd.pemantauankesehatananak.databinding.ActivityDetailAktivitasBinding
import com.abcd.pemantauankesehatananak.utils.SharedPreferencesLogin
import com.abcd.pemantauankesehatananak.utils.network.UIState
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.ArrayList

@Suppress("DEPRECATION")
@AndroidEntryPoint
class DetailAktivitasActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailAktivitasBinding
    private val viewModel: DetailAktivitasViewModel by viewModels()
    private lateinit var aktivitas: AktivitasModel
    private lateinit var sharedPreferences: SharedPreferencesLogin
    private var idAktivitas: Int = 0
    private var idYoutube: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailAktivitasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = SharedPreferencesLogin(this@DetailAktivitasActivity)
        setDataSebelumnya()
        setButton()
        fetchRiwayatAktivitas()
        getRiwayatAktivitas()
        getYoutubeResult()
        getUpdateSelesai()
    }

    private fun setButton() {
        binding.apply {
            btnTandai.setOnClickListener {
                postUpdateSelesai()
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
            idYoutube = searchIdUrlVideo(aktivitas.video_url!!)
            idAktivitas = aktivitas.id_aktivitas!!
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
                tvKategori.text = "Kategori : "+aktivitas.kategori!!.kategori
                tvUsia.text = "Usia Minimal : ${aktivitas.usia_minimal} Bulan"
                tvDeskripsi.text = aktivitas.deskripsi
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

        binding.youtubePlayerView.settings.javaScriptEnabled = true
        binding.youtubePlayerView.loadData(
            "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/$videoId\" frameborder=\"0\" allowfullscreen></iframe>",
            "text/html",
            "utf-8"
        )
        
//        lifecycle.addObserver(binding.youtubePlayerView)
//
//        binding.youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
//            override fun onReady(youTubePlayer: YouTubePlayer) {
//                if (videoId != "") {
//                    youTubePlayer.loadVideo(videoId, 0f)
//                    Log.d("DetailTAG", "onReady: $videoId")
//                } else {
//                    Toast.makeText(this@DetailAktivitasActivity, "Video ID tidak valid", Toast.LENGTH_SHORT).show()
//                    // fallback buka di YouTube app
//                    openYoutubeVideo(this@DetailAktivitasActivity, videoId)
//                }
//            }
//        })
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

    private fun fetchRiwayatAktivitas() {
        viewModel.loadData(sharedPreferences.getIdUser(), idAktivitas, idYoutube)
    }

    private fun getRiwayatAktivitas() {
        viewModel.getRiwayatAktivitas.observe(this@DetailAktivitasActivity){result->
            when(result){
                is UIState.Loading-> {}
                is UIState.Success-> setSuccessFetchRiwyatAktivitas(result.data)
                is UIState.Failure-> setFailureFetchRiwyatAktivitas(result.message)
            }
        }
    }

    private fun setSuccessFetchRiwyatAktivitas(data: ArrayList<RiwayatAktivitasModel>) {
        if(data.isNotEmpty()){
            setAdapterRiwayatAktivitas(data)
        } else{
            binding.tvTitleRiwayatAktivitas.visibility = View.GONE

            Toast.makeText(this@DetailAktivitasActivity, "Tidak ada Riwayat aktivitas", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setAdapterRiwayatAktivitas(data: ArrayList<RiwayatAktivitasModel>) {
        val adapterRiwayatAktivitas = RiwayatAktivitasAdapter(data)
        binding.rvRiwayatAktivitas.apply {
            layoutManager = LinearLayoutManager(this@DetailAktivitasActivity, LinearLayoutManager.VERTICAL, false)
            adapter = adapterRiwayatAktivitas
        }
    }

    private fun setFailureFetchRiwyatAktivitas(message: String) {
        Toast.makeText(this@DetailAktivitasActivity, message, Toast.LENGTH_SHORT).show()
        binding.tvTitleRiwayatAktivitas.visibility = View.GONE
    }

    private fun getYoutubeResult() {
        viewModel.getYoutubeResult.observe(this@DetailAktivitasActivity){result->
            when(result){
                is UIState.Loading-> {}
                is UIState.Success-> setSuccessFetchYoutubResult(result.data)
                is UIState.Failure-> setFailureFetchYoutubResult(result.message)
            }
        }
    }

    private fun setSuccessFetchYoutubResult(data: YoutubeResultModel) {
        if(data.items!!.isNotEmpty()){
            binding.apply {
                tvYoutubeChannelTitle.text = data.items!![0].snippet?.channelTitle
                tvYoutubeTitle.text = data.items!![0].snippet?.title

                tvYoutubeChannelTitle.setOnClickListener {
                    openYoutubeChannel(this@DetailAktivitasActivity, data.items!![0].snippet?.channelId!!)
                }
                tvYoutubeTitle.setOnClickListener{
                    openYoutubeVideo(this@DetailAktivitasActivity, data.items!![0].id!!)
                }
            }
        } else{
            Toast.makeText(this@DetailAktivitasActivity, "Tidak di dapat referensi", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setFailureFetchYoutubResult(message: String) {
        Toast.makeText(this@DetailAktivitasActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun postUpdateSelesai() {
        viewModel.postUpdateSelesai(sharedPreferences.getIdUser(), idAktivitas)
    }

    private fun getUpdateSelesai() {
        viewModel.getUpdateSelesai.observe(this@DetailAktivitasActivity){result->
            when(result){
                is UIState.Loading-> {}
                is UIState.Success-> setSuccessPostUpdateSelesai(result.data)
                is UIState.Failure-> setFailurePostUpdateSelesai(result.message)
            }
        }
    }

    private fun setSuccessPostUpdateSelesai(data: ResponseModel) {
        if(data.status == "0"){
            Toast.makeText(this@DetailAktivitasActivity, "Ditandai selesai", Toast.LENGTH_SHORT).show()
            fetchRiwayatAktivitas()
        } else{
            Toast.makeText(this@DetailAktivitasActivity, data.message_response, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setFailurePostUpdateSelesai(message: String) {
        Toast.makeText(this@DetailAktivitasActivity, message, Toast.LENGTH_SHORT).show()
    }

    fun openYoutubeChannel(context: Context, channelId: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/$channelId"))
        intent.setPackage("com.google.android.youtube") // coba buka langsung di YouTube app
        try {
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            // fallback buka browser kalau YouTube app tidak ada
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/$channelId")))
        }
    }

    fun openYoutubeVideo(context: Context, videoId: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=$videoId"))
        intent.setPackage("com.google.android.youtube") // coba buka di aplikasi YouTube
        try {
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            // fallback ke browser kalau YouTube app tidak ada
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=$videoId")))
        }
    }
}