package com.example.broadcastreceiver

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.broadcastreceiver.databinding.ActivityProgressBarBinding
import java.util.concurrent.TimeUnit

class ProgressBarActivity : AppCompatActivity() {
    private var _binding: ActivityProgressBarBinding? = null
    private val binding get() = _binding!!

    private lateinit var handler: Handler
    private val UPDATE_PROGRESS_INTERVAL: Long = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityProgressBarBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        handler = Handler()
        handler.postDelayed(updateProgressTask, UPDATE_PROGRESS_INTERVAL)

        val m = MediaPlayerManager.getMediaPlayer(assets.openFd("creep.mp3"))


        binding.mainActivityBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.playStopBtn2.setOnClickListener {
            if (m.isPlaying) {
                m.pause()
                binding.playStopBtn2.text = "Play"
            } else {
                m.start()
                binding.playStopBtn2.text = "Stop"
            }
        }
    }

    private val updateProgressTask = object: Runnable {
        override fun run() {
            val currentPosition = MediaPlayerManager.getCurrentPosition()

            binding.progressBar.progress = currentPosition
            binding.progressBar.max = MediaPlayerManager.getDuration()

            createTime(binding.progressTv, currentPosition, MediaPlayerManager.getDuration())

            handler.postDelayed(this, UPDATE_PROGRESS_INTERVAL)
        }
    }

    private fun createTime(textView: TextView, time: Int, duration: Int){
        textView.text = String.format(
            "%02d:%02d/%02d:%02d",
            TimeUnit.MILLISECONDS.toMinutes(time.toLong()),
            TimeUnit.MILLISECONDS.toSeconds(time.toLong()) - TimeUnit.MINUTES.toSeconds(
                TimeUnit.MILLISECONDS.toMinutes(time.toLong())
            ),
            TimeUnit.MILLISECONDS.toMinutes(duration.toLong()),
            TimeUnit.MILLISECONDS.toSeconds(duration.toLong()) - TimeUnit.MINUTES.toSeconds(
                TimeUnit.MILLISECONDS.toMinutes(duration.toLong())
            )
        )
    }
}