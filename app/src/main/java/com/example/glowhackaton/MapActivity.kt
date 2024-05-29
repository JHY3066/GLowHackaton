package com.example.glowhackaton

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity


class MapActivity : AppCompatActivity() {

    private lateinit var scrollView: ScrollView
    private lateinit var linearLayout: LinearLayout
    private lateinit var store: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        scrollView = findViewById(R.id.scroll_view)
        linearLayout = scrollView.getChildAt(0) as LinearLayout
        store = findViewById(R.id.store_1)

        store.setOnClickListener {
            changeToSingleButton()
        }
    }

    private fun changeToSingleButton() {
        // 기존 LinearLayout의 모든 뷰를 제거
        linearLayout.removeAllViews()

        // 새로운 버튼 생성
        val singleButton = Button(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            text = "가게 세부 정보"
            setPadding(16, 16, 16, 16)
        }

        singleButton.setOnClickListener {
            val intent = Intent(
                this@MapActivity,
                StoreActivity::class.java
            )
            startActivity(intent)
        }

        // LinearLayout에 새로운 버튼 추가
        linearLayout.addView(singleButton)
    }
}