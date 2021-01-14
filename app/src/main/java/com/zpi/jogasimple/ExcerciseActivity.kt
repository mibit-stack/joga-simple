package com.zpi.jogasimple

import android.content.Intent
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_excercise)

        val exerciseMorning = arrayOf(
            Exercise(R.drawable.ex_m_1, "Pierwsze cwiczenie",10),
            Exercise(R.drawable.ex_m_2, "Drugie cwiczenie",10),
            Exercise(R.drawable.ex_m_3,"Trzecie cwiczenie",10),
            Exercise(R.drawable.ex_m_4,"Czwarte cwiczenie",10),
            Exercise(R.drawable.ex_m_5,"Piate cwiczenie",10))

        val exerciseNight = arrayOf(
            Exercise(R.drawable.ex_n_1, "Pierwsze cwiczenie",20),
            Exercise(R.drawable.ex_n_2, "To ćwiczenie nazywane jako Kwiat Lotosu, jest symbolem jogi. Ciało się odpręża, a umysł odpoczywa.",50),
            Exercise(R.drawable.ex_n_3,"ddddd",30),
            Exercise(R.drawable.ex_n_4,"xxxxx",30),
            Exercise(R.drawable.ex_n_5,"asdfassafd",30))

        supportActionBar?.setDisplayHomeAsUpEnabled(true);


        val timeFormat = SimpleDateFormat("mm:ss", Locale.ENGLISH)
        var startTime : Long = 0;

        loadExercise(exerciseMorning[2])

        var underNineTxt = ""
        if (exerciseTime<10) {underNineTxt= "0"}
            timerView.setText("00:${underNineTxt}${exerciseTime}")

        imageView3.setImageResource(exerciseImage)


        //val currentTime = System.currentTimeMillis()
        //val time: Int = 30-((currentTime-startTime)/1000).toInt()

        //timerView.setText("00:${time}")

        var timerIsStarted = false

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
                openStartDialog()

                val toneG = ToneGenerator(AudioManager.STREAM_ALARM, 100)
                toneG.startTone(ToneGenerator.TONE_CDMA_ABBR_ALERT,500)
                reload()
            }

            timerIsStarted = false

        }

        startButton.setOnClickListener{
            startTime = System.currentTimeMillis()

            if(!timerIsStarted) {
                thread.start()
            }
        }
    }

    fun loadExercise(exercise: Exercise){

        currentExcercise = exercise
        exerciseImage = exercise.exImg
        exerciseTime = exercise.exTime
        exerciseDescription  = exercise.exDesc

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

        dialogBuilder.setMessage(currentExcercise?.exDesc)
        dialogBuilder.setView(layoutInflater.inflate(R.layout.excercise_dialog_layout,null))

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