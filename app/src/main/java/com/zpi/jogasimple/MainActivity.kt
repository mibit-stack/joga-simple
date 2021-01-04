package com.zpi.jogasimple

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainImageView.setOnClickListener{
            //Toast.makeText(this,"Kliknięto w kwiat lotosu...",Toast.LENGTH_SHORT).show()
            Snackbar.make(mainView,"Kliknięto w kwiat lotosu...",Snackbar.LENGTH_SHORT).show()
        }

        excerciseDayButton.setOnClickListener{
            val excerciseIntent = Intent(this,ExcerciseActivity::class.java)
            startActivity(excerciseIntent)
        }
        excerciseNightButton.setOnClickListener{
            val excerciseIntent2 = Intent(this,activity_ex_2_m::class.java)
            startActivity(excerciseIntent2)
        }
        aboutButton.setOnClickListener{
            val excerciseIntent3 = Intent(this,ExcerciseActivity::class.java)
            startActivity(excerciseIntent3)
        }
    }
}