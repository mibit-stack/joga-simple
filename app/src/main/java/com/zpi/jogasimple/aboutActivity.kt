package com.zpi.jogasimple

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity

class aboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)

        return super.onCreateOptionsMenu(menu)
    }
}