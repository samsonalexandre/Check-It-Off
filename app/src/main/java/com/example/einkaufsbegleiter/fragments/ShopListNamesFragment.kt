package com.example.einkaufsbegleiter.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import com.example.einkaufsbegleiter.activities.MainApp
import com.example.einkaufsbegleiter.databinding.FragmentShopListNamesBinding
import com.example.einkaufsbegleiter.db.MainViewModel
import com.example.einkaufsbegleiter.dialogs.NewListDialog
import com.example.einkaufsbegleiter.entities.ShopListNameItem
import com.example.einkaufsbegleiter.utils.TimeManager


class ShopListNamesFragment : BaseFragment() {
    private lateinit var binding: FragmentShopListNamesBinding


    private val mainViewModel: MainViewModel by activityViewModels {
        MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database)
    }


    override fun onClickNew() {
        NewListDialog.showDialog(activity as AppCompatActivity, object : NewListDialog.Listener {
            override fun onClick(name: String) {
                Log.d("MyLog", "Name: $name")
                val shopListName = ShopListNameItem(
                    null,
                    name,
                    TimeManager.getCurrentTime(),
                    0,
                    0,
                    ""
                )
                mainViewModel.insertShopListName(shopListName)
            }

        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShopListNamesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
        observer()
    }


    private fun initRcView() = with(binding) {

    }

    private fun observer() {
        mainViewModel.allShopListNamesItem.observe(viewLifecycleOwner) {

        }
    }


    companion object {
        @JvmStatic
        fun newInstance() = ShopListNamesFragment()

    }
}