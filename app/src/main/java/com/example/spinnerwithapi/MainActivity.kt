package com.example.spinnerwithapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.spinnerwithapi.viewmodel.ShopViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() , DialogSpinner.NewItemDialogListener {

    private lateinit var shopViewModel: ShopViewModel
    //    private var spinner : Spinner? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        shopViewModel = ViewModelProvider(this).get(ShopViewModel::class.java)

        add_item_btn.setOnClickListener{
            addNewShop()
        }



    }

    private fun addNewShop(){
        val newFragment = DialogSpinner.newInstance(R.string.daily,null)
        newFragment.show(supportFragmentManager,"newItem")
    }

    override fun onDialogPositiveClick(
        dialog: DialogFragment,
        shop_name: String,
        isEdit: Boolean,
        positive: Int?
    ) {
        if (!isEdit){
            Log.d("diaName",shop_name)
            shopViewModel.setName(shop_name)
        }
        var txtname = findViewById<TextView>(R.id.txtshopName)
        shopViewModel.getName().observe(this, Observer {
            txtname.text = it
        })
    }

    override fun onDialogNegativeClick(dialog: DialogFragment) {
        Snackbar.make(add_item_btn,"Nothing added", Snackbar.LENGTH_LONG
        ).setAction("Action",null).show()    }


}