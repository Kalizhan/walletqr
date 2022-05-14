package com.example.walletqrproject.ui.fragments

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.walletqrproject.R
import com.example.walletqrproject.databinding.AddNoteBinding
import com.example.walletqrproject.databinding.FragmentNoteBinding
import com.example.walletqrproject.ui.adapter.NoteAdapter
import com.example.walletqrproject.ui.model.Note
import com.example.walletqrproject.ui.viewmodels.NoteViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class NotePageFragment : Fragment() {
    private lateinit var binding: FragmentNoteBinding
    private lateinit var viewModel: NoteViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //binding = ActivityMainBinding.inflate(layoutInflater)
        binding = FragmentNoteBinding.inflate(inflater, container, false)

//        viewModel = ViewModelProvider(this)[NoteViewModel::class.java]
//        binding.btnAddNote.setOnClickListener {
//            showDialog()
//        }
//
//        binding.recyclerNote.layoutManager = LinearLayoutManager(requireContext())
//        viewModel.readAllNotes.observe(viewLifecycleOwner, androidx.lifecycle.Observer { note ->
//            if(note.isEmpty()){
//                binding.tvEmpty.visibility = View.VISIBLE
//                binding.imgEmptyFolder.visibility = View.VISIBLE
//                binding.recyclerNote.visibility = View.GONE
//            }else{
//                binding.tvEmpty.visibility = View.GONE
//                binding.imgEmptyFolder.visibility = View.GONE
//                binding.recyclerNote.visibility = View.VISIBLE
//
//                val adapter = NoteAdapter(note as ArrayList<Note>, requireContext())
//                binding.recyclerNote.adapter = adapter
//                adapter.setOnItemClickListener(object : NoteAdapter.onItemClickListener {
//                    override fun onItemClick(position: Int) {
//                        val item = viewModel.readAllNotes.value?.get(position)
//                        val builder = AlertDialog.Builder(requireContext())
//                        builder.setTitle("Удалить заметку")
//                        builder.setMessage("Вы точно хотите удалить заметку?")
//
//                        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
//                            if (item != null) {
//                                viewModel.deleteNote(item)
//                            }else{
//                                Toast.makeText(context, "LiveData is empty", Toast.LENGTH_SHORT).show()
//                            }
//                            dialog.cancel()
//                        }
//
//                        builder.setNegativeButton(android.R.string.no) { dialog, which ->
//                            dialog.cancel()
//                        }
//                        builder.show()
//                    }
//                })
//            }
//        })

        //this = requireContext() / requireActivity()

        Toast.makeText(requireActivity(), "Text msg", Toast.LENGTH_SHORT).show()
        return binding.root
    }

    private fun showDialog(){
//        Log.i("TAG", "work work work")
        val viewBinding = AddNoteBinding.inflate(LayoutInflater.from(context))
        val view2 = viewBinding.root
        val dialog = Dialog(requireContext(), R.style.FullScreenDialog)
        requireActivity().window.setGravity(Gravity.NO_GRAVITY)

        dialog.setContentView(view2)

        viewBinding.btnCloseDialog.setOnClickListener {
            dialog.cancel()
        }

        viewBinding.btnSaveNote.setOnClickListener {
            if (viewBinding.etAddNote.text.isEmpty()){
                viewBinding.etAddNote.error = "Перед сохранением заполните заметку"
            }else{
                val sdf = SimpleDateFormat("dd/M/yyyy")
                val currentDate = sdf.format(Date())
                val note = Note(viewBinding.etAddNote.text.toString(), currentDate)
                viewModel.insertNote(note)

                Toast.makeText(
                    requireContext(),
                    "Сохранено",
                    Toast.LENGTH_SHORT
                ).show()

                dialog.cancel()
            }
        }

        dialog.show()
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
    }
}