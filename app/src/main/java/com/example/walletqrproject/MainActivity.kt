package com.example.walletqrproject

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.walletqrproject.databinding.ActivityMainBinding
import com.example.walletqrproject.databinding.AddMinusMoneyDialogBinding
import com.example.walletqrproject.ui.fragments.*
import com.example.walletqrproject.ui.model.MoneyData
import com.example.walletqrproject.ui.viewmodels.MainActivitySaveMoneyViewModel
import com.example.walletqrproject.ui.viewmodels.ShowCreditCardInfoViewModel
import com.google.android.material.navigation.NavigationView
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivitySaveMoneyViewModel
    private lateinit var viewModel2: ShowCreditCardInfoViewModel

    var last = 0
    var lastMoney = 0
    var lastMoneyCard1 = 0
    var lastMoneyCard2 = 0

    val myCalendar: Calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initViews()

        viewModel.getAccountInfo("Наличные").observe(this, androidx.lifecycle.Observer { initial ->
            lastMoney = initial ?: 0
        })

        viewModel.getAccountInfo("Карта 1").observe(this, androidx.lifecycle.Observer { initial ->
            lastMoneyCard1 = initial ?: 0
        })

        viewModel.getAccountInfo("Карта 2").observe(this, androidx.lifecycle.Observer { initial ->
            lastMoneyCard2 = initial ?: 0
        })

        binding.fab.setOnClickListener {
            val viewBinding = AddMinusMoneyDialogBinding.inflate(layoutInflater)
            val view2 = viewBinding.root
            val dialog = Dialog(this, R.style.FullScreenDialog)
            window.setGravity(Gravity.NO_GRAVITY)

            dialog.setContentView(view2)

            var inorout = "plus"
            viewBinding.toggleButtonGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
                if (isChecked) {
                    when (checkedId) {
                        R.id.btnIncome -> {
                            viewBinding.expenseOrIncome.text = "+"
                            inorout = "plus"
                        }
                        R.id.btnExpense -> {
                            viewBinding.expenseOrIncome.text = "-"
                            inorout = "minus"
                        }
                    }
                }
            }

            viewBinding.backToCourse.setOnClickListener {
                dialog.cancel()
            }

            val items1 = listOf("Наличные", "Карта 1")
            val items = listOf("Наличные", "Карта 1", "Карта 2")

            val date =
                OnDateSetListener { _, year, month, day ->
                    myCalendar.set(Calendar.YEAR, year)
                    myCalendar.set(Calendar.MONTH, month)
                    myCalendar.set(Calendar.DAY_OF_MONTH, day)
                    updateLabel(viewBinding.textInputDate)
                }

            viewBinding.textInputDate.setOnClickListener {
                DatePickerDialog(
                    this@MainActivity,
                    date,
                    myCalendar[Calendar.YEAR],
                    myCalendar[Calendar.MONTH],
                    myCalendar[Calendar.DAY_OF_MONTH]
                ).show()
            }


