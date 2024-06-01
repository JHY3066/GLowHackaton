package com.example.glowhackaton

import com.example.glowhackaton.adapter.MarketListAdapter
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.glowhackaton.network.DataModel
import com.example.glowhackaton.network.Main
import com.example.glowhackaton.network.Search
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchActivity : AppCompatActivity(), MarketListAdapter.OnItemClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MarketListAdapter
    private lateinit var dataList: List<DataModel>
    private lateinit var searchButton: Button
    private lateinit var searchEditText: EditText
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        searchButton = findViewById(R.id.search_icon)
        searchEditText = findViewById(R.id.search_bar)

        searchButton.setOnClickListener {
            val searchQuery = searchEditText.text.toString()
            sendSearchQueryToServer(searchQuery)
            val intent = Intent(this, SelectActivity::class.java)
            startActivity(intent)
        }

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        fetchDataFromServer()

        //항목 구분선
        val dividerItemDecoration = DividerItemDecoration(recyclerView.context, LinearLayoutManager.VERTICAL)
        recyclerView.addItemDecoration(dividerItemDecoration)
    }

    private fun sendSearchQueryToServer(query: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://localhost:8080") // 실제 서버의 기본 URL로 변경하세요.
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(Search::class.java)
        val requestBody = RequestBody.create(MediaType.parse("text/plain"), query)

        apiService.sendMarketName(requestBody).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    Log.d("SearchActivity", "Server response: ${response.body()}")
                } else {
                    Log.e("SearchActivity", "Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("SearchActivity", "Failed to send search query", t)
            }
        })

    }

    //시장 목록 띄우기
    private fun fetchDataFromServer() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://localhost:8080") // 실제 서버의 기본 URL로 변경하세요.
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(Main::class.java)

        apiService.getDataList().enqueue(object : Callback<List<DataModel>> {
            override fun onResponse(call: Call<List<DataModel>>, response: Response<List<DataModel>>) {
                if (response.isSuccessful) {
                    dataList = response.body() ?: emptyList()
                    adapter = MarketListAdapter(dataList, this@SearchActivity) // 어댑터에 데이터를 설정합니다.
                    recyclerView.adapter = adapter
                } else {
                    Log.e("SearchActivity", "Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<DataModel>>, t: Throwable) {
                Log.e("SearchActivity", "Failed to fetch data", t)
            }
        })
    }
    override fun onItemClick(position: Int) {
        val clickedItem = dataList[position]
        sendItemClickToServer(clickedItem.name)
        val intent = Intent(this, SelectActivity::class.java)
        intent.putExtra("itemData", clickedItem)
        startActivity(intent)
    }

    //시장 정보 요청
    private fun sendItemClickToServer(itemName: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://localhost:8080") // 실제 서버의 기본 URL로 변경하세요.
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(Search::class.java)
        val requestBody = RequestBody.create(MediaType.parse("text/plain"), itemName)

        apiService.sendMarketName(requestBody).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    Log.d("SearchActivity", "Server response: ${response.body()}")
                } else {
                    Log.e("SearchActivity", "Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("SearchActivity", "Failed to send item click", t)
            }
        })

    }
}


