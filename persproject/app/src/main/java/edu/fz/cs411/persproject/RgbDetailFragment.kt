// Name: Jenny Vu
// CWID:  884427030
// Email: jvu00@csu.fullerton.edu
package edu.fz.cs411.persproject

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.Switch
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider


class RgbDetailFragment: Fragment() {
    private lateinit var colorViewModel: MyViewModel

    private lateinit var rSwitch: Switch
    private lateinit var gSwitch: Switch
    private lateinit var bSwitch: Switch

    private lateinit var rSeekBar: SeekBar
    private lateinit var gSeekBar: SeekBar
    private lateinit var bSeekBar: SeekBar

    private lateinit var rValue: EditText
    private lateinit var gValue:EditText
    private lateinit var bValue:EditText

    private lateinit var resetB : Button

    override fun onCreate(mysavedInstanceState: Bundle?) {
        super.onCreate(mysavedInstanceState)
        Log.d("RGBFRAGkt", "onCreate called")

        //Initialize Datastore Repo
        MyAppRepo.initialize(requireContext())
        colorViewModel=ViewModelProvider(this)[MyViewModel::class.java]
        colorViewModel.loadSwitches()
        colorViewModel.loadValues()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(savedInstanceState!=null){
            var test = savedInstanceState.toString()
        }
        val view = inflater.inflate(R.layout.fragment_rgb_detail, container, false)
        //need to findViewById all the declared assets above
        this.rSwitch=view.findViewById(R.id.redSwitch)
        this.gSwitch=view.findViewById(R.id.greenSwitch)
        this.bSwitch=view.findViewById(R.id.blueSwitch)

        this.rSeekBar=view.findViewById(R.id.redseekBar)
        this.gSeekBar=view.findViewById(R.id.greenseekBar)
        this.bSeekBar=view.findViewById(R.id.blueseekBar)

        this.rValue=view.findViewById(R.id.redVal)
        this.gValue=view.findViewById(R.id.greenVal)
        this.bValue=view.findViewById(R.id.blueVal)

        this.resetB=view.findViewById(R.id.ResetButton)

        //set initial Switch States
        rSwitch.isChecked=colorViewModel.getRSwitchState()
        gSwitch.isChecked=colorViewModel.getGSwitchState()
        bSwitch.isChecked=colorViewModel.getBSwitchState()
        //set initial seekbar vals
        rSeekBar.progress = colorViewModel.getRInt()
        gSeekBar.progress = colorViewModel.getGInt()
        bSeekBar.progress = colorViewModel.getBInt()
        //function to load color values from datastore
        setValues()
        //function to display color in other fragment
        sendToColorFrag()
        //function to set locks based on switch vals from data store
        setLocks()


        this.rSwitch.setOnClickListener{
            colorViewModel.setRSwitchState(rSwitch.isChecked)
            rSeekBar.isEnabled = colorViewModel.getRSwitchState()
            rValue.isEnabled = colorViewModel.getRSwitchState()
            sendToColorFrag()

        }
        this.gSwitch.setOnClickListener{
            colorViewModel.setGSwitchState(gSwitch.isChecked)
            gSeekBar.isEnabled = colorViewModel.getGSwitchState()
            gValue.isEnabled = colorViewModel.getGSwitchState()
            sendToColorFrag()
        }
        this.bSwitch.setOnClickListener{
            colorViewModel.setBSwitchState(bSwitch.isChecked)
            bSeekBar.isEnabled = colorViewModel.getBSwitchState()
            bValue.isEnabled = colorViewModel.getBSwitchState()
            sendToColorFrag()
        }
        rSeekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(sb: SeekBar?, p1: Int, p2: Boolean) {
                colorViewModel.setRVal(rSeekBar.progress)
                rValue.setText(colorViewModel.getRFl().toString())
                sendToColorFrag()
            }
            override fun onStartTrackingTouch(p0: SeekBar?) { }
            override fun onStopTrackingTouch(p0: SeekBar?) { }
        } )
        gSeekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(sb: SeekBar?, p1: Int, p2: Boolean) {
                colorViewModel.setGVal(gSeekBar.progress)
                gValue.setText(colorViewModel.getGFl().toString())
                sendToColorFrag()
            }
            override fun onStartTrackingTouch(p0: SeekBar?) { }
            override fun onStopTrackingTouch(p0: SeekBar?) { }
        } )
        bSeekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(sb: SeekBar?, p1: Int, p2: Boolean) {
                colorViewModel.setBVal(bSeekBar.progress)
                bValue.setText(colorViewModel.getBFl().toString())
                sendToColorFrag()
            }
            override fun onStartTrackingTouch(p0: SeekBar?) { }
            override fun onStopTrackingTouch(p0: SeekBar?) { }
        } )

        rValue.setOnClickListener {
            var currProg : Float = ((rValue.text).toString().toFloat())*rSeekBar.max
            rSeekBar.progress=currProg.toInt()
            colorViewModel.setRVal(rSeekBar.progress)
            sendToColorFrag()
        }
        gValue.setOnClickListener {
            var currProg : Float = ((gValue.text).toString().toFloat())*gSeekBar.max
            gSeekBar.progress=currProg.toInt()
            colorViewModel.setGVal(gSeekBar.progress)
            sendToColorFrag()
        }
        bValue.setOnClickListener {
            var currProg : Float = ((bValue.text).toString().toFloat())*bSeekBar.max
            bSeekBar.progress=currProg.toInt()
            colorViewModel.setBVal(bSeekBar.progress)
            sendToColorFrag()
        }

        resetB.setOnClickListener(){
            rSwitch.isChecked=true
            gSwitch.isChecked=true
            bSwitch.isChecked=true
            rSeekBar.progress=0
            gSeekBar.progress=0
            bSeekBar.progress=0
            colorViewModel.setRSwitchState(rSwitch.isChecked)
            colorViewModel.setGSwitchState(gSwitch.isChecked)
            colorViewModel.setBSwitchState(bSwitch.isChecked)
            //Reset values, lock states, and color
            setValues()
            setLocks()
            sendToColorFrag()
        }

        return view
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("R_SWITCH", rSwitch.isChecked)
        outState.putBoolean("G_SWITCH", gSwitch.isChecked)
        outState.putBoolean("B_SWITCH", bSwitch.isChecked)
        outState.putInt("R_VAL",colorViewModel.getRInt())
        outState.putInt("G_VAL",colorViewModel.getGInt())
        outState.putInt("B_VAL",colorViewModel.getBInt())
    }
    override fun onDestroy() {
      super.onDestroy()
      Log.d("ColorFrag", "onDestroy called")
    }

    private fun sendToColorFrag(){
        val colorUpdate = Bundle()
        colorUpdate.putInt("RED", colorViewModel.getR())
        colorUpdate.putInt("GREEN", colorViewModel.getG())
        colorUpdate.putInt("BLUE", colorViewModel.getB())
        if(!colorViewModel.getRSwitchState()) colorUpdate.putInt("RED", 0)
        if(!colorViewModel.getGSwitchState()) colorUpdate.putInt("GREEN", 0)
        if(!colorViewModel.getBSwitchState()) colorUpdate.putInt("BLUE", 0)

        val toFragment= ColorFragment()
        toFragment.arguments=colorUpdate
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.ColorContainer,toFragment)
            .commit()
    }
    private fun setLocks(){
        rSeekBar.isEnabled = colorViewModel.getRSwitchState()
        rValue.isEnabled = colorViewModel.getRSwitchState()
        gSeekBar.isEnabled = colorViewModel.getGSwitchState()
        gValue.isEnabled = colorViewModel.getGSwitchState()
        bSeekBar.isEnabled = colorViewModel.getBSwitchState()
        bValue.isEnabled = colorViewModel.getBSwitchState()
    }
    private fun setValues(){
        colorViewModel.setRVal(rSeekBar.progress)
        colorViewModel.setGVal(gSeekBar.progress)
        colorViewModel.setBVal(bSeekBar.progress)

        rValue.setText(colorViewModel.getRFl().toString())
        gValue.setText(colorViewModel.getGFl().toString())
        bValue.setText(colorViewModel.getBFl().toString())
    }
}