package com.example.glowhackaton

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddActivity : AppCompatActivity() {

    private lateinit var food: Button
    private lateinit var cloth: Button
    private lateinit var register: Button
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        food = findViewById(R.id.food)
        cloth = findViewById(R.id.cloth)
        register = findViewById(R.id.add_store)

        food.setOnClickListener {
            food.isSelected = !food.isSelected
            cloth.isSelected = false
            selectButton(it as Button)
        }

        cloth.setOnClickListener {
            cloth.isSelected = !cloth.isSelected
            food.isSelected = false
            selectButton(it as Button)
        }

        register.setOnClickListener {
            if (food.isSelected || cloth.isSelected) {
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "가게 유형을 선택해주세요", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun selectButton(selectedButton: Button) {
        food.setBackgroundResource(R.drawable.round)
        cloth.setBackgroundResource(R.drawable.round)

        selectedButton.setBackgroundResource(R.drawable.round_after)
    }
}