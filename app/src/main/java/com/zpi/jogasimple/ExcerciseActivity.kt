package com.zpi.jogasimple

import android.content.Intent
import android.graphics.Color
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_excercise.*
import kotlinx.android.synthetic.main.activity_main.mainView
import kotlinx.android.synthetic.main.excercise_dialog_layout.*
import java.text.SimpleDateFormat
import java.util.*


class Exercise(var exImg: Int = 0,
               var exDesc:String = "",
               var exTime:Int = 30)

class ExcerciseActivity : AppCompatActivity() {
    var exerciseTime = 30;
    var exerciseDescription = "Przykladowy opis"
    var exerciseImage = 0;

    var currentExcercise: Exercise? = null;
    var timerIsStarted = false

    var currentExcerciseNo = 0

    val exerciseMorning = arrayOf(
        Exercise(R.drawable.ex_m_1, "Pierwsze cwiczenie",5),
        Exercise(R.drawable.ex_m_2, "Drugie cwiczenie",5),
        Exercise(R.drawable.ex_m_3,"Trzecie cwiczenie",5),
        Exercise(R.drawable.ex_m_4,"Czwarte cwiczenie",5),
        Exercise(R.drawable.ex_m_5,"Piate cwiczenie",5))

    val exerciseNight = arrayOf(
        Exercise(R.drawable.ex_n_1, "Pierwsze cwiczenie",5),
        Exercise(R.drawable.ex_n_2, "To ćwiczenie nazywane jako Kwiat Lotosu, jest symbolem jogi. Ciało się odpręża, a umysł odpoczywa.",5),
        Exercise(R.drawable.ex_n_3,"ddddd",5),
        Exercise(R.drawable.ex_n_4,"xxxxx",5),
        Exercise(R.drawable.ex_n_5,"asdfassafd",5))

    var currentExcerciseSet = exerciseMorning

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val exerciseLayoutView = findViewById(R.id.mainView) as ImageView
//        exerciseLayoutView.setBackgroundColor(0x00FF00)
        setContentView(R.layout.activity_excercise)




//        val dialogExcerciseView = dialogView.findViewById(R.id.dialogExcerciseImageView) as ImageView
//        dialogExcerciseView.setImageResource(exerciseImage)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val setName = intent.extras?.get("ExerciseSet") as String

        // Changing layout color by set name

        val activityMain = findViewById(R.id.mainView) as ConstraintLayout


        setName.let {
            if(it.contains("Morning")) {
                currentExcerciseSet = exerciseMorning
                activityMain.setBackgroundColor(Color.parseColor("#fee276"));
            }

            else {
                currentExcerciseSet = exerciseNight
                activityMain.setBackgroundColor(Color.parseColor("#7799ff"));
//                activityMain.setBackgroundResource(R.drawable.night_set_gradient)

            }

        }

        loadExercise(currentExcerciseSet[currentExcerciseNo])


        startButton.setOnClickListener{

            if(!timerIsStarted) {
                startExcercise()
            }
        }
    }

    fun loadExercise(exercise: Exercise){
        currentExcercise = exercise
        exerciseImage = exercise.exImg
        exerciseTime = exercise.exTime
        exerciseDescription  = exercise.exDesc

        var underNineTxt = ""
        if (exerciseTime<10) {underNineTxt= "0"}
        timerView.setText("00:${underNineTxt}${exerciseTime}")

        imageView3.setImageResource(exerciseImage)

    }

    fun startExcercise(){
        val thread = Thread {
            var number = exerciseTime
            timerIsStarted = true

            for (i in 0..exerciseTime) {
                var underNineTxt = "";
                if (number<10) {underNineTxt= "0"}
                runOnUiThread {
                    timerView.setText("00:${underNineTxt}${number}")
                }
                Thread.sleep(1000)
                number--
            }

            runOnUiThread{
                val toneG = ToneGenerator(AudioManager.STREAM_ALARM, 100)
                toneG.startTone(ToneGenerator.TONE_CDMA_ABBR_ALERT,500)

                currentExcerciseNo++

                if(currentExcerciseNo >= currentExcerciseSet.size)
                    currentExcerciseNo = 0

                loadExercise(currentExcerciseSet[currentExcerciseNo])
                openStartDialog()

                //reload()
            }

            timerIsStarted = false

        }

        thread.start()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        /*if(item.itemId==android.R.id.home)
            finish()
        */

        when(item.itemId){
            android.R.id.home->finish()
            R.id.id_info->{
                openStartDialog()
                return true
            }
            R.id.id_sound->{
                Snackbar.make(mainView, "Kliknięto menu stop...", Snackbar.LENGTH_SHORT).show()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    fun openStartDialog(){
        val dialogBuilder = AlertDialog.Builder(this)
        // set exercise Dialog image
//        setContentView(R.layout.excercise_dialog_layout)
//        imageView2.setImageResource(exerciseImage)

        val dialogView = layoutInflater.inflate(R.layout.excercise_dialog_layout,null)

        val dialogExcerciseView = dialogView.findViewById(R.id.dialogExcerciseImageView) as ImageView
        dialogExcerciseView.setImageResource(exerciseImage)

        dialogBuilder.setMessage(currentExcercise?.exDesc)

        dialogBuilder.setView(dialogView)

        dialogBuilder.setPositiveButton("OK"){
                dialog, id ->
        }
        val dialog = dialogBuilder.create()

        dialog.show()
    }
// reload Activity?
    fun reload() {
        finish()
        startActivity(getIntent());
    }


}