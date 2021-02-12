package com.zpi.jogasimple

import android.content.Intent
import android.graphics.Color
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.ToneGenerator
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
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
        Exercise(R.drawable.ex_m_1, "Muktasana jest idealna pozą do chwili odpoczynku i medytacji. Ćwiczenie to ma na celu stopniowe rozbudzenie naszego organizmu po śnie i przygotowanie go do ruchu w następnych ćwiczeniach.\n" +
                "\n" +
                "W czasie trzech minut tego ćwiczenia skup się na głębokim i spokojnym oddechu.  Pamiętaj również o wyprostowanych plecach.\n",45),
        Exercise(R.drawable.ex_m_2, "Z pozycji przyjętego wcześniej siadu wykonuj rekoma naprzemiennie skłony w lewy i prawy bok. Nie spiesz się, dokładność rozciągnięcia jest ważniejsza. \n" +
                "\n" +
                "Ćwiczenie to pomoże nam rozciągnąć ramiona i ręce, które często cierpną w nocy przez spanie w nieodpowiednich pozycjach.",45),
        Exercise(R.drawable.ex_m_3,"Setu bandhasana, inaczej pozycja mostka. \n" +
                "Łopatki oraz góra pleców leżą na ziemi, podczas gdy biodra i zgięte w kolanach nogi podnoszą się ku górze.\n" +
                "Ćwiczenie należy wykonywać powtórzeniami, uwzględniając przerwy pomiędzy nimi. W ramach przerw należy poleżeć przez parę sekund.\n" +
                "\n" +
                "Asana ta działa pozytywnie na plecy, miednicę, nogi i klatkę piersiowa. Przygotowuje nas idealnie na czekający dzień - pomaga usunąć napięcie z barków, uśmierzyć ból pleców.",45),
        Exercise(R.drawable.ex_m_4,"Klęknięcie na jedną nogę z wyrokiem w przód (Anjaneyasana)\n" +
                "\n" +
                "W ćwiczeniu należy pozostać dłuższą chwilę, po czym zmieniać kolejność na drugą nogę.\n" +
                "\n" +
                "Pamiętaj o prostych plecach i spokojnym oddechu.\n" +
                "Ćwiczenie wspiera prawidłowe funkcjonowanie kolan.",45),
        Exercise(R.drawable.ex_m_5,"Asana psa z głową w dół (Adho Mukha Svanasana).\n" +
                "Osoby początkujące mogą stać na czubkach palców, ale w miarę postępów należy dążyć do postawienia stóp całkowicie na ziemi.\n" +
                "\n" +
                "Jest to jedna z najbardziej znanych póz jogi - używa się jej często jako relaksacyjnego przerywnika między sekwencjami ćwiczeń.\n" +
                "\n" +
                "Wpływa pozytywnie na krążenie krwi, wzmacnia ramiona i poprawia elastyczność stawów.",45))

    val exerciseNight = arrayOf(
        Exercise(R.drawable.ex_n_1, "Adho mukhna virasana - skłon do przodu. \n" +
                "Pozycja ta jest jedna z lepszych jeśli chodzi o odprężenie ciała i regenerację po ciężkim dniu.\n" +
                "\n" +
                "Ćwiczenie pomaga uelastycznić kręgosłup, rozluźnia mięśnie szyi i intensywnie rozciąga kolana",45),
        Exercise(R.drawable.ex_n_2, " Siad klęczny z odchyleniem w tył. \n" +
                "Należy przyjąć tą samą pozycję przez cały czas trwania ćwiczenia.\n" +
                "\n" +
                "Ćwiczenie pomaga rozluźnić mięśnie karku, ramion i wzmacnia zasięg odchylania pleców",45),
        Exercise(R.drawable.ex_n_3,"Siad klęczny z wyciągnięciem do przodu.\n" +
                "Ćwiczenie należy wykonywać powtórzeniami z krótkimi przerwami pomiędzy.\n" +
                "\n" +
                "Pamiętaj żeby podczas ćwiczenia próbować sięgnąć jak najdalej.\n" +
                "\n" +
                "Ćwiczenie wspomaga ramiona, rozluźnia plecy i mięśnie karku.",45),
        Exercise(R.drawable.ex_n_4,"Pozycja kota (Marjarysana).\n" +
                "Wygięcia grzbietu należy utrzymywać przez dłuższą chwilę i uwzględniać chwile rozluźnienia między powtórzeniami.\n" +
                "\n" +
                "Ćwiczenie rewelacyjnie działa na kręgosłup wzmacniając go i rozluźniając przy tym plecy.",45),
        Exercise(R.drawable.ex_n_5,"Skręt w Svastikasanie. Ćwiczenie należy wykonywać naprzemiennie sięgnięciami za siebie. \n" +
                "\n" +
                "Należy pamiętać o prostych plecach i zsynchronizowaniu oddychania z wykonywanymi skrętami.\n" +
                "Ćwiczenie wpływa pozytywnie na mięśnie ramion i ich rozciągnięcie.",45))

    var currentExcerciseSet = exerciseMorning

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_excercise)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val setName = intent.extras?.get("ExerciseSet") as String
        val activityMain = findViewById(R.id.mainView) as ConstraintLayout
        val buttonStart = findViewById(R.id.startButton) as Button

        setName.let {
            if(it.contains("Morning")) {
                currentExcerciseSet = exerciseMorning
                activityMain.setBackgroundColor(Color.parseColor("#fffde8"));
            }
            else {
                currentExcerciseSet = exerciseNight
                activityMain.setBackgroundColor(Color.parseColor("#7799ff"));
                buttonStart.setBackgroundColor(Color.parseColor("#3355bb"))
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

                var mediaPlayer = MediaPlayer.create(this, R.raw.zapsplat_bells_medium_bell_soft_strike_long_decay_002_60153)
                mediaPlayer.start()

                currentExcerciseNo++

                if(currentExcerciseNo >= currentExcerciseSet.size) {
                    val excerciseIntent4 = Intent(this,MainActivity::class.java)
                    startActivity(excerciseIntent4)
                    currentExcerciseNo = 0
                }
                else{
                    loadExercise(currentExcerciseSet[currentExcerciseNo])
                    openStartDialog()
                }
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
        when(item.itemId){
            android.R.id.home->finish()
            R.id.id_info->{
                openStartDialog()
                return true
            }
            R.id.id_sound->{
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun openStartDialog(){
        val dialogBuilder = AlertDialog.Builder(this)

        val dialogView = layoutInflater.inflate(R.layout.excercise_dialog_layout,null)

        val dialogExcerciseView = dialogView.findViewById(R.id.dialogExcerciseImageView) as ImageView
        dialogExcerciseView.setImageResource(exerciseImage)

        dialogBuilder.setMessage(currentExcercise?.exDesc)
        dialogBuilder.setView(dialogView)
        dialogBuilder.setPositiveButton("OK"){
                dialog, id ->
        }
        val dialog = dialogBuilder.create()

        if(currentExcerciseNo <= currentExcerciseSet.size -1) {
            dialog.show()
        }
    }



}