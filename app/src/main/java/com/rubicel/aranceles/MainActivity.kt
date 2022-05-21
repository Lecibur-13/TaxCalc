package com.rubicel.aranceles

import android.icu.text.DecimalFormat
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import kotlin.math.roundToInt


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        supportActionBar?.hide()

        //Calculate Button
        val calculateBtn = findViewById<Button>(R.id.calculateBtn);

        //Card visibility
        val resultCard = findViewById<CardView>(R.id.resultCard);
        resultCard.visibility = View.GONE;

        //Result fields
        val result = findViewById<TextView>(R.id.value)
        val statusMessage = findViewById<TextView>(R.id.statusMessage)
        val shippingCostMessage = findViewById<TextView>(R.id.shippingCostMessage)
        val statusColor = findViewById<ImageView>(R.id.statusColor)

        var valueIsEmpty = false //TODO validar si el campo viene vacio

        calculateBtn.setOnClickListener {

            val formatter = DecimalFormat("#,###")
            calcCustomValue(formatter, result, statusMessage, statusColor, valueIsEmpty, resultCard)
            calcWeight(formatter, shippingCostMessage)
        }

    }

    private fun calcCustomValue(formatter: DecimalFormat, result: TextView, statusMessage: TextView, statusColor: ImageView, valueIsEmpty: Boolean, resultCard: CardView){
        var inputValue = findViewById<EditText>(R.id.customsValue)

        if (!valueIsEmpty){
            val customsValue = inputValue.text.toString().toInt()
            var customsValueResult: Int = 0
            var statusMessageResult: String = " "

            if (customsValue > 0) {

                val addTaxes = customsValue * .16
                val customsValueIVA = (customsValue + addTaxes).toInt()

                if (customsValue < 1000) {
                    customsValueResult = customsValueIVA
                    statusMessageResult = "No aplican impuestos de importanción (solo IVA)"
                    statusColor.setBackgroundColor(ContextCompat.getColor(this, R.color.success));
                }
                if (customsValue > 20000) {
                    customsValueResult = (customsValueIVA + addTaxes).toInt()
                    statusMessageResult = "Se requiere un agente aduanal para su valoración (se aplico IVA)"
                    statusColor.setBackgroundColor(ContextCompat.getColor(this, R.color.alert));
                }
                if (customsValue in 1000..20000) {
                    customsValueResult = (customsValueIVA + addTaxes).toInt()
                    statusMessageResult = "Se aplicó el 16% del valor por impuestos aduanales (se aplicó IVA)"
                    statusColor.setBackgroundColor(ContextCompat.getColor(this, R.color.warning));
                }

                result.text = "$ ${formatter.format(customsValueResult).toString()}"
                statusMessage.text = statusMessageResult
                resultCard.visibility = View.VISIBLE
            }
        } else {
            // TODO enviar mensaje de alerta
        }
    }

    private fun calcWeight(formatter: DecimalFormat,shippingCostMessage: TextView){
        var inputValue : EditText = findViewById(R.id.weightValue)
        var result : String = " "

        val customsValue = inputValue.text.toString().toInt()
        val baseShippingCost = 250.0
        val additionalTax = (baseShippingCost * 2.03)
        val taxValue = (baseShippingCost * (customsValue.toFloat() / 100) * 2)

        if (customsValue in 1..9 ){
            result = formatter.format(baseShippingCost).toString()
        }
        if (customsValue in 10..29){
            val newShippingCost = (baseShippingCost + taxValue).roundToInt()
            result = formatter.format(newShippingCost).toString()
        }
        if (customsValue >= 30){
            val newShippingCost = (baseShippingCost + taxValue + additionalTax).roundToInt()
            result = formatter.format(newShippingCost).toString()
        }

        shippingCostMessage.text = "+ $${result} de envío."
    }
}