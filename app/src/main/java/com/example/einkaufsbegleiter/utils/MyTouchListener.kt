package com.example.einkaufsbegleiter.utils

import android.view.MotionEvent
import android.view.View

// Diese Klasse implementiert den `View.OnTouchListener` und ermöglicht das Bewegen einer View per Touch-Geste.
class MyTouchListener: View.OnTouchListener {
    var xDelta = 0.0f
    var yDelta = 0.0f

    // Diese Funktion wird aufgerufen, wenn eine Touch-Geste auf der View erkannt wird.
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        when(event?.action) {
            MotionEvent.ACTION_DOWN -> {
                if (v != null) {
                    xDelta = v.x - event.rawX
                }
                if (v != null) {
                    yDelta = v.y - event.rawY
                }
            }
            MotionEvent.ACTION_MOVE -> {
                if (v != null) {
                    v.x = xDelta + event.rawX
                }
                if (v != null) {
                    v.y = yDelta + event.rawY
                }
            }
        }
        return true
    }
}