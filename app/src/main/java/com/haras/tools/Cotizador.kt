package com.haras.tools

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import kotlinx.android.synthetic.main.activity_cotizador.*

class Cotizador : AppCompatActivity() {

    private var precioConImpuestosExtra : Float = 0f
    private var precioCompra : Float = 0f
    private var precioVenta : Float = 0f
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
            precioConImpuestosExtra = editCantidad.text.toString().toFloat() * usd * iva
            precioCompra = precioConImpuestosExtra

            if(swIVA.isChecked){

                precioConImpuestosExtra = precioCompra * iva
                precioVenta = precioConImpuestosExtra * ganancia
                txtGananciaAlterna.setText("Ganancia si conservas IVA extra: $"+(precioVenta-precioCompra))
                txtHacienda.setText("$"+(precioConImpuestosExtra-precioCompra)+" para Hacienda")
            }else{
                precioVenta = precioConImpuestosExtra * ganancia
                txtGananciaAlterna.setText("")
                txtHacienda.setText("")
            }
        }catch (e:Throwable){
            precioConImpuestosExtra = 0f
            precioCompra = 0f
            precioVenta = 0f
        }

        txtCompra.setText("$"+precioCompra)
        txtVenta.setText("$"+precioVenta)
        txtGanancia.setText("$"+(precioVenta-precioConImpuestosExtra))


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
