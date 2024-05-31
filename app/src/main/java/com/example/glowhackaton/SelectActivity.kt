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

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select)

        val addStore = findViewById<Button>(R.id.add_store)
        val food: Button = findViewById(R.id.food)
        val cloth: Button = findViewById(R.id.cloth)
        val all: Button = findViewById(R.id.all_store)

        food.setOnClickListener {
            // MapActivity로 이동하는 인텐트를 생성합니다.
            val intent = Intent(this, MapActivity::class.java)
            // 생성된 인텐트를 실행하여 MapActivity로 이동합니다.
            startActivity(intent)
        }

        cloth.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }

        all.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }

        addStore.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }

    }
}