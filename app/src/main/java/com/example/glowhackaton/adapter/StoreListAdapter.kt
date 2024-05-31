package com.example.glowhackaton.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.glowhackaton.R
import com.example.glowhackaton.SearchActivity
import com.example.glowhackaton.SelectActivity
import com.example.glowhackaton.StoreActivity

class StoreListAdapter(
    private val context: Context,
    private val itemList: List<String>,
    private val onItemClicked: (String) -> Unit
) : RecyclerView.Adapter<StoreListAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.itemText)

        fun bind(data: String) {
            textView.text = data
            itemView.setOnClickListener {
                onItemClicked(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.store_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}
