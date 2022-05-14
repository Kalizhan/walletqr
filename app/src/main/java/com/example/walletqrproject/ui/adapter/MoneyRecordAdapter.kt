package com.example.walletqrproject.ui.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.walletqrproject.R
import com.example.walletqrproject.databinding.UniqueMoneyBinding
import com.example.walletqrproject.ui.model.MoneyData

class MoneyRecordAdapter(private val moneyList: ArrayList<MoneyData>, val context: Context): RecyclerView.Adapter<MoneyRecordAdapter.MyViewHolder>() {
    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    class MyViewHolder(itemView: View, listener: onItemClickListener): RecyclerView.ViewHolder(itemView) {
        val binding = UniqueMoneyBinding.bind(itemView)
        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.unique_money, parent, false)
        return MyViewHolder(view, mListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = moneyList[position]
        with(holder){
            if (item.inorout == "plus"){
                binding.tvNewmoney.text = "+ ${item.newMoney}"
                binding.tvNewmoney.setTextColor(ContextCompat.getColor(context, R.color.ic_launcher_background))
                binding.imgOfInorout.setImageResource(R.drawable.right_arrow)
                binding.imgOfInorout.setColorFilter(ContextCompat.getColor(context, R.color.ic_launcher_background))
            }else{
                binding.tvNewmoney.text = "- ${item.newMoney}"
                binding.tvNewmoney.setTextColor(ContextCompat.getColor(context, R.color.red))
                binding.imgOfInorout.setImageResource(R.drawable.left_arrow)
                binding.imgOfInorout.setColorFilter(ContextCompat.getColor(context, R.color.red))
            }

            binding.tvDate.text = item.date
            binding.tvAccount.text = item.account
            binding.tvCategory.text = item.category
//            binding.tvInitialMoney.text = "${item.initialMoney} тг"
        }
    }

    override fun getItemCount(): Int {
        return moneyList.size
    }

}