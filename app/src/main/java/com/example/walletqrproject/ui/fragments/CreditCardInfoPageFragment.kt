package com.example.walletqrproject.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.walletqrproject.R
import com.example.walletqrproject.databinding.FragmentCreditCardInfoPageBinding
import com.example.walletqrproject.ui.adapter.CreditCardFormAdapter
import com.example.walletqrproject.ui.viewmodels.ShowCreditCardInfoViewModel

class CreditCardInfoPageFragment : Fragment() {

    private lateinit var binding: FragmentCreditCardInfoPageBinding
    private lateinit var viewModel: ShowCreditCardInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCreditCardInfoPageBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this)[ShowCreditCardInfoViewModel::class.java]

        binding.recyclerCreditCard.layoutManager = LinearLayoutManager(requireContext())
        viewModel.readAllData.observe(viewLifecycleOwner, Observer { credit ->
                if(credit.isEmpty()){
                    binding.tvEmptyMoney.visibility = View.VISIBLE
                    binding.imgEmptyFolderMoney.visibility = View.VISIBLE
                    binding.recyclerCreditCard.visibility = View.GONE
                }else{
                    binding.tvEmptyMoney.visibility = View.GONE
                    binding.imgEmptyFolderMoney.visibility = View.GONE
                    binding.recyclerCreditCard.visibility = View.VISIBLE

                    val adapter = CreditCardFormAdapter(credit)
                    binding.recyclerCreditCard.adapter = adapter
                }
        })

        return binding.root
    }
}