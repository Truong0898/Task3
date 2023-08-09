package com.example.task3

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.example.task3.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale
import java.util.logging.Logger

class MainActivity : ComponentActivity(), OnClickListener {

    private lateinit var binding: ActivityMainBinding

    private lateinit var edtNumber1: EditText
    private lateinit var edtNumber2: EditText
    private lateinit var btnSearch: Button
    private lateinit var tvSearch1: TextView
    private lateinit var tvTime: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        edtNumber1 = binding.edtNumber1
        edtNumber2 = binding.edtNumber2
        btnSearch = binding.btnSearch
        tvSearch1 = binding.tvSearch1
        tvTime = binding.tvTime



        btnSearch.setOnClickListener(this)

        edtNumber1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {

                edtNumber1.removeTextChangedListener(this)
                edtNumber1.setSelection(edtNumber1.text.length)
                edtNumber1.addTextChangedListener(this)
            }

        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnSearch -> {

                GlobalScope.launch(Dispatchers.Main) {
                    val currentTime1 = System.currentTimeMillis()
                    val string = edtNumber1.text.toString()
                    val list = string.split(",").map { it.toInt() }
                    val item = edtNumber2.text.toString().toInt()
                    val index = list.indexOf(item)
                    if (index < 0) {
                        tvSearch1.text = "Số $item không có trong chuỗi"
                    } else {
                        tvSearch1.text = "Số $item xuất hiện ở vị trí thứ $index"
                    }

                    withContext(Dispatchers.Default) {
                        delay(1000L)
                        Logger.getLogger("loading in ${Thread.currentThread().name}")
                    }
                    val currentTime2 = System.currentTimeMillis()
                    val time = currentTime2 - currentTime1
                    tvTime.text = "Thời gian xử lý: $time ms"
                }
            }

        }
    }


    fun convertEditText(editText: EditText): String {
        val string = editText.text.toString()
        val numberFormat = NumberFormat.getNumberInstance(Locale.ENGLISH)
        val format = DecimalFormat("#")
        if (!TextUtils.isEmpty(string)) {
            val textNb = string.replace(",".toRegex(), "")
            val number = textNb.toDouble()
            return format.format(number)
        }
        return ""
    }

}
