package com.example.einkaufsbegleiter.fragments

import androidx.fragment.app.Fragment

// Diese abstrakte Klasse ist die Basisklasse für alle Fragmente in meiner App.
abstract class BaseFragment: Fragment() {
    abstract fun onClickNew()
}