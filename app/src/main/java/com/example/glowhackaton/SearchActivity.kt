package com.example.glowhackaton

import com.example.glowhackaton.adapter.MarketListAdapter
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.glowhackaton.network.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchActivity : AppCompatActivity(), MarketListAdapter.OnItemClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MarketListAdapter
    private lateinit var button: Button
    private lateinit var viewModel: MarketViewModel

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val search_bar = findViewById<androidx.appcompat.widget.SearchView>(R.id.search_bar)
        search_bar.queryHint = "시장을 검색하세요"

        search_bar.setOnClickListener {
            showKeyboard()
        }

        search_bar.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { searchQuery(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        var searchButton: Button = findViewById(R.id.search_icon)

        searchButton.setOnClickListener {
            val intent = Intent(this, SelectActivity::class.java)
            startActivity(intent)
        }

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val dataList = getDummyData()
        adapter = MarketListAdapter(dataList, this)
        recyclerView.adapter = adapter

        //항목 구분선
        val dividerItemDecoration = DividerItemDecoration(recyclerView.context, LinearLayoutManager.VERTICAL)
        recyclerView.addItemDecoration(dividerItemDecoration)

        button = findViewById(R.id.info)
    }

    override fun onItemClick(position: Int) {
        val intent = Intent(this, SelectActivity::class.java)
        intent.putExtra("position", position)
        startActivity(intent)
    }
    private fun getDummyData(): List<String> {
        val data = mutableListOf<String>()
        for (i in 1..10) {
            data.add("Market $1")
        }
        return data
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
                        val intent = Intent(this@SearchActivity, SelectActivity::class.java)
                        startActivity(intent)
                    } else {
                        // 검색 실패 처리 (팝업 표시)
                        showAlertDialog("검색 결과가 없습니다")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    showAlertDialog("검색 결과가 없습니다")
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


