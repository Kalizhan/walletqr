package com.example.walletqrproject.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.walletqrproject.R
import com.example.walletqrproject.databinding.CreditCardLayoutBinding
import com.example.walletqrproject.ui.model.CardInfo
import java.util.*

class CreditCardFormAdapter(private val list: List<CardInfo>): RecyclerView.Adapter<CreditCardFormAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val binding = CreditCardLayoutBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.credit_card_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = list[position]
        with(holder) {
            val card: String = item.cardNumber
            val lastCard = card.split("").toTypedArray()
            var cardNumber = ""
            for (i in lastCard.indices) {
                cardNumber += if (i % 4 == 0) {
                    lastCard[i] + " "
                } else {
                    lastCard[i]
                }
            }

            binding.tvCardNumber.text = cardNumber

            val date: String = item.expirationDate
            val lastDate = date.split("").toTypedArray()
            var last = ""
            for (i in lastDate.indices) {
                last += if (i == 2) {
                    lastDate[i] + "/"
                } else {
                    lastDate[i]
                }
            }

            binding.tvDate.text = last
            binding.tvMobileNumber.text = "+${item.countryCode} ${item.mobileNumber}"
            binding.tvPostalCode.text = "Почтовый индекс: ${item.postalCode}"
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}