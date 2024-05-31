package com.example.glowhackaton

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import okhttp3.Address

class RegisterActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var addressEditText: EditText
    private lateinit var callEditText: EditText
    private lateinit var timeEditText: EditText
    private lateinit var meunEditText: EditText
    private lateinit var addButton: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        nameEditText = findViewById(R.id.name)
        addressEditText = findViewById(R.id.address)
        callEditText = findViewById(R.id.call)
        timeEditText = findViewById(R.id.time)
        meunEditText = findViewById(R.id.menu)
        addButton = findViewById(R.id.add)

        addButton.setOnClickListener {
            val name = nameEditText.text.toString().trim()
            val address = addressEditText.text.toString().trim()
            val call = callEditText.text.toString().trim()
            val time = timeEditText.text.toString().trim()
            val menu = meunEditText.text.toString().trim()

            if (name.isNotEmpty()
                &&address.isNotEmpty()
                &&call.isNotEmpty()
                &&time.isNotEmpty()
                &&menu.isNotEmpty()) {
                showRegistrationSuccessPopup()
            } else {
                Toast.makeText(this, "전부 입력해 주세요", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showRegistrationSuccessPopup() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("등록 완료")
            .setMessage("등록이 완료되었습니다.")
            .setPositiveButton("확인") { _, _ ->
                startActivity(Intent(this, SelectActivity::class.java))
                finish()
            }
            .show()
    }
}