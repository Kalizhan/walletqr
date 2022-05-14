package com.example.walletqrproject.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.walletqrproject.R
import com.example.walletqrproject.databinding.FragmentQrScannerPageBinding

class QrScannerPageFragment : Fragment() {

    private lateinit var binding: FragmentQrScannerPageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentQrScannerPageBinding.inflate(inflater, container, false)

        val fragmentA = PageWithQrScannerFragment()
        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, fragmentA).commit()

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }
}