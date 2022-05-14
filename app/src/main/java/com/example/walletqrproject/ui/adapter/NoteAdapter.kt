package com.example.walletqrproject.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.AdapterView.OnItemLongClickListener
import androidx.recyclerview.widget.RecyclerView
import com.example.walletqrproject.R
import com.example.walletqrproject.databinding.UniqueNoteBinding
import com.example.walletqrproject.ui.model.Note


class NoteAdapter(private val noteList: ArrayList<Note>, private val context: Context): RecyclerView.Adapter<NoteAdapter.MyViewHolder>() {
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
        val view = LayoutInflater.from(context).inflate(R.layout.unique_note, parent, false)
        return MyViewHolder(view, mListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = noteList[position]
        with(holder){
            binding.tvNoteDate.text = item.noteDate
            binding.tvNoteText.text = item.noteText
        }
    }

    override fun getItemCount(): Int {
        return noteList.size
    }
}