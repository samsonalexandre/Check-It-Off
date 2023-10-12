package com.example.einkaufsbegleiter.utils

import android.text.Html
import android.text.Spanned

// Diese Klasse bietet Funktionen zum Konvertieren von HTML-Text in Android-`Spanned`-Text und umgekehrt.
object HtmlManager {
    // Diese Funktion konvertiert HTML-Text in einen Android-`Spanned`-Text.
    fun getFromHtml(text: String): Spanned {
        return if (android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.N) {
            Html.fromHtml(text)
        } else {
            Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT)
        }
    }

    // Diese Funktion konvertiert Android-`Spanned`-Text in HTML-Text.
    fun toHtml(text: Spanned): String {
        return if (android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.N) {
            Html.toHtml(text)
        } else {
            Html.toHtml(text, Html.FROM_HTML_MODE_COMPACT)
        }
    }
}