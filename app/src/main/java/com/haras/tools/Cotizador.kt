package com.haras.tools

import android.net.TrafficStats
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import android.widget.Switch
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_cotizador.*
import java.math.RoundingMode
import java.text.DecimalFormat

class Cotizador : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cotizador)

        btnCalcular.setOnClickListener {
            var cotizacion : Float = 0f
            var ivaCompra: Float = 0f


            try {
                ivaCompra = editCantidad.text.toString().toFloat()*1.16f
            }catch (e: Throwable){
                Toast.makeText(this,"Ingrese un valor valido",Toast.LENGTH_LONG).show()
            }


            if(swIvaVenta.isChecked){
                var ivaVenta: Float = ivaCompra * 1.16f
                cotizacion = ivaVenta * ((editGanancia.text.toString().toFloat()/100)+1)
            }else{
                cotizacion = ivaCompra * ((editGanancia.text.toString().toFloat()/100)+1)
            }


            txtValorFinal.text = cotizacion.toString()
            var ganancia : Float = cotizacion - ivaCompra
            var stringG : String = "Ganancia en Pesos: \$" + ganancia.toString()
            txtGananciaFinal.text = stringG
        }

    }
}
