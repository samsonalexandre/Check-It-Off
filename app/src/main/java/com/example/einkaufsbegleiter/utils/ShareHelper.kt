package com.example.einkaufsbegleiter.utils

import android.content.Intent
import com.example.einkaufsbegleiter.entities.ShopListItem
import java.lang.StringBuilder

object ShareHelper {
    fun shareShopList(shopList: List<ShopListItem>, listName: String): Intent {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plane"
        intent.apply {
            putExtra(Intent.EXTRA_TEXT, makeShareText(shopList, listName))
        }
        return intent
    }

    private fun makeShareText(shopList: List<ShopListItem>, listName: String): String {
        val sBilder = StringBuilder()
        sBilder.append("<<$listName>>")
        sBilder.append("\n")
        var counter = 0
        shopList.forEach {
            sBilder.append("${++counter} - ${it.name} (${it.itemInfo})")
            sBilder.append("\n")
        }
        return sBilder.toString()
    }
}