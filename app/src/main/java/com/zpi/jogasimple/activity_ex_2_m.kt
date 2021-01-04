package com.zpi.jogasimple

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class activity_ex_2_m : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ex_2_m)
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


        dialogBuilder.setMessage("Przykładowy opis ćwiczenia")
        dialogBuilder.setView(layoutInflater.inflate(R.layout.excercise_dialog_layout,null))

        dialogBuilder.setPositiveButton("OK"){
                dialog, id ->
        }


        val dialog = dialogBuilder.create()

        dialog.show()
    }
}