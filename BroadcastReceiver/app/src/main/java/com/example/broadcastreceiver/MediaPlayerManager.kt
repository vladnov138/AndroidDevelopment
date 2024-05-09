package com.example.broadcastreceiver

import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer

object MediaPlayerManager {
    private var mediaPlayer: MediaPlayer? = null

    fun getMediaPlayer(music: AssetFileDescriptor): MediaPlayer {
        if (mediaPlayer == null) {

            mediaPlayer = MediaPlayer()
            mediaPlayer?.setDataSource(
                music.fileDescriptor,
                music.startOffset,
                music.length
            )
            music.close()
            mediaPlayer?.prepare()
        }
        return mediaPlayer!!
    }

    fun getCurrentPosition(): Int {
        return mediaPlayer?.currentPosition ?: 0
    }

    fun getDuration(): Int {
        return mediaPlayer?.duration ?: 0
    }
    fun releaseMediaPlayer() {
        mediaPlayer?.release()
        mediaPlayer = null
    }
}