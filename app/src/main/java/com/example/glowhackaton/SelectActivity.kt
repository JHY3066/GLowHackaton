package com.example.glowhackaton

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.glowhackaton.adapter.YourDataAdapter
import com.example.glowhackaton.network.DataModel
import com.example.glowhackaton.network.Search
import com.example.glowhackaton.network.Select
import com.example.glowhackaton.network.Shop
import com.example.glowhackaton.network.YourDataModel
import kotlinx.coroutines.CoroutineScope
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

class SelectActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: YourDataAdapter
    private lateinit var dataList: List<YourDataModel>

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select)

        val addStore = findViewById<Button>(R.id.add_store)
        val food: Button = findViewById(R.id.food)
        val cloth: Button = findViewById(R.id.cloth)
        val all: Button = findViewById(R.id.all_store)

        val itemData: YourDataModel? = intent.getParcelableExtra("itemData")
        val name: TextView = findViewById(R.id.market_name)
        val call_num: TextView = findViewById(R.id.call_num)
        val address: TextView = findViewById(R.id.address)
        val workingHour: TextView = findViewById(R.id.info)
        val information: TextView = findViewById(R.id.hashtag)

        itemData?.let {
            name.text = it.name
            call_num.text = it.telephone
            address.text = it.address
            workingHour.text=it.workingHour
            information.text=it.information
        }

        food.setOnClickListener {
            sendDataSelect(name.text.toString(), 1)
            // MapActivity로 이동하는 인텐트를 생성합니다.
            val intent = Intent(this, MapActivity::class.java)
            // 생성된 인텐트를 실행하여 MapActivity로 이동합니다.
            startActivity(intent)
        }

        cloth.setOnClickListener {
            sendDataSelect(name.text.toString(), 2)

            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }

        all.setOnClickListener {
            sendDataAll(name.text.toString())
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }

        addStore.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        fetchDataFromServer()
    }

    private fun sendDataSelect(name: String, type: Int) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://localhost:8080") // 실제 서버의 기본 URL로 변경하세요.
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(Select::class.java)

        apiService.sendDataSelect(name, type).enqueue(object : Callback<List<Shop>> {
            override fun onResponse(call: Call<List<Shop>>, response: Response<List<Shop>>) {
                if (response.isSuccessful) {
                    val intent = Intent(this@SelectActivity, MapActivity::class.java)
                    intent.putExtra("dataList", ArrayList(response.body()))
                } else {
                    // 에러 처리
                }
            }

            override fun onFailure(call: Call<List<Shop>>, t: Throwable) {
                // 실패 처리
            }
        })
    }

    private fun sendDataAll(name: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://localhost:8080") // 실제 서버의 기본 URL로 변경하세요.
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(Select::class.java)
        val requestBody = RequestBody.create(MediaType.parse("text/plain"), name)

        apiService.sendDataAll(requestBody).enqueue(object : Callback<List<DataModel>> {
            override fun onResponse(call: Call<List<DataModel>>, response: Response<List<DataModel>>) {
                if (response.isSuccessful) {
                    val intent = Intent(this@SelectActivity, MapActivity::class.java)
                    intent.putExtra("dataList", ArrayList(response.body()))
                } else {
                    // 에러 처리
                }
            }

            override fun onFailure(call: Call<List<DataModel>>, t: Throwable) {
                // 실패 처리
            }
        })
    }


    private fun fetchDataFromServer() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://localhost:8080") // 실제 서버의 기본 URL로 변경하세요.
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(Search::class.java)

        apiService.getDataList().enqueue(object : Callback<List<YourDataModel>> {
            override fun onResponse(call: Call<List<YourDataModel>>, response: Response<List<YourDataModel>>) {
                if (response.isSuccessful) {
                    dataList = response.body() ?: emptyList()
                    adapter = YourDataAdapter(dataList)
                    recyclerView.adapter = adapter
                } else {
                    // 에러 처리
                }
            }

            override fun onFailure(call: Call<List<YourDataModel>>, t: Throwable) {
                // 실패 처리
            }
        })
    }
}