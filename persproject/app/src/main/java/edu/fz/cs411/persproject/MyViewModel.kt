// Name: Jenny Vu
// CWID:  884427030
// Email: jvu00@csu.fullerton.edu
package edu.fz.cs411.persproject

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class MyViewModel: ViewModel() {
    val MAX=100
    private var rSeekProg : Int = 0
    private var gSeekProg : Int = 0
    private var bSeekProg : Int = 0

    private var rValue : Float = 0f
    private var gValue : Float = 0f
    private var bValue : Float = 0f

    private var rSwitch : Boolean = false
    private var gSwitch : Boolean = false
    private var bSwitch : Boolean = false


    private var red=0
    private var green =0
    private var blue=0

    private val prefs = MyAppRepo.getRepo()

    private fun saveSwitchCur(curSwitch: Boolean, index: Int) {
        viewModelScope.launch{
            prefs.saveSwitch(curSwitch, index)
        }
    }

    private fun saveValue(curVal:Int, index: Int){
        viewModelScope.launch{
            prefs.saveValue(curVal, index)
        }
    }

    fun loadSwitches(){
        GlobalScope.launch{
            prefs.rSwitchLoad.collectLatest {
                rSwitch=it
            }
        }
        GlobalScope.launch{
            prefs.gSwitchLoad.collectLatest {
                gSwitch=it
            }

        }
        GlobalScope.launch{
            prefs.bSwitchLoad.collectLatest {
                bSwitch=it
            }
        }
    }
    fun loadValues(){
        GlobalScope.launch{
            prefs.rValLoad.collectLatest {
                rSeekProg = it
            }
        }
        GlobalScope.launch{
            prefs.gValLoad.collectLatest {
                gSeekProg = it
            }
        }
        GlobalScope.launch{
            prefs.bValLoad.collectLatest {
                bSeekProg = it
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("VIEWMODEL", "VM Cleared")
    }
    fun setRVal(r : Int) {
        rSeekProg = r
        rValue = r.toFloat()/MAX.toFloat()
        red=(rValue*255).roundToInt()
        saveValue(rSeekProg, 1)
    }
    fun setGVal(g : Int) {
        gSeekProg = g
        gValue = g.toFloat()/MAX.toFloat()
        green=(gValue*255).roundToInt()
        saveValue(gSeekProg,2)
    }
    fun setBVal(b : Int) {
        bSeekProg = b
        bValue = b.toFloat()/MAX.toFloat()
        blue=(bValue*255).roundToInt()
        saveValue(bSeekProg,3)
    }

    fun setRSwitchState( s : Boolean){
        rSwitch = s
        saveSwitchCur(rSwitch,1)
    }
    fun setGSwitchState( s : Boolean){
        gSwitch = s
        saveSwitchCur(gSwitch,2)
    }
    fun setBSwitchState( s : Boolean){
        bSwitch = s
        saveSwitchCur(bSwitch,3)
    }
    fun getRInt(): Int{
        return rSeekProg
    }
    fun getGInt(): Int{
        return gSeekProg
    }
    fun getBInt(): Int{
        return bSeekProg
    }
    fun getRFl(): Float{
        return rValue
    }
    fun getGFl(): Float{
        return gValue
    }
    fun getBFl(): Float{
        return bValue
    }
    fun getRSwitchState() : Boolean{
        return rSwitch
    }
    fun getGSwitchState() : Boolean{
        return gSwitch
    }
    fun getBSwitchState() : Boolean{
        return bSwitch
    }
    fun getR() : Int{
        return red
    }
    fun getG() : Int{
        return green
    }
    fun getB() : Int{
        return blue
    }

}


















