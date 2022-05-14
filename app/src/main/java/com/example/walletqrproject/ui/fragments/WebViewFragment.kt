package com.example.walletqrproject.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.walletqrproject.R
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.walletqrproject.databinding.FragmentWebViewBinding

class WebViewFragment : Fragment() {

    private lateinit var binding: FragmentWebViewBinding
//    private lateinit var webView: WebView
    var displayMessage: String? = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWebViewBinding.inflate(inflater, container, false)

        displayMessage = arguments?.getString("message")

        binding.webview.settings.javaScriptEnabled = true

        binding.webview.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                view?.loadUrl(request.toString())
                return true
            }
        }

        binding.webview.loadUrl("$displayMessage")

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }
}