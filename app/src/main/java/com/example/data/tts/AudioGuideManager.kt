package com.example.data.tts

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale

class AudioGuideManager(context: Context) {
    private var tts: TextToSpeech? = null
    private var isInitialized = false

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _currentlySpeakingStopId = MutableStateFlow<String?>(null)
    val currentlySpeakingStopId: StateFlow<String?> = _currentlySpeakingStopId.asStateFlow()

    init {
        tts = TextToSpeech(context.applicationContext) { status ->
            if (status == TextToSpeech.SUCCESS) {
                val result = tts?.setLanguage(Locale("tr", "TR"))
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    // Fallback to default locale
                    tts?.language = Locale.getDefault()
                }
                tts?.setSpeechRate(0.95f)
                tts?.setPitch(1.0f)
                isInitialized = true
            }
        }

        tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {
                _isPlaying.value = true
            }

            override fun onDone(utteranceId: String?) {
                _isPlaying.value = false
                _currentlySpeakingStopId.value = null
            }

            override fun onError(utteranceId: String?) {
                _isPlaying.value = false
                _currentlySpeakingStopId.value = null
            }
        })
    }

    fun speak(stopId: String, text: String) {
        if (!isInitialized || tts == null) return

        if (_currentlySpeakingStopId.value == stopId && _isPlaying.value) {
            stop()
            return
        }

        _currentlySpeakingStopId.value = stopId
        _isPlaying.value = true
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, stopId)
    }

    fun stop() {
        tts?.stop()
        _isPlaying.value = false
        _currentlySpeakingStopId.value = null
    }

    fun shutdown() {
        tts?.stop()
        tts?.shutdown()
        tts = null
    }
}
