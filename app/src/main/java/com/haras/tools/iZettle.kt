package com.haras.tools

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.IntegerRes
import kotlinx.android.synthetic.main.activity_cotizador.*
import kotlinx.android.synthetic.main.activity_i_zettle.*

class iZettle : AppCompatActivity() {


    private var precioOrigial : Float = 0f

    private var precioConComision : Float = 0f
    private var comisionBasePorcentaje : Float = .035f
    private var comisionBase : Float = 0f

    private var msi = 0
    private var comisionMSI : Float = 0f
    private var ajusteOtras : Float = 0.01f

    private var ajustePorteccion : Float = 1.05f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_i_zettle)

        try{
            editCompra.setText(intent.getStringExtra("precioVenta"))
            calcular()
        }catch (e:Throwable){

        }


        editCompra.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {

                calcular()
            }
        })

        swComision.setOnClickListener{
            asignarComision()
        }

        swBBVA.setOnClickListener{
            asignarTarjeta()
        }

        spMSI.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                asignarMeses()
            }

        }

    }

    private fun calcular(){



        try{
            precioOrigial = editCompra.text.toString().toFloat()
            comisionBase =  ((precioOrigial * comisionBasePorcentaje)  + (precioOrigial * comisionMSI))* 1.16f
            precioConComision = precioOrigial + comisionBase

        }catch (e:Throwable){
            comisionBase = 0f
            precioConComision = 0f
        }

        txtResultado.setText("$%.2f".format(precioConComision*ajustePorteccion))
    }

    private fun asignarComision(){
        if (swComision.isChecked){
            comisionBasePorcentaje = 0.037f
        }else{
            comisionBasePorcentaje = 0.035f
        }

        calcular()
    }

    private fun asignarTarjeta(){
        if (swBBVA.isChecked){
            ajusteOtras = 0f
        }else{
            ajusteOtras = 0.01f
        }

        asignarMeses()
    }

    private fun asignarMeses(){

        msi = spMSI.selectedItemPosition

        when(msi){
            0 -> comisionMSI = 0f
            1 -> comisionMSI = 0.04f
            2 -> comisionMSI = 0.07f
            3 -> comisionMSI = 0.10f - ajusteOtras
            4 -> comisionMSI = 0.13f - ajusteOtras

        }

        calcular()
    }
}
