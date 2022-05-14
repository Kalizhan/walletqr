package com.example.walletqrproject.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.walletqrproject.R
import com.example.walletqrproject.databinding.FragmentAboutProjectBinding

class AboutProjectFragment : Fragment() {
    private lateinit var binding: FragmentAboutProjectBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAboutProjectBinding.inflate(inflater, container, false)



        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }
}