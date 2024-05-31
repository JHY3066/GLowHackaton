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
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.example.glowhackaton.adapter.BannerPagerAdapter
import com.example.glowhackaton.model.ResponseAllMarket
import com.example.glowhackaton.network.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager
    private lateinit var dotsLayout: LinearLayout
    private lateinit var bannerPagerAdapter: BannerPagerAdapter
    private  lateinit var viewModel: MarketViewModel
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

        viewModel = ViewModelProvider(this).get(MarketViewModel::class.java)

        val marketSearchButton: Button = findViewById(R.id.market_search)
        marketSearchButton.setOnClickListener {
            /*CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = RetrofitClient.marketService.getMarket()
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            val marketResponse: ResponseAllMarket? = response.body()
                            marketResponse.let {
                                if (marketResponse != null) {
                                    viewModel.setMarketResponse(marketResponse)
                                }
                                val intent = Intent(this@MainActivity, SearchActivity::class.java)
                                startActivity(intent)
                            }
                        }
                        else {
                            //요청 실패 시?
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }*/
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }
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