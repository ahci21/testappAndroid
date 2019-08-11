package com.haras.tools

import android.net.TrafficStats
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.CompoundButton
import android.widget.Switch
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_cotizador.*
import java.math.RoundingMode
import java.text.DecimalFormat

class Cotizador : AppCompatActivity() {

    private var cantidadInicial : Float = 0f
    private var cantidadInicialAlterna : Float = 0f
    private var cantidadFinal : Float = 0f
    private var usd : Float = 1f
    private var iva : Float = 1.16f
    private var ganancia : Float = 1f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cotizador)


        editCantidad.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                cotizar()
            }
        })

        editPorcentaje.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                try {
                    ganancia = (editPorcentaje.text.toString().toFloat()/100f)+1f
                }catch (e:Throwable){
                    ganancia = 1f
                }

                cotizar()
            }
        })

        swUnit.setOnClickListener{
            calcularDolares()
        }

        swIVA.setOnClickListener{
            calcularIVA()
        }

        //Toast.makeText(this,"Ingrese un valor valido",Toast.LENGTH_LONG).show()


    }

    private fun cotizar(){



        try {
            cantidadInicial = editCantidad.text.toString().toFloat() * usd * iva

            if(swIVA.isChecked){
                cantidadInicialAlterna = cantidadInicial
                cantidadInicial = cantidadInicialAlterna * iva
                cantidadFinal = cantidadInicial * ganancia
                txtGananciaAlterna.setText("Ganancia si conservas IVA extra: $"+(cantidadFinal-cantidadInicialAlterna))
            }else{
                cantidadFinal = cantidadInicial * ganancia
                txtGananciaAlterna.setText("")
            }
        }catch (e:Throwable){
            cantidadInicial = 0f
            cantidadFinal = 0f
        }

        txtCompra.setText("$"+cantidadInicial)
        txtVenta.setText("$"+cantidadFinal)
        txtGanancia.setText("$"+(cantidadFinal-cantidadInicial))


    }

    private fun calcularDolares(){
        if (swUnit.isChecked){
            usd = 20f
            cotizar()
        }else if(!swUnit.isChecked){
            usd = 1f
            cotizar()
        }
    }

    private fun calcularIVA(){
        //if (swIVA.isChecked){
            //iva = 1.3456f
            cotizar()
        //}else if(!swIVA.isChecked){
        //    iva = 1.16f
        //    cotizar()
        //}
    }
}
