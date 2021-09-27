package com.example.a2in1app

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_row.view.*

class PhraseAdapter(val messages: ArrayList<String>):
    RecyclerView.Adapter<PhraseAdapter.MessageViewHolder>() {
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

        holder.itemView.apply {
            message_TextView.text = message
            if(message.startsWith("Found")){
                message_TextView.setTextColor(Color.GREEN)
            }else if(message.startsWith("No")||message.startsWith("Wrong")){
                message_TextView.setTextColor(Color.RED)
            }else{
                message_TextView.setTextColor(Color.BLACK)
            }
        }
    }

    override fun getItemCount() = messages.size
}