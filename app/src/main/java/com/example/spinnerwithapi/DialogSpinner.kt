package com.example.spinnerwithapi

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.customnavigationdrawer.model.Shop
import com.example.spinnerwithapi.viewmodel.ShopViewModel
import kotlinx.android.synthetic.main.activity_main.*

class DialogSpinner : DialogFragment() {
    interface NewItemDialogListener {
        fun onDialogPositiveClick(
            dialog: DialogFragment,
            shop_name: String,
            isEdit: Boolean,
            positive: Int?

        )

        fun onDialogNegativeClick(
            dialog: DialogFragment
        )

    }

    private var newItemDialogListener: NewItemDialogListener? = null
    private var isEdit = false
    private lateinit var shopViewModel: ShopViewModel
    private var spinner : Spinner? = null
    var selectShop = ""

    companion object {
        fun newInstance(title: Int, position: Int?): DialogSpinner {
            var dialogSpinner = DialogSpinner()
            var args = Bundle()
            args.putInt("dialog_title", title)
            if (position != null) {
                args.putInt("item_position", position)
            } else {
                args.putInt("item_position", -1)
            }
            dialogSpinner.arguments = args
            return dialogSpinner
        }
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        activity?.let {
            shopViewModel = ViewModelProvider(it).get(ShopViewModel::class.java)

        }
        var title = arguments?.getInt("dialog_title")
        val position = arguments?.getInt("item_position", -1)

        val builder = AlertDialog.Builder(activity)
        if (title != null) {
            builder.setTitle(title)
        }

        newItemDialogListener = activity as NewItemDialogListener

        val dialogView = activity?.layoutInflater?.inflate(R.layout.dialog_item, null)
        spinner= dialogView?.findViewById(R.id.spinner)
        shopViewModel.setShopData()
        shopViewModel.getShopData().observe(this, Observer {
            val responseList = it.shops
            val item = arrayOfNulls<String>(responseList.size)
            for (i in 0 until responseList.size) {

                item[i] = responseList.get(i).shop_name

            }

            val arrayAdapter =
                ArrayAdapter(activity!!, android.R.layout.simple_spinner_dropdown_item, item)
            Log.d("ShopArray>>", arrayAdapter.toString())

            spinner?.adapter = arrayAdapter

            spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    positionItem: Int,
                    id: Long
                ) {
                    selectShop = parent?.adapter?.getItem(positionItem).toString()


                    Toast.makeText(activity,selectShop,Toast.LENGTH_LONG).show()

                }

            }
        })
        if (position != -1) {

        }

        builder.setView(dialogView).setPositiveButton("Save") { _, _ ->



            newItemDialogListener?.onDialogPositiveClick(this, selectShop, isEdit, position)


        }.setNegativeButton(android.R.string.cancel) { _, _ ->
            newItemDialogListener?.onDialogNegativeClick(this)

        }
        return builder.create()


    }

}