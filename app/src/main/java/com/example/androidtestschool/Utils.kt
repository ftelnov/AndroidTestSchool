package com.example.androidtestschool

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.get
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.primary_toast.view.*


// Функция быстрого вызова тоста
fun Context.showPrimaryToast(message: CharSequence) {
    val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT) // Получаем тост через статик метод
    val toastContainer = LinearLayout(this) // Получаем контейнер из контекста
    val inflater: LayoutInflater =
        this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater // Получаем инфлэйтер через систему
    inflater.inflate(R.layout.primary_toast, toastContainer) // Вставляем тост в лайаут контекста
    toastContainer[0].message.text = message // ставим ему свой текст
    toast.view = toastContainer // и устанавливаем вьюшкой тоста собранный лайаут
    toast.show() // показываем тост
}

// Утилса для быстрого спрятывания клавиатуры, вызывается на вьюшке
fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

// Функция копирования в буфер обмена с оповещением
fun Context.copyToClipboard(message: CharSequence) {
    val clipboard =
        getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    clipboard.setPrimaryClip(ClipData.newPlainText("rate", message))
    showPrimaryToast(resources.getString(R.string.copied))
}