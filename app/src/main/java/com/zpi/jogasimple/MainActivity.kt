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

        excerciseDayButton.setOnClickListener{
            val excerciseIntent = Intent(this,ExcerciseActivity::class.java)
            excerciseIntent.putExtra("ExerciseSet","exerciseMorning")
            startActivity(excerciseIntent)
        }
        excerciseNightButton.setOnClickListener{
            val excerciseIntent = Intent(this,ExcerciseActivity::class.java)
            excerciseIntent.putExtra("ExerciseSet","exerciseNight")
            startActivity(excerciseIntent)
        }
        aboutButton.setOnClickListener{
            val excerciseIntent3 = Intent(this,aboutActivity::class.java)
            startActivity(excerciseIntent3)
        }
    }
}