package com.example.einkaufsbegleiter.fragments

import androidx.appcompat.app.AppCompatActivity
import com.example.einkaufsbegleiter.R

// Diese Objektklasse verwaltet Fragmente in meiner App.
object FragmentManager {
    var currentFrag: BaseFragment? = null

    fun setFragment(newFrag: BaseFragment, activity: AppCompatActivity) {
        val transaction = activity.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.placeHolder, newFrag)
        transaction.commit()
        currentFrag = newFrag
    }
}