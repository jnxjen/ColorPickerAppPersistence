// Name: Jenny Vu
// CWID:  884427030
// Email: jvu00@csu.fullerton.edu
package edu.fz.cs411.persproject

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class ColorFragment:Fragment() {
        private lateinit var colorBox: TextView

        private var red : Int= 0
        private var green : Int = 0
        private var blue : Int= 0
    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
        }
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val view = inflater.inflate(R.layout.fragment_color, container, false)
            val sentBund = this.arguments

            var redS = sentBund?.getInt("RED")
            var greenS =sentBund?.getInt("GREEN")
            var blueS = sentBund?.getInt("BLUE")

            if(redS != null){
                red=redS.toInt()
            }
            if(greenS != null){
                green=greenS.toInt()
            }
            if(blueS is Int){
               blue=blueS.toInt()
            }
            colorBox = view.findViewById(R.id.ColorBox)
            colorBox.setBackgroundColor(Color.rgb(red, green, blue))

            return view
        }
    override fun onDestroy() {
        super.onDestroy()
        Log.d("ColorFrag", "onDestroy called")
    }
}