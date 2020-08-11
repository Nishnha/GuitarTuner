package me.nishnha.guitartuner

import android.app.Activity
import android.content.Context
import android.view.View
import android.os.Bundle
import android.os.Vibrator
import android.os.VibrationEffect
import android.widget.Toast
import net.mabboud.android_tone_player.ContinuousBuzzer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {

    private val STRING_HIGH_E_FREQUENCY: Double = 329.63
    private val STRING_B_FREQUENCY: Double = 246.94
    private val STRING_G_FREQUENCY: Double = 196.00
    private val STRING_D_FREQUENCY: Double = 146.83
    private val STRING_A_FREQUENCY: Double = 110.00
    private val STRING_LOW_E_FREQUENCY: Double = 82.41

    private lateinit var vibrator: Vibrator
    private var tonePlayer = ContinuousBuzzer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        if (!vibrator.hasVibrator()) {
            val denyText = "Your device does not have a vibrator. This app will not work"
            Toast.makeText(getApplicationContext(), denyText, Toast.LENGTH_LONG)
        }
    }

    override fun onPause() {
        super.onPause()
        stop()
    }

    fun generateFrequencyTimings(frequency: Double): (Int) -> Long {
        return { _: Int -> frequency.toLong() }
    }

    fun vibrateAtFrequency(f: Double) {
        val timings = LongArray(1000, generateFrequencyTimings(f))
        val effect: VibrationEffect = VibrationEffect.createWaveform(timings, -1)
        vibrator.vibrate(effect)
    }

    fun playToneAtFrequency(f: Double) {
        tonePlayer.setVolume(100);
        tonePlayer.setToneFreqInHz(f);
        tonePlayer.play()
    }

    fun startPlayback(f: Double) {
        stop()
        playToneAtFrequency(f)
        vibrateAtFrequency(f)
    }

    private fun stop() {
        vibrator.cancel()
        tonePlayer.stop()
    }

    fun getButtonPressed(v: View) {
        when (v) {
            high_e_stringButton -> startPlayback(STRING_HIGH_E_FREQUENCY)
            b_stringButton -> startPlayback(STRING_B_FREQUENCY)
            g_stringButton -> startPlayback(STRING_G_FREQUENCY)
            d_stringButton -> startPlayback(STRING_D_FREQUENCY)
            a_stringButton -> startPlayback(STRING_A_FREQUENCY)
            low_e_stringButton -> startPlayback(STRING_LOW_E_FREQUENCY)
            stopButton -> stop()
        }
    }

}
