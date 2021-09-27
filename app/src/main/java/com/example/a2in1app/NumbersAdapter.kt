package com.example.numbersgameapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a2in1app.R
import kotlinx.android.synthetic.main.item_row.view.*

class NumbersAdapter(private val messages: ArrayList<String>) :
    RecyclerView.Adapter<NumbersAdapter.MessageViewHolder>() {
    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_row,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages[position]
        holder.itemView.apply { message_TextView.text = message }
    }

    override fun getItemCount() = messages.size

}