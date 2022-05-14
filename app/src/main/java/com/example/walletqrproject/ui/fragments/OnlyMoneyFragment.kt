package com.example.walletqrproject.ui.fragments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.walletqrproject.databinding.ActivityMainBinding
import com.example.walletqrproject.databinding.FragmentOnlyMoneyBinding
import com.example.walletqrproject.ui.adapter.MoneyRecordAdapter
import com.example.walletqrproject.ui.model.MoneyData
import com.example.walletqrproject.ui.viewmodels.MainActivitySaveMoneyViewModel


class OnlyMoneyFragment : Fragment() {
    private lateinit var binding: FragmentOnlyMoneyBinding
    private lateinit var viewmodel1: MainActivitySaveMoneyViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOnlyMoneyBinding.inflate(inflater, container, false)

        viewmodel1 = ViewModelProvider(this)[MainActivitySaveMoneyViewModel::class.java]

        binding.recyclerMoney.layoutManager = LinearLayoutManager(requireContext())

        viewmodel1.readAllData.observe(viewLifecycleOwner, Observer { money ->
            if (money.isEmpty()){
                binding.tvEmptyMoney.visibility = View.VISIBLE
                binding.imgEmptyFolderMoney.visibility = View.VISIBLE
                binding.recyclerMoney.visibility = View.GONE
            }else{
                binding.tvEmptyMoney.visibility = View.GONE
                binding.imgEmptyFolderMoney.visibility = View.GONE
                binding.recyclerMoney.visibility = View.VISIBLE
                val adapter = MoneyRecordAdapter(
                    money as ArrayList<MoneyData>,
                    requireContext()
                )
                binding.recyclerMoney.adapter = adapter
                adapter.setOnItemClickListener(object : MoneyRecordAdapter.onItemClickListener{
                    override fun onItemClick(position: Int) {
                        val item = viewmodel1.readAllData.value?.get(position)
                        val builder = AlertDialog.Builder(requireContext())
                        builder.setTitle("Удалить заметку")
                        builder.setMessage("Вы точно хотите удалить заметку?")

                        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                            if (item != null) {
                                viewmodel1.deleteMoneyData(item)
                            }else{
                                Toast.makeText(context, "LiveData is empty", Toast.LENGTH_SHORT).show()
                            }
                            dialog.cancel()
                        }

                        builder.setNegativeButton(android.R.string.no) { dialog, which ->
                            dialog.cancel()
                        }
                        builder.show()
                    }
                })
            }
        })

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }
}