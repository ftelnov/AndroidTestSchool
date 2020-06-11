package com.example.androidtestschool

import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.get
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.properties.Delegates


class MainActivity : AppCompatActivity() {
    // Отношение валют к доллару
    lateinit var rates: Map<String, Float>
    var firstSelectedRate: Float = 1.0f
    var secondSelectedRate: Float = 1.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        firstInputLayout.inputType =
            InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        secondInputLayout.inputType =
            InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        var firstChangedListener: TextWatcher? = null
        var secondChangedListener: TextWatcher? = null
        progressLayout.visibility = ConstraintLayout.VISIBLE
        Api.getApiClient().getCurrencies().enqueue(object : Callback<Bodies.GetCurrencyBody> {
            override fun onFailure(call: Call<Bodies.GetCurrencyBody>, t: Throwable) {
                Log.e("retrofit2_error", t.toString())
                showPrimaryToast("Что-то пошло не так")
            }

            override fun onResponse(
                call: Call<Bodies.GetCurrencyBody>,
                response: Response<Bodies.GetCurrencyBody>
            ) {
                rates = response.body()!!.rates
                firstSelectedRate = rates["EUR"]!!
                secondSelectedRate = rates["EUR"]!!
                // Когда получили результат - делаем остальной стафф с вьюшками
                // Листенер для первого поля ввода валюты
                firstChangedListener = object : TextWatcher {
                    override fun afterTextChanged(p0: Editable?) {
                        val firstInput = p0.toString().toFloatOrNull()
                        if (firstInput != null) {
                            val secondInput = firstInput / firstSelectedRate * secondSelectedRate
                            secondInputLayout.removeTextChangedListener(secondChangedListener)
                            secondInputLayout.setText(secondInput.toString())
                            secondInputLayout.addTextChangedListener(secondChangedListener)
                        }
                    }

                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    }

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    }
                }
                // Листенер для второго поля ввода валюты
                secondChangedListener = object : TextWatcher {
                    override fun afterTextChanged(p0: Editable?) {
                        val secondInput = p0.toString().toFloatOrNull()
                        if (secondInput != null) {
                            val firstInput = secondInput * firstSelectedRate / secondSelectedRate
                            firstInputLayout.removeTextChangedListener(firstChangedListener)
                            firstInputLayout.setText(firstInput.toString())
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
                firstCurrencySelect.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }

                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        firstSelectedRate = rates[parent!!.getItemAtPosition(position).toString()] ?: error("null_selector")
                    }

                }

                secondCurrencySelect.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }

                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        secondSelectedRate = rates[parent!!.getItemAtPosition(position).toString()] ?: error("null_selector")
                    }

                }
                // Скопировать значение из окон результатов
                copyResults.setOnClickListener {
                    copyToClipboard("Количество в ${firstCurrencySelect.selectedItem}: ${firstInputLayout.text} | Количество в ${secondCurrencySelect.selectedItem}: ${secondInputLayout.text}")
                }
                progressLayout.visibility = ConstraintLayout.GONE
            }

        })
    }
}