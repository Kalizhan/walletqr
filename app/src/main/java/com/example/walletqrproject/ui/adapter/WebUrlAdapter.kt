package com.example.walletqrproject.ui.adapter

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.walletqrproject.R
import com.example.walletqrproject.databinding.UniqueNoteBinding
import com.example.walletqrproject.ui.model.WebUrl

class WebUrlAdapter(private val context: Context, private val list: ArrayList<WebUrl>): RecyclerView.Adapter<WebUrlAdapter.MyViewHolder>() {
    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }


    class MyViewHolder(itemView: View, listener: onItemClickListener): RecyclerView.ViewHolder(itemView) {
        val binding = UniqueNoteBinding.bind(itemView)
        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.unique_note, parent, false)
        return MyViewHolder(view, mListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = list[position]
        with(holder){
            binding.tvNoteText.text = item.url
            binding.tvNoteDate.text = item.date
        }

        holder.binding.relativeLayout.setOnLongClickListener(OnLongClickListener {
            val clipboardManager = context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Copied Text", holder.binding.tvNoteText.text.toString())
            clipboardManager.setPrimaryClip(clip)

            Toast.makeText(context, "Скопировано в буфер обмена", Toast.LENGTH_SHORT).show()
            true
        })
    }

    override fun getItemCount(): Int {
        return list.size
    }

}