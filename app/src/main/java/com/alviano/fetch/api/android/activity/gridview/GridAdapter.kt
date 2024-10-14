package com.alviano.fetch.api.android.activity.gridview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.alviano.fetch.api.android.R

class GridAdapter: BaseAdapter() {

    private lateinit var context: Context
    private lateinit var imgProduct: Array<Int>
    private lateinit var productTitle: Array<String>
    private lateinit var inflater: LayoutInflater

    fun girdAdapter(context: Context, imgProduct: Array<Int>, productTitle: Array<String>) {
        this.context = context
        this.imgProduct = imgProduct
        this.productTitle = productTitle
        this.inflater = LayoutInflater.from(context) // Inisialisasi inflater
    }

    override fun getCount(): Int {
        return productTitle.size
    }

    override fun getItem(p0: Int): Any? {
        return null
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        // Gunakan var view untuk menampung convertView atau inflate jika null
        val view = convertView ?: inflater.inflate(R.layout.grid_viewholder, parent, false)

        // Gunakan findViewById secara aman tanpa !! untuk menghindari NullPointerException
        val imageView: ImageView = view.findViewById(R.id.imageTxt)
        val textView: TextView = view.findViewById(R.id.titleTxt)

        // Atur data ke imageView dan textView
        imageView.setImageResource(imgProduct[position])
        textView.text = productTitle[position]

        // Kembalikan view yang tidak null
        return view
    }

}