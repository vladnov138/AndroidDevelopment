package com.example.cryptoandroid

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import java.math.BigDecimal

class RateCheckService : Service() {
    companion object {
        const val TAG = "RateCheckService"
        const val RATE_CHECK_INTERVAL = 5000L
        const val RATE_CHECK_ATTEMPTS_MAX = 100

        const val ARG_START_RATE = "ARG_START_RATE"
        const val ARG_TARGET_RATE = "ARG_TARGET_RATE"
        fun startService(context: Context, startRate: String, targetRate: String) {
            context.startService(Intent(context, RateCheckService::class.java).apply {
                putExtra(ARG_START_RATE, startRate)
                putExtra(ARG_TARGET_RATE, targetRate)
            })
        }

        fun stopService(context: Context) {
            context.stopService(Intent(context, RateCheckService::class.java))
        }
    }

    val handler = Handler(Looper.getMainLooper())
    var rateCheckAttempt = 0
    lateinit var startRate: BigDecimal
    lateinit var targetRate: BigDecimal
    val rateCheckInteractor = RateCheckInteractor()

    val rateCheckRunnable: Runnable = Runnable {
        // Write your code here. Check number of attempts and stop service if needed
        rateCheckAttempt++
        if (rateCheckAttempt >= RATE_CHECK_ATTEMPTS_MAX) {
            Log.d(TAG, "Rate check attempts limit reached")
            stopSelf()
        } else {
            requestAndCheckRate()
            Log.d(TAG, "Rate check attempt = $rateCheckAttempt")
        }
    }

    private fun requestAndCheckRate() {
        val coroutineScope = CoroutineScope(Dispatchers.IO)


        val currentRate = runBlocking {
            rateCheckInteractor.requestRate()
        }
        if (currentRate.isNotEmpty()) {
            val currentRateBigDecimal = BigDecimal(currentRate)
            Log.d(TAG, "Current rate = $currentRateBigDecimal")
            if (currentRateBigDecimal >= targetRate) {
                Log.d(TAG, "Target rate reached")
                handler.removeCallbacks(rateCheckRunnable)
                stopSelf()
                sendNotification("Target rate reached!")
                return
            } else if (currentRateBigDecimal > startRate) {
                Log.d(TAG, "Rate is increasing")
            }
        }
        handler.postDelayed(rateCheckRunnable, RATE_CHECK_INTERVAL)
    }

    override fun onBind(p0: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startRate = BigDecimal(intent?.getStringExtra(ARG_START_RATE))
        targetRate = BigDecimal(intent?.getStringExtra(ARG_TARGET_RATE))

        Log.d(TAG, "onStartCommand startRate = $startRate targetRate = $targetRate")

        handler.post(rateCheckRunnable)
        return Service.START_STICKY
    }

    private lateinit var notificationManager: NotificationManager

    override fun onCreate() {
        super.onCreate()
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Rate Notifications"
            val descriptionText = "Notifications for rate updates"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("rate_updates", name, importance).apply {
                description = descriptionText
            }
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun sendNotification(message: String) {
        val notification = NotificationCompat.Builder(this, "rate_updates")
            .setContentTitle("Rate Update")
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()

        notificationManager.notify(1, notification)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(rateCheckRunnable)
    }
}






