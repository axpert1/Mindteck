package com.app.mindteck.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.viewpager.widget.PagerAdapter

import com.app.mindteck.utils.loadImageFromUrl
import com.app.rfid.R


class ViewPagerAdapter constructor(val context: Context, val images: MutableList<String>) :
    PagerAdapter() {


    private val mLayoutInflater: LayoutInflater by lazy { context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater }


    override fun getCount(): Int {
        return images.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as LinearLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemView: View = mLayoutInflater.inflate(R.layout.slider_item, container, false)
        itemView.findViewById<ImageView>(R.id.iv_slider).apply {
            loadImageFromUrl(images[position])
        }
        container.addView(itemView)
        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout?)
    }
}