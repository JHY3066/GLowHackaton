package com.example.glowhackaton

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.glowhackaton.adapter.MarketListAdapter
import com.example.glowhackaton.adapter.StoreListAdapter


class MapActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StoreListAdapter
    private lateinit var button: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val dataList = getDummyData()
        adapter = StoreListAdapter(this, dataList) {
            selectedItem -> showButton(selectedItem)
        }
        recyclerView.adapter = adapter

        //항목 구분선
        val dividerItemDecoration = DividerItemDecoration(recyclerView.context, LinearLayoutManager.VERTICAL)
        recyclerView.addItemDecoration(dividerItemDecoration)

        button = findViewById(R.id.info)
    }

    private fun showButton(selectedItem: String) {
        recyclerView.visibility = View.GONE

        button.text = selectedItem
        button.visibility = View.VISIBLE
        button.setOnClickListener {
            val intent = Intent(this, StoreActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getDummyData(): MutableList<String> {
        val data = mutableListOf<String>()
        for (i in 1..10) {
            data.add("Store $1")
        }
        return data
    }
}