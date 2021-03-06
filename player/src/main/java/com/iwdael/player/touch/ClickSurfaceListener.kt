package com.iwdael.player.touch

import android.view.View
import com.iwdael.player.*
import com.iwdael.player.callback.UiCallback
import com.iwdael.player.compat.delay
import com.iwdael.player.compat.setSeek
import com.iwdael.player.compat.setText
import com.iwdael.player.compat.toTime

/**
 * author : Iwdael
 * e-mail : iwdael@outlook.com
 * time   : 2019/8/5
 * desc   : MVVM
 * version: 1.0
 */
class ClickSurfaceListener(private val uiCallback: UiCallback) : ClickPlayerListener {
    companion object {
        private const val DURATION_SEEK_BAR = 1000L
        private const val DURATION_TOGGLE = 5000L
    }

    private val intervalTimer = IntervalTimer()
    private var togglePlaying = false
    private var togglePause = false

    override fun onClick(it: View) {
        if (!uiCallback.isUsed()) return
        if (uiCallback.state() == State.PLAYER_STATE_PLAYING) {
            if (!togglePlaying) {
                uiCallback.onDialogSettingDismiss()
                uiCallback.onStateChangeToPlayingToggle()
                intervalTimer.interval(DURATION_SEEK_BAR) {
                    uiCallback.view().setText(R.id.tvCurrent, uiCallback.getCurDuration().toTime())
                    uiCallback.view().setText(R.id.tvTotal, uiCallback.getTotalDuration().toTime())
                    uiCallback.view().setSeek(R.id.sbProgress, uiCallback.getCurDuration().toInt())
                }
                togglePlaying = true
                it.delay(DURATION_TOGGLE) {
                    if (togglePlaying && uiCallback.state() == State.PLAYER_STATE_PLAYING) {
                        uiCallback.onStateChangeToPlaying()
                        intervalTimer.clear()
                        togglePlaying = false
                    }
                }
            } else {
                uiCallback.onStateChangeToPlaying()
                intervalTimer.clear()
                togglePlaying = false
            }
        }
        if (uiCallback.state() == State.PLAYER_STATE_PAUSE) {
            if (!togglePause) {
                uiCallback.onDialogSettingDismiss()
                togglePause = true
                uiCallback.onStateChangeToPauseToggle()
                intervalTimer.interval(DURATION_SEEK_BAR) {
                    uiCallback.view().setText(R.id.tvCurrent, uiCallback.getCurDuration().toTime())
                    uiCallback.view().setText(R.id.tvTotal, uiCallback.getTotalDuration().toTime())
                    uiCallback.view().setSeek(R.id.sbProgress, uiCallback.getCurDuration().toInt())
                }
                it.delay(DURATION_TOGGLE) {
                    if (togglePause && uiCallback.state() == State.PLAYER_STATE_PAUSE) {
                        uiCallback.onStateChangeToPause()
                        intervalTimer.clear()
                        togglePause = false
                    }
                }
            } else {
                uiCallback.onStateChangeToPause()
                intervalTimer.clear()
                togglePause = false
            }
        }
    }

    override fun clear() {
        togglePlaying = false
        togglePause = false
    }
}