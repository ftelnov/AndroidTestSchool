package com.example.androidtestschool

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

const val EXCHANGE_RATE = 74 // Курс обмена рубля на доллар

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Скопировать значение из окна результата
        resultAmount.setOnClickListener {
            copyToClipboard(resultAmount.text)
        }
        transferButton.setOnClickListener {
            transferButton.hideKeyboard() // прячем клаву
            resultAmount.visibility = TextView.GONE // прячем результирующее значение
            val userInput = rubInputLayout.text.toString() // берем из поля данные
            val finalFloat = userInput.toFloatOrNull() // приводим к флоату
            if (finalFloat == null) {
                // Если не получилось, пишем пользователю что неправильный формат ввода
                this@MainActivity.showPrimaryToast(resources.getString(R.string.wrong_format_warning))
            } else {
                val result = finalFloat / 74 // получаем значение в долларах
                resultAmount.text = result.toString() // приводим значение к строковому
                resultAmount.visibility = TextView.VISIBLE // Показываем поле с результатом
            }
        }
    }
}