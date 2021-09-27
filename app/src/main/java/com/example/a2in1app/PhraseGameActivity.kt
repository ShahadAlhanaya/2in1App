package com.example.a2in1app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.content.DialogInterface
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.numbersgameapp.NumbersAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_numbers_game.*
import kotlinx.android.synthetic.main.activity_phrase_game.*

class PhraseGameActivity : AppCompatActivity() {

    private lateinit var clRoot: ConstraintLayout
    private lateinit var guessField: EditText
    private lateinit var guessButton: Button
    private lateinit var messages: ArrayList<String>
    private lateinit var tvPhrase: TextView
    private lateinit var tvLetters: TextView

    private val answer = "this is the secret phrase"
    private val myAnswerDictionary = mutableMapOf<Int, Char>()
    private var myAnswer = ""
    private var guessedLetters = ""
    private var count = 0
    private var guessPhrase = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phrase_game)

        for(i in answer.indices){
            if(answer[i] == ' '){
                myAnswerDictionary[i] = ' '
                myAnswer += ' '
            }else{
                myAnswerDictionary[i] = '*'
                myAnswer += '*'
            }
        }

        clRoot = findViewById(R.id.clRoot)
        messages = ArrayList()

        rvMessages.adapter = NumbersAdapter( messages)
        rvMessages.layoutManager = LinearLayoutManager(this)

        guessField = findViewById(R.id.etGuessField)
        guessButton = findViewById(R.id.btGuessButton)
        guessButton.setOnClickListener { addMessage() }

        tvPhrase = findViewById(R.id.tvPhrase)
        tvLetters = findViewById(R.id.tvLetters)

        updateText()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        menu!!.findItem(R.id.menu_phrase_game).isVisible = false
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_numbers_game -> {
                goToNumbersGame()
                return true
            }
            R.id.menu_new_game -> {
                goToPhraseGame()
                return true
            }
            R.id.menu_main_menu -> {
                goToMainActivity()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun goToNumbersGame(){
        val intent = Intent(this, NumbersGameActivity::class.java)
        startActivity(intent)
        this.finish()
    }
    private fun goToPhraseGame(){
        val intent = Intent(this, PhraseGameActivity::class.java)
        startActivity(intent)
        this.finish()
    }
    private fun goToMainActivity(){
        this.finish()
    }

    private fun addMessage(){
        val msg = guessField.text.toString()

        if(guessPhrase){
            if(msg == answer){
                disableEntry()
                showAlertDialog("You win!\n\nPlay again?")
            }else{
                messages.add("Wrong guess: $msg")
                guessPhrase = false
                updateText()
            }
        }else{
            if(msg.isNotEmpty() && msg.length==1){
                myAnswer = ""
                guessPhrase = true
                checkLetters(msg[0])
            }else{
                Snackbar.make(clRoot, "Please enter one letter only", Snackbar.LENGTH_LONG).show()
            }
        }

        guessField.text.clear()
        guessField.clearFocus()
        rvMessages.adapter?.notifyDataSetChanged()
    }

    private fun disableEntry(){
        guessButton.isEnabled = false
        guessButton.isClickable = false
        guessField.isEnabled = false
        guessField.isClickable = false
    }

    private fun updateText(){
        tvPhrase.text = "Phrase:  " + myAnswer.toUpperCase()
        tvLetters.text = "Guessed Letters:  " + guessedLetters
        if(guessPhrase){
            guessField.hint = "Guess the full phrase"
        }else{
            guessField.hint = "Guess a letter"
        }
    }

    private fun checkLetters(guessedLetter: Char){
        var found = 0
        for(i in answer.indices){
            if(answer[i] == guessedLetter){
                myAnswerDictionary[i] = guessedLetter
                found++
            }
        }
        for(i in myAnswerDictionary){myAnswer += myAnswerDictionary[i.key]}
        if(myAnswer==answer){
            disableEntry()
            showAlertDialog("You win!\n\nPlay again?")
        }
        if(guessedLetters.isEmpty()){guessedLetters+=guessedLetter}else{guessedLetters+=", "+guessedLetter}
        if(found>0){
            messages.add("Found $found ${guessedLetter.toUpperCase()}(s)")
        }else{
            messages.add("No ${guessedLetter.toUpperCase()}s found")
        }
        count++
        val guessesLeft = 10 - count
        if(count<10){messages.add("$guessesLeft guesses remaining")}
        updateText()
        rvMessages.scrollToPosition(messages.size - 1)
    }

    private fun showAlertDialog(title: String) {
        // build alert dialog
        val dialogBuilder = AlertDialog.Builder(this)

        // set message of alert dialog
        dialogBuilder.setMessage(title)
            // if the dialog is cancelable
            .setCancelable(false)
            // positive button text and action
            .setPositiveButton("Yes", DialogInterface.OnClickListener {
                    dialog, id -> this.recreate()
            })
            // negative button text and action
            .setNegativeButton("No", DialogInterface.OnClickListener {
                    dialog, id -> dialog.cancel()
            })

        // create dialog box
        val alert = dialogBuilder.create()
        // set title for alert dialog box
        alert.setTitle("Game Over")
        // show alert dialog
        alert.show()
    }
}