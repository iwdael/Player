package com.hacknife.player.touch

import android.view.MotionEvent
import android.view.ViewGroup
import android.view.Window
import android.widget.FrameLayout
import com.hacknife.player.IPlayer
import com.hacknife.player.callback.UiCallback
import com.hacknife.player.compat.getActivity
import kotlin.math.roundToInt

/**
 * author : 段泽全(hacknife)
 * e-mail : hacknife@outlook.com
 * time   : 2019/8/5
 * desc   : MVVM
 * version: 1.0
 */
class TouchMove(private val uiCallback: UiCallback) : TouchGesture {
    private val contentView =
        uiCallback.context().getActivity()?.findViewById<ViewGroup>(Window.ID_ANDROID_CONTENT)
    private val params = uiCallback.layoutParams()
    private var moveX = 0f
    private var moveY = 0f
    override fun onTouch(e: MotionEvent, width: Int, height: Int) {
        val x = e.rawX
        val y = e.rawY
        when (e.action) {
            MotionEvent.ACTION_DOWN -> {
                moveX = x
                moveY = y
            }
            MotionEvent.ACTION_MOVE -> {
                if (contentView == null) return
                val movedX = x - moveX
                val movedY = y - moveY
                params.rightMargin = (params.rightMargin - movedX).roundToInt()
                params.topMargin = (params.topMargin + movedY).roundToInt()
                contentView.updateViewLayout(uiCallback.view(), params)
                moveX = x
                moveY = y
            }
        }
    }
}