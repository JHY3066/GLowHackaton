package com.example.glowhackaton

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.example.glowhackaton.R

class BannerPagerAdapter(private val context: Context, private val images: List<Int>) :
    PagerAdapter() {

    override fun getCount(): Int {
        return images.size
    }

    @SuppressLint("MissingInflatedId")
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.banner_item, container, false)
        val imageView = view.findViewById<ImageView>(R.id.imageView)
        imageView.setImageResource(images[position])
        container.addView(view)
        return view
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view == obj
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View)
    }
}
