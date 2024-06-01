package com.example.glowhackaton

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.glowhackaton.adapter.BannerPagerAdapter
import com.example.glowhackaton.network.Main

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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

        val marketSearchButton: Button = findViewById(R.id.market_search)
        marketSearchButton.setOnClickListener {
            sendButtonClickToServer()
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }
    }

    private fun sendButtonClickToServer() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://localhost:8080") // 서버의 기본 URL 설정
            .addConverterFactory(GsonConverterFactory.create()) // JSON 변환기 설정
            .build()

        val apiService = retrofit.create(Main::class.java)

        apiService.sendButtonClick().enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    println("Button click sent successfully.")
                } else {
                    println("Button click failed to send.")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                println("Failed to send button click: ${t.message}")
            }
        })
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