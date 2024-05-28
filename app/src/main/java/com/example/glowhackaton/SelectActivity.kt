package com.example.glowhackaton

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SelectActivity : AppCompatActivity() {

    private lateinit var food: Button
    private lateinit var cloth: Button
    private lateinit var store: Button

    private var isFoodPressed = true
    private var isClothPressed = true
    private var isStorePressed = true

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select)


        food = findViewById(R.id.food)
        food.setOnClickListener {
            if (isFoodPressed) {
                food.background = getDrawable(R.drawable.round_after)
                food.setTextColor(Color.WHITE)
            } else {
                food.background = getDrawable(R.drawable.round)
                food.setTextColor(Color.parseColor("#1C32A5"))
            }
            isFoodPressed = !isFoodPressed
        }

        cloth = findViewById(R.id.cloth)
        cloth.setOnClickListener {
            if (isClothPressed) {
                cloth.background = getDrawable(R.drawable.round_after)
                cloth.setTextColor(Color.WHITE)
            } else {
                cloth.background = getDrawable(R.drawable.round)
                cloth.setTextColor(Color.parseColor("#1C32A5"))
            }
            isClothPressed = !isClothPressed
        }

        store = findViewById(R.id.all_store)
        store.setOnClickListener {
            if (isStorePressed) {
                store.background = getDrawable(R.drawable.round_after)
                store.setTextColor(Color.WHITE)
            } else {
                store.background = getDrawable(R.drawable.round)
                store.setTextColor(Color.parseColor("#1C32A5"))
            }
            isStorePressed = !isStorePressed
        }

        val foodButton: Button = findViewById(R.id.food)
        foodButton.setOnClickListener {
            // MapActivity로 이동하는 인텐트를 생성합니다.
            val intent = Intent(this, MapActivity::class.java)
            // 생성된 인텐트를 실행하여 MapActivity로 이동합니다.
            startActivity(intent)
        }

    }
}