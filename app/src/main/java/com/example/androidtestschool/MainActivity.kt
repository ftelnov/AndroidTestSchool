package com.example.androidtestschool

import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


const val EXCHANGE_RATE = 74 // Курс обмена рубля на доллар

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        exchangeRate.text = EXCHANGE_RATE.toString()
        rubInputLayout.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        usdInputLayout.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        var rubChangedListener: TextWatcher? = null
        var usdChangedListener: TextWatcher? = null
        rubChangedListener = object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                val rubInput = p0.toString().toFloatOrNull()
                if (rubInput != null) {
                    val usdInput = rubInput / EXCHANGE_RATE
                    usdInputLayout.removeTextChangedListener(usdChangedListener)
                    usdInputLayout.setText(usdInput.toString())
                    usdInputLayout.addTextChangedListener(usdChangedListener)
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        }
        usdChangedListener = object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                val usdInput = p0.toString().toFloatOrNull()
                if (usdInput != null) {
                    val rubInput = usdInput * EXCHANGE_RATE
                    rubInputLayout.removeTextChangedListener(rubChangedListener)
                    rubInputLayout.setText(rubInput.toString())
                    rubInputLayout.addTextChangedListener(rubChangedListener)
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        }
        rubInputLayout.addTextChangedListener(rubChangedListener)
        usdInputLayout.addTextChangedListener(usdChangedListener)
        // Скопировать значение из окон результатов
        copyResults.setOnClickListener {
            copyToClipboard("Количество в рублях: ${rubInputLayout.text} | Количество в долларах: ${usdInputLayout.text}")
        }
    }
}