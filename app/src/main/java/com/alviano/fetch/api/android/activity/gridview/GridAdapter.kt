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

        var view = convertView

        if (inflater == null) {
            inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
        if (view == null) {
            view = inflater.inflate(R.layout.grid_viewholder, null)
        }

        val imageView: ImageView = convertView?.findViewById(R.id.imageTxt)!!
        val textView: TextView = convertView.findViewById(R.id.titleTxt)!!

        imageView.setImageResource(imgProduct[position])
        textView.text = productTitle[position]

        return convertView
    }

}