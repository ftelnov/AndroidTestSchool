package com.example.androidtestschool

import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

// Отношение валют к доллару
lateinit var rates: Map<String, Float>
const val EXCHANGE_RATE = 74

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        firstInputLayout.inputType =
            InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        secondInputLayout.inputType =
            InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        var firstChangedListener: TextWatcher? = null
        var secondChangedListener: TextWatcher? = null
        firstChangedListener = object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                val rubInput = p0.toString().toFloatOrNull()
                if (rubInput != null) {
                    val usdInput = rubInput / EXCHANGE_RATE
                    secondInputLayout.removeTextChangedListener(secondChangedListener)
                    secondInputLayout.setText(usdInput.toString())
                    secondInputLayout.addTextChangedListener(secondChangedListener)
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        }
        secondChangedListener = object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                val usdInput = p0.toString().toFloatOrNull()
                if (usdInput != null) {
                    val rubInput = usdInput * EXCHANGE_RATE
                    firstInputLayout.removeTextChangedListener(firstChangedListener)
                    firstInputLayout.setText(rubInput.toString())
                    firstInputLayout.addTextChangedListener(firstChangedListener)
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        }
        firstInputLayout.addTextChangedListener(firstChangedListener)
        secondInputLayout.addTextChangedListener(secondChangedListener)
        // Скопировать значение из окон результатов
        copyResults.setOnClickListener {
            copyToClipboard("Количество в ${firstCurrencySelect.selectedItem}: ${firstInputLayout.text} | Количество в ${secondCurrencySelect.selectedItem}: ${secondInputLayout.text}")
        }
    }
}