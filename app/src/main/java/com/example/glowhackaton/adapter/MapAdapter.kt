package com.example.glowhackaton.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.glowhackaton.R
import com.example.glowhackaton.network.DataModel
import com.example.glowhackaton.network.Shop
import com.example.glowhackaton.network.Summary
import com.example.glowhackaton.network.YourDataModel

class MapAdapter(private val dataList: ArrayList<DataModel>, private val listener: MarketListAdapter.OnItemClickListener) :
    RecyclerView.Adapter<MapAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int)

        companion object {
            fun onItemClick(position: Int) {

            }
        }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_your_data, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.nameTextView.text = item.name
        holder.itemView.setOnClickListener {
            // 클릭 이벤트 발생 시 ItemClickListener의 onItemClick 함수 호출
            OnItemClickListener.onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}