package com.example.a2in1app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    lateinit var numbersGameButton: Button
    lateinit var phraseGameButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numbersGameButton = findViewById(R.id.numbersGame_button)
        phraseGameButton = findViewById(R.id.phraseGame_button)


        numbersGameButton.setOnClickListener {
            goToNumbersGame()
        }
        phraseGameButton.setOnClickListener {
            goToPhraseGame()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        menu!!.findItem(R.id.menu_main_menu).isVisible = false
        menu!!.findItem(R.id.menu_new_game).isVisible = false
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_numbers_game -> {
                Toast.makeText(this,"Numbers Game!", Toast.LENGTH_SHORT).show()
                goToNumbersGame()
                return true
            }
            R.id.menu_phrase_game -> {
                Toast.makeText(this,"Phrase Game!",Toast.LENGTH_SHORT).show()
                goToPhraseGame()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun goToNumbersGame(){
        val intent = Intent(this, NumbersGameActivity::class.java)
        startActivity(intent)
    }
    private fun goToPhraseGame(){
        val intent = Intent(this, PhraseGameActivity::class.java)
        startActivity(intent)
    }
}