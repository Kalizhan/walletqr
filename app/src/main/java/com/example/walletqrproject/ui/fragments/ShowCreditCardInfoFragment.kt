package com.example.walletqrproject.ui.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.walletqrproject.databinding.FragmentSowCreditCardInfoBinding
import com.example.walletqrproject.ui.model.CardInfo
import com.example.walletqrproject.ui.viewmodels.ShowCreditCardInfoViewModel

class ShowCreditCardInfoFragment : Fragment() {

    private lateinit var binding: FragmentSowCreditCardInfoBinding
    private lateinit var viewModel: ShowCreditCardInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSowCreditCardInfoBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[ShowCreditCardInfoViewModel::class.java]

        binding.cardForm.cardRequired(true)
            .expirationRequired(true)
            .cvvRequired(true)
            .postalCodeRequired(true)
            .mobileNumberRequired(true)
            .setup(requireActivity())

        binding.cardForm.cvvEditText.inputType =
            InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD

        binding.btnSave.setOnClickListener {
            if (binding.cardForm.isValid) {
                val cardInfo = CardInfo(
                    binding.cardForm.cardNumber,
                    binding.cardForm.expirationDateEditText.text.toString(),
                    binding.cardForm.cvv,
                    binding.cardForm.postalCode,
                    binding.cardForm.countryCode,
                    binding.cardForm.mobileNumber
                )
                Log.i("TAG", "$cardInfo")
                viewModel.insertCard(cardInfo)
                Toast.makeText(requireContext(), "Сохранено", Toast.LENGTH_SHORT).show()
//                val pref = requireContext().getSharedPreferences("AccountCardInfo", Context.MODE_PRIVATE)
//                val editor: SharedPreferences.Editor = pref.edit()
//
//                editor.putString("cardnumber",binding.cardForm.cardNumber)
//                editor.putString("expirationdate", binding.cardForm.expirationDateEditText.text.toString())
//                editor.putString("cvv", binding.cardForm.cvv)
//                editor.putString("postalcode", binding.cardForm.postalCode)
//                editor.putString("phonenumber", binding.cardForm.mobileNumber)
//
//                editor.apply()
            } else {
                Toast.makeText(requireContext(), "Пожалуйста заполните полностью", Toast.LENGTH_LONG)
                    .show();
            }
        }

        return binding.root
    }
}