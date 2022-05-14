package com.example.walletqrproject.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.walletqrproject.R
import com.example.walletqrproject.databinding.FragmentHomePageBinding
import com.example.walletqrproject.ui.adapter.NoteAdapter
import com.example.walletqrproject.ui.model.Note
import com.example.walletqrproject.ui.viewmodels.MainActivitySaveMoneyViewModel
import com.example.walletqrproject.ui.viewmodels.NoteViewModel
import com.example.walletqrproject.ui.viewmodels.ShowCreditCardInfoViewModel


class HomePageFragment : Fragment(), View.OnClickListener, IOnBackPressed {

    private lateinit var binding: FragmentHomePageBinding
    private lateinit var viewModel: MainActivitySaveMoneyViewModel
    private lateinit var viewModel2: NoteViewModel

    private lateinit var viewModel3: ShowCreditCardInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomePageBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this)[MainActivitySaveMoneyViewModel::class.java]
        viewModel2 = activity?.run {
            ViewModelProvider(this)[NoteViewModel::class.java]
        } ?: throw Exception("Invalid Activity")

        viewModel3 = ViewModelProvider(this)[ShowCreditCardInfoViewModel::class.java]

        viewModel.getAccountInfo("Наличные").observe(viewLifecycleOwner, Observer { initial ->
            if (initial == null) {
                binding.accountCard1.text = "0 тг"
            } else {
                binding.accountCard1.text = "$initial тг"
            }
        })

        viewModel.getAccountInfo("Карта 1").observe(viewLifecycleOwner, Observer { initial ->
            if (initial == null) {
                binding.accountCard2.text = "0 тг"
            } else {
                binding.accountCard2.text = "$initial тг"
            }
        })

        viewModel.getAccountInfo("Карта 2").observe(viewLifecycleOwner, Observer { initial ->
            if (initial == null) {
                binding.accountCard3.text = "0 тг"
            } else {
                binding.accountCard3.text = "${initial} тг"
            }
        })

        binding.recyclerNoteHome.layoutManager = LinearLayoutManager(requireContext())
        viewModel2.readAllNotes.observe(viewLifecycleOwner, Observer { note ->
            if (note.isEmpty()) {
                binding.tvEmpty.visibility = View.VISIBLE
                binding.imgEmptyFolder.visibility = View.VISIBLE
                binding.recyclerNoteHome.visibility = View.GONE
            } else {
                binding.tvEmpty.visibility = View.GONE
                binding.imgEmptyFolder.visibility = View.GONE
                binding.recyclerNoteHome.visibility = View.VISIBLE
                val adapter = NoteAdapter(note as ArrayList<Note>, requireActivity().baseContext)
                binding.recyclerNoteHome.adapter = adapter
                adapter.setOnItemClickListener(object : NoteAdapter.onItemClickListener {
                    override fun onItemClick(position: Int) {

                    }
                })
            }
        })

        checkCardSize()

        binding.btnShowMoreNotes.setOnClickListener(this)
        binding.cardView1.setOnClickListener(this)
        binding.btnSaveCards.setOnClickListener(this)

        return binding.root
    }

    private fun checkCardSize() {
        viewModel3.readAllData.observe(viewLifecycleOwner, Observer { credit ->
            if (credit.size >= 2){
                binding.cardView1.visibility = View.GONE
            }else if(credit.size == 1){
                binding.cardView1.visibility = View.VISIBLE
                binding.tvCard.visibility = View.VISIBLE
                binding.cardViewCard1.visibility = View.VISIBLE
                binding.cardViewCard2.visibility = View.GONE
            }else{
                binding.cardView1.visibility = View.VISIBLE
                binding.tvCard.visibility = View.GONE
                binding.cardViewCard1.visibility = View.GONE
                binding.cardViewCard2.visibility = View.GONE
            }
        })
    }

    override fun onClick(p0: View?) {
        when (p0) {
            binding.btnShowMoreNotes -> {
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                val fragmentB = NotePageFragment()

                transaction.replace(R.id.fragment_container, fragmentB)
                transaction.commit()
            }
            binding.cardView1 -> {
                onScanPress()
            }
            binding.btnSaveCards -> {
                onScanPress()
            }
        }
    }



    fun onScanPress() {
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        val fragmentB = ShowCreditCardInfoFragment()

        transaction.replace(R.id.fragment_container, fragmentB)
        transaction.commit()
    }

    override fun onBackPressed(): Boolean {
        return true
    }
}