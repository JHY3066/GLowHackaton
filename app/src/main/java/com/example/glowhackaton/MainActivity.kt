package com.example.glowhackaton

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager
    private lateinit var dotsLayout: LinearLayout
    private lateinit var bannerPagerAdapter: BannerPagerAdapter
    private val images = listOf(
        R.drawable.banner_image1,
        R.drawable.banner_image2,
        R.drawable.banner_image3
    )

    private var currentPage = 0
    private val DELAY_MS: Long = 1500 // 자동 넘김 딜레이
    private val handler = Handler(Looper.getMainLooper())
    private val update = Runnable {
        if (currentPage == images.size) {
            currentPage = 0
        }
        viewPager.setCurrentItem(currentPage++, true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        viewPager = findViewById(R.id.banner)
        dotsLayout = findViewById(R.id.dotsLayout)

        bannerPagerAdapter = BannerPagerAdapter(this, images)
        viewPager.adapter = bannerPagerAdapter

        addDotsIndicator()

        val pageListener = object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                currentPage = position
                addDotsIndicator()
            }

            override fun onPageScrollStateChanged(state: Int) {}
        }

        viewPager.addOnPageChangeListener(pageListener)
        handler.postDelayed(object : Runnable {
            override fun run() {
                if (currentPage == images.size) {
                    currentPage = 0
                }
                viewPager.setCurrentItem(currentPage++, true)
                handler.postDelayed(this, DELAY_MS)
            }
        }, DELAY_MS)
    }

    private fun addDotsIndicator() {
        val dots = arrayOfNulls<TextView>(images.size)
        dotsLayout.removeAllViews()

        for (i in dots.indices) {
            dots[i] = TextView(this).apply {
                text = "•"
                textSize = 35f
                setTextColor(resources.getColor(R.color.inactive_dot, applicationContext.theme))
            }
            dotsLayout.addView(dots[i])
        }

        if (dots.isNotEmpty()) {
            dots[currentPage]?.setTextColor(resources.getColor(R.color.active_dot, applicationContext.theme))
        }
    }
}