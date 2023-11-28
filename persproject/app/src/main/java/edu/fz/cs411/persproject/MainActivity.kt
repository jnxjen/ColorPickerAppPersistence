// Name: Jenny Vu
// CWID:  884427030
// Email: jvu00@csu.fullerton.edu
package edu.fz.cs411.persproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate called")
        setContentView(R.layout.activity_main)

        val fragmentColor = this.supportFragmentManager.findFragmentById(R.id.ColorContainer)
        if(fragmentColor==null){
            val fragment = ColorFragment()
            this.supportFragmentManager
                .beginTransaction()
                .add(R.id.ColorContainer, fragment)
                .commit()
        }

        val fragmentRGB = this.supportFragmentManager.findFragmentById(R.id.rgbDetailContainer)
        if(fragmentRGB==null){
            val fragment = RgbDetailFragment()
            this.supportFragmentManager
                .beginTransaction()
                .add(R.id.rgbDetailContainer,fragment)
                .commit()
        }

    }


}