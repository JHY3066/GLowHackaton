package com.example.glowhackaton

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.glowhackaton.adapter.MapAdapter
import com.example.glowhackaton.adapter.MarketListAdapter
import com.example.glowhackaton.adapter.StoreListAdapter
import com.example.glowhackaton.adapter.YourDataAdapter
import com.example.glowhackaton.network.DataModel
import com.example.glowhackaton.network.Detail
import com.example.glowhackaton.network.Search
import com.example.glowhackaton.network.Select
import com.example.glowhackaton.network.Shop
import com.example.glowhackaton.network.Sum
import com.example.glowhackaton.network.Summary
import com.example.glowhackaton.network.YourDataModel
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MapActivity: AppCompatActivity(), MapAdapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MapAdapter
    private lateinit var dataList: List<DataModel>
    private lateinit var sumService: Sum
    private lateinit var button: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        button = findViewById(R.id.info)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val dataList: ArrayList<DataModel>? = intent.getParcelableArrayListExtra("dataList")

        val retrofit = Retrofit.Builder()
            .baseUrl("https://localhost:8080") // 실제 서버의 기본 URL로 변경하세요.
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Retrofit Service 생성
        sumService = retrofit.create(Sum::class.java)
        //항목 구분선
        val dividerItemDecoration = DividerItemDecoration(recyclerView.context, LinearLayoutManager.VERTICAL)
        recyclerView.addItemDecoration(dividerItemDecoration)

        if (dataList != null) {

        } else {
            // 데이터가 없을 경우 처리
        }
    }

    private fun showButton(selectedItem: String) {
        recyclerView.visibility = View.GONE

        val requestBody = RequestBody.create(MediaType.parse("text/plain"), selectedItem)
        sumService.sendDataSum(requestBody).enqueue(object : Callback<List<DataModel>> {
            override fun onResponse(call: Call<List<DataModel>>, response: Response<List<DataModel>>) {
                if (response.isSuccessful) {
                    // 서버로부터 데이터를 받아와서 버튼에 표시
                    val summaryList = response.body() ?: emptyList()
                    if (summaryList.isNotEmpty()) {
                        val summary = summaryList[0]
                        button.text = summary.name
                        button.visibility = View.VISIBLE
                        button.setOnClickListener {
                            val intent = Intent(this@MapActivity, StoreActivity::class.java)
                            // 여기서 summary 데이터를 StoreActivity로 넘길 수 있음
                            startActivity(intent)
                        }
                    }
                } else {
                    // 서버 요청 실패 처리
                }
            }
            override fun onFailure(call: Call<List<DataModel>>, t: Throwable) {
                // 네트워크 오류 처리
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

    private fun sendItemClickToServer(itemName: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://localhost:8080") // 실제 서버의 기본 URL로 변경하세요.
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(Sum::class.java)
        val requestBody = RequestBody.create(MediaType.parse("text/plain"), itemName)

        apiService.sendDataDetail(requestBody).enqueue(object : Callback<List<DataModel>> {
            override fun onResponse(call: Call<List<DataModel>>, response: Response<List<DataModel>>) {
                if (response.isSuccessful) {
                    Log.d("MapActivity", "Server response: ${response.body()}")
                } else {
                    Log.e("MapActivity", "Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<DataModel>>, t: Throwable) {
                Log.e("MapActivity", "Failed to send item click", t)
            }
        })

    }
}