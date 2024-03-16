package com.example.services

import android.app.IntentService
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import kotlin.concurrent.thread
import kotlin.random.Random
import kotlin.time.Duration.Companion.seconds

class TimeService : Service() {
    companion object {
        private val TAG = TimeService::class.simpleName
    }

    override fun onCreate() {
        super.onCreate()
        Log.e(TAG, "OnCreate")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(TAG, "OnDestroy")
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.e(TAG, "OnUnbind")
        return super.onUnbind(intent)
    }

    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
        Log.e(TAG, "OnRebind")
    }

    override fun onBind(intent: Intent?): IBinder {
        Log.e(TAG, "OnBind")
        return TimeServiceBinder()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e(TAG, "OnStartCommand(intent=$intent)")
        return super.onStartCommand(intent, flags, startId)
    }

    fun getTime(): LocalDateTime {
        return LocalDateTime.now()
    }

    inner class TimeServiceBinder : Binder() {
        private val flow = MutableSharedFlow<Boolean>(
            replay = 1, // Сколько повторится
            onBufferOverflow = BufferOverflow.DROP_OLDEST // Если переполнится место
        )

        fun getTime(): LocalDateTime {
            return this@TimeService.getTime()
        }

        fun sendMessage() {
//            GlobalScope.launch {
//                delay(2.seconds)
//                flow.emit(Random.nextBoolean())
//            }
            thread {
                Thread.sleep(2)
                flow.tryEmit(Random.nextBoolean())
            }
        }

        fun getMessageSendResults(): Flow<Boolean> {
            return flow.asSharedFlow()
        }
    }
}