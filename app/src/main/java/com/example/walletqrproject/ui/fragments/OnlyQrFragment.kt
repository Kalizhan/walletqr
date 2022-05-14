package com.example.walletqrproject.ui.fragments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.walletqrproject.R
import com.example.walletqrproject.databinding.FragmentOnlyQrBinding
import com.example.walletqrproject.ui.adapter.WebUrlAdapter
import com.example.walletqrproject.ui.model.WebUrl
import com.example.walletqrproject.ui.viewmodels.PageWithQrScannerViewModel

class OnlyQrFragment : Fragment() {
    private lateinit var binding: FragmentOnlyQrBinding
    private lateinit var viewModel: PageWithQrScannerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOnlyQrBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this)[PageWithQrScannerViewModel::class.java]

        binding.recyclerWeb.layoutManager = LinearLayoutManager(requireContext())
        viewModel.readAllWebUrls.observe(viewLifecycleOwner, Observer { url ->
            if(url.isEmpty()){
                binding.tvEmptyQr.visibility = View.VISIBLE
                binding.imgEmptyFolderQr.visibility = View.VISIBLE
                binding.recyclerWeb.visibility = View.GONE
            }else{
                binding.tvEmptyQr.visibility = View.GONE
                binding.imgEmptyFolderQr.visibility = View.GONE
                binding.recyclerWeb.visibility = View.VISIBLE

                val adapter = WebUrlAdapter(requireContext(), url as ArrayList<WebUrl>)
                binding.recyclerWeb.adapter = adapter
                adapter.setOnItemClickListener(object : WebUrlAdapter.onItemClickListener{
                    override fun onItemClick(position: Int) {
                        val item = viewModel.readAllWebUrls.value?.get(position)
                        val builder = AlertDialog.Builder(requireContext())
                        builder.setTitle("Удалить заметку")
                        builder.setMessage("Вы точно хотите удалить заметку?")

                        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                            if (item != null) {
                                viewModel.deleteWebUrl(item)
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