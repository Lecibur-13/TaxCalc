package com.rubicel.aranceles

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        supportActionBar?.hide()

        val calculateBtn = findViewById<Button>(R.id.calculateBtn);

        val resultCard = findViewById<CardView>(R.id.resultCard);
        resultCard.visibility = View.GONE;

        val result = findViewById<TextView>(R.id.value)
        var statusMessage = findViewById<TextView>(R.id.statusMessage)
        val statusColor = findViewById<ImageView>(R.id.statusColor)

        var valueIsEmpty = false //TODO validar si el campo viene vacio

        calculateBtn.setOnClickListener {
            var inputValue : EditText = findViewById<EditText>(R.id.customsValue)

            if (!valueIsEmpty){
                val customsValue = inputValue.text.toString().toInt()
                var customsValueResult: Int = 0
                var statusMessageResult: String = " "

                if (customsValue > 0) {

                    val addTaxes = customsValue * .16
                    val customsValueIVA = (customsValue + addTaxes).toInt()

                    if (customsValue < 1000) {
                        customsValueResult = customsValueIVA
                        statusMessageResult = "No aplican impuestos de importancion (solo IVA)"
                        statusColor.setBackgroundColor(ContextCompat.getColor(this, R.color.success));
                    }
                    if (customsValue > 20000) {
                        customsValueResult = (customsValueIVA + addTaxes).toInt()
                        statusMessageResult = "Se requiere un agente aduanal para su valoracion (se aplico IVA)"
                        statusColor.setBackgroundColor(ContextCompat.getColor(this, R.color.alert));
                    }
                    if (customsValue in 1001..19999) {
                        customsValueResult = (customsValueIVA + addTaxes).toInt()
                        statusMessageResult = "Se aplico el 16% del valor por impuestos aduanales (se aplico IVA)"
                        statusColor.setBackgroundColor(ContextCompat.getColor(this, R.color.warning));
                    }

                    result.text = "$ ${customsValueResult.toString()}"
                    statusMessage.text = statusMessageResult
                    resultCard.visibility = View.VISIBLE
                }
            } else {
                // TODO enviar mensaje de alerta
            }
        }

    }
}