//            val today = MaterialDatePicker.todayInUtcMilliseconds()
//            val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
//
//            calendar.timeInMillis = today
//            calendar[Calendar.MONTH] = Calendar.JANUARY
//            val janThisYear = calendar.timeInMillis
//
//            calendar.timeInMillis = today
//            calendar[Calendar.MONTH] = Calendar.DECEMBER
//            val decThisYear = calendar.timeInMillis
//
//// Build constraints.
//            val constraintsBuilder =
//                CalendarConstraints.Builder()
//                    .setStart(janThisYear)
//                    .setEnd(decThisYear)

            viewModel2.readAllData.observe(this, androidx.lifecycle.Observer { credit ->
                if (credit.isEmpty()) {
                    viewBinding.textInputAccount.visibility = View.GONE
                } else if (credit.size == 1) {
                    val adapter = ArrayAdapter(
                        this,
                        androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                        items1
                    )
                    viewBinding.textInputAccount.visibility = View.VISIBLE
                    viewBinding.autoComplete.setAdapter(adapter)
                    adapter.notifyDataSetChanged()
                } else {
                    val adapter = ArrayAdapter(
                        this,
                        androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                        items
                    )
                    viewBinding.textInputAccount.visibility = View.VISIBLE
                    viewBinding.autoComplete.setAdapter(adapter)
                    adapter.notifyDataSetChanged()
                }
            })

            viewBinding.btnSave.setOnClickListener {
                when {
                    viewBinding.editText.text.isEmpty() -> {
                        viewBinding.editText.error = "Заполните цифры"
                    }
                    viewBinding.textInputCategory.text?.isEmpty() == true -> {
                        viewBinding.textInputCategory.error = "Заполните категорию"
                    }
                    else -> {
                        saveToLocalDb(
                            viewBinding.editText.text.toString(),
                            inorout,
                            viewBinding.autoComplete.text.toString(),
                            viewBinding.textInputCategory.text.toString(),
                            viewBinding.textInputDate.text.toString()
                        )
                        dialog.cancel()
                    }
                }
            }

            dialog.show()
        }
    }

    override fun onBackPressed() {
        val fragment =
            this.supportFragmentManager.findFragmentById(R.id.fragment_container)
        (fragment as? IOnBackPressed)?.onBackPressed()?.not()?.let {
            super.onBackPressed()
        }
    }

    private fun updateLabel(textInputDate: TextInputEditText) {
        val myFormat = "MM/dd/yy"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
        textInputDate.setText(dateFormat.format(myCalendar.getTime()))
    }

    private fun saveToLocalDb(
        money: String,
        inorout: String,
        autoComplete: String,
        textInputStr: String,
        textInputDate: String
    ) {
        var pocket = "Наличные"

        if (!autoComplete.isEmpty()) {
            pocket = autoComplete
        }

        when (pocket) {
            "Наличные" -> {
                if (inorout == "plus") {
                    last = lastMoney + money.toInt()
                } else if (inorout == "minus") {
                    last = lastMoney - money.toInt()
                }
            }
            "Карта 1" -> {
                if (inorout == "plus") {
                    last = lastMoneyCard1 + money.toInt()
                } else if (inorout == "minus") {
                    last = lastMoneyCard1 - money.toInt()
                }
            }
            "Карта 2" -> {
                if (inorout == "plus") {
                    last = lastMoneyCard2 + money.toInt()
                } else if (inorout == "minus") {
                    last = lastMoneyCard2 - money.toInt()
                }
            }
        }


//        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm")
//        val currentDate = sdf.format(Date())
        val moneyData = MoneyData(money.toInt(), inorout, textInputDate, last, textInputStr, pocket)
//        val accountCard2 = AccountCard2(money.toInt(), inorout, currentDate, last, textInputStr, pocket)
//        val accountCard3 = AccountCard3(money.toInt(), inorout, currentDate, last, textInputStr, pocket)

        Log.i("TAG", moneyData.toString())

        viewModel.insertMoney(moneyData)
//        Log.i("TAG", accountCard2.toString())
//        Log.i("TAG", accountCard3.toString())

//        if (pocket == "Наличные"){
//            viewModel.insertMoney(moneyData)
//        }
//        else if(pocket == "Карта 1"){
//            viewModel.insertMoneyCard1(moneyData)
//        }else if(pocket == "Карта 2"){
//            viewModel.insertMoneyCard2(moneyData)
//        }

        Toast.makeText(
            this,
            "Сохранено",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun initViews() {
        viewModel = ViewModelProvider(this)[MainActivitySaveMoneyViewModel::class.java]
        viewModel2 = ViewModelProvider(this)[ShowCreditCardInfoViewModel::class.java]

        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.layout.toolbar,
            R.string.open,
            R.string.close
        )
        toggle.isDrawerIndicatorEnabled = true
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navMenu.setNavigationItemSelectedListener(this)

        setToolbarTitle(getString(R.string.homepage))
        changeFragment(HomePageFragment())
    }

    private fun setToolbarTitle(s: String) {
        binding.layout.toolbar.title = s
    }

    private fun changeFragment(frag: Fragment) {
        val fragment = supportFragmentManager.beginTransaction()
        fragment.replace(R.id.fragment_container, frag).commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        when (item.itemId) {
            R.id.nav_home -> {
                setToolbarTitle(getString(R.string.homepage))
                changeFragment(HomePageFragment())
                binding.fab.visibility = View.VISIBLE
            }
            R.id.nav_records -> {
                setToolbarTitle(getString(R.string.recordspage))
                changeFragment(RecordsPageFragment())
                binding.fab.visibility = View.VISIBLE
            }
            R.id.nav_note -> {
                setToolbarTitle(getString(R.string.notepage))
                changeFragment(NotePageFragment())
                binding.fab.visibility = View.GONE
            }
            R.id.nav_credit_card -> {
                setToolbarTitle(getString(R.string.card))
                changeFragment(CreditCardInfoPageFragment())
                binding.fab.visibility = View.GONE
            }
            R.id.nav_qr_scanner -> {
                setToolbarTitle(getString(R.string.qrscannerpage))
                changeFragment(QrScannerPageFragment())
                binding.fab.visibility = View.GONE
            }
            R.id.nav_about_project -> {
                setToolbarTitle(getString(R.string.aboutproject))
                changeFragment(AboutProjectFragment())
                binding.fab.visibility = View.GONE
            }
        }
        return true
    }
}