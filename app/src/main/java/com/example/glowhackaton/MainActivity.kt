package com.example.glowhackaton

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

class MainActivity : AppCompatActivity() {

    private lateinit var search_bar: SearchView
    private lateinit var food: Button
    private lateinit var cloth: Button
    private var isFoodPressed = true
    private var isClothPressed = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        search_bar = findViewById(R.id.search_bar)
        search_bar.queryHint="시장을 검색하세요"
        search_bar.isIconifiedByDefault = false

        search_bar.setOnClickListener {
            showKeyboard()
        }

        search_bar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {searchQuery(it)}
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        food = findViewById(R.id.food)
        food.setOnClickListener {
            if (isFoodPressed) {
                food.background = getDrawable(R.drawable.round_after)
                food.setTextColor(Color.WHITE)
            }
            else {
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
            }
            else {
                cloth.background = getDrawable(R.drawable.round)
                cloth.setTextColor(Color.parseColor("#1C32A5"))
            }
            isClothPressed = !isClothPressed
        }

    }

    private fun showKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    private fun searchQuery(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitClient.apiService.search(query)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful && response.body()?.success == true) {
                        // 검색 성공 처리
                        Toast.makeText(this@MainActivity, "검색 성공: ${response.body()?.data}", Toast.LENGTH_SHORT).show()
                    } else {
                        // 검색 실패 처리 (팝업 표시)
                        showAlertDialog("검색 결과가 없습니다")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    showAlertDialog("오류가 발생했습니다")
                }
            }
        }
    }

    private fun showAlertDialog(message: String) {
        AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton("확인") { dialog, _ -> dialog.dismiss() }
            .show()
    }
}
