package com.example.calculatetest

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.example.calculatetest.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var mainHandler : Handler
    private var Validate: Boolean = false
    private var RecordTime: Int = 10
    private var secondsLeft:Int = 0
    private var list: MutableList<Int> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.txtRec.text = RecordTime.toString()
        binding.butStart.isEnabled = true
        ButEnabled(false)
        mainHandler = Handler(Looper.getMainLooper())
    }
    private val updateTextTask = object :Runnable{
        override fun run() {
            Second()
            mainHandler.postDelayed(this,1000)
        }
    }

    private fun Second(){
            secondsLeft++
            binding.txtSec.text = secondsLeft.toString()
    }

    //При нажатие на кнопку Старт запускаем программу, присваивая начальные значения
    fun butStart(view: View) {

        super.onResume()
        mainHandler.post(updateTextTask)

        binding.txtVarOne.text = VarValue().toString()
        binding.txtOperating.text = OperatValue()
        binding.txtVarTwo.text = VarValue().toString()

        Show()
        binding.butStart.isEnabled = false
        ButEnabled(true)
        binding.linearLayoutExample.setBackgroundColor(Color.argb(255,100,100,100))


    }

    private fun ButEnabled(Truth: Boolean){
        binding.butTrue.isEnabled = Truth
        binding.butFalse.isEnabled = Truth
    }

    private fun Show() {
        var VarOne: Double = binding.txtVarOne.text.toString().toDouble()
        var Operating: String = binding.txtOperating.text.toString()
        var VarTwo: Double = binding.txtVarTwo.text.toString().toDouble()

        var rnd: Int = Random.nextInt(2)
        Validate = if(rnd == 0) true else false
        when (Operating) {
            "+" -> binding.VarResult.text = (VarOne + VarTwo - rnd * 2).toInt().toString()
            "-" -> binding.VarResult.text = (VarOne - VarTwo + rnd * 2).toInt().toString()
            "*" -> binding.VarResult.text = (VarOne * VarTwo - rnd * 2).toInt().toString()
            "/" -> {
                     var n: Double = VarOne / VarTwo - rnd / 10.0
                     binding.VarResult.text = String.format("%.2f", n)
            }
        }
    }

    //Создаем функции,которые
    private fun VarValue():Int = (10..99).random() //Возвращает рандомное двухзначное число
    private fun OperatValue():String = arrayOf("+","-","*","/").random()//Возвращает рандомный операцию

    fun True(view: View){
        binding.txtAllCount.text = (binding.txtAllCount.text.toString().toInt() + 1).toString()
        if(Validate){
            binding.linearLayoutExample.setBackgroundColor(Color.argb(255,0,255,0))
            binding.txtCountTrue.text = (binding.txtCountTrue.text.toString().toInt() + 1).toString()
            RecordSecond()
        }else {
            binding.linearLayoutExample.setBackgroundColor(Color.argb(255, 255, 0, 0))
            binding.txtCountFalse.text =  (binding.txtCountFalse.text.toString().toInt() + 1).toString()
        }

        Result()


    }

    fun False(view: View){

        binding.txtAllCount.text = (binding.txtAllCount.text.toString().toInt() + 1).toString()
        if(!Validate){
            binding.linearLayoutExample.setBackgroundColor(Color.argb(255,0,255,0))
            binding.txtCountTrue.text = (binding.txtCountTrue.text.toString().toInt() + 1).toString()
            RecordSecond()
        }else {
            binding.linearLayoutExample.setBackgroundColor(Color.argb(255, 255, 0, 0))
            binding.txtCountFalse.text =  (binding.txtCountFalse.text.toString().toInt() + 1).toString()
        }
       Result()

    }

    private fun Result(){
        var AllCount: Int = binding.txtAllCount.text.toString().toInt()
        var CountTrue:Int = binding.txtCountTrue.text.toString().toInt()
        binding.txtProcentResult.text = "${String.format("%.2f", (CountTrue * 100.0 / AllCount))}%"


        binding.butStart.isEnabled = true
        ButEnabled(false)

        list.add(secondsLeft)
        var avg = 0
        for(i in list){
            avg+=i
        }
        binding.txtAvg.text = (avg/list.size).toString()

        secondsLeft = 0
        binding.txtSec.text = secondsLeft.toString()

        super.onPause()
        mainHandler.removeCallbacks(updateTextTask)

    }
    private fun RecordSecond(){
        if(RecordTime > secondsLeft) {
            RecordTime = secondsLeft
            binding.txtRec.text = RecordTime.toString()
        }
    }

}