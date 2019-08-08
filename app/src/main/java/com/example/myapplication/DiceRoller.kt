package com.example.myapplication

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_dice_roller.*
import java.util.*

class DiceRoller : AppCompatActivity() {

    lateinit var diceImage: ImageView

    companion object {
        const val EXTRA_TASK_DESCRIPTION = "task"
        const val DATA_BACK = false

        fun newIntent(context: Context) = Intent(context, DiceRoller::class.java)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                //supportFragmentManager.popBackStack()
                supportActionBar!!.setDisplayHomeAsUpEnabled(false)
                if(DATA_BACK){
                    val taskDescription = ""
                    if (!taskDescription.isEmpty()) {
                        val result = Intent()
                        result.putExtra(EXTRA_TASK_DESCRIPTION, taskDescription)
                        setResult(Activity.RESULT_OK, result)
                    } else {
                        setResult(Activity.RESULT_CANCELED)
                    }
                }
                Log.w("a","a")
                finish()
            }
        }
        return false
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dice_roller)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true);
        roller.setOnClickListener {
            var r : Int = Random().nextInt(6)+1
            val drawableResource = when (r) {
                1 -> R.drawable.dice_1
                2 -> R.drawable.dice_2
                3 -> R.drawable.dice_3
                4 -> R.drawable.dice_4
                5 -> R.drawable.dice_5
                else -> R.drawable.dice_6
            }
            diceImage.setImageResource(drawableResource)
            label.text="Random picker: "+r.toString()
        }
        diceImage = findViewById(R.id.imageView)
    }
    fun funny(v: View){
        if(label.text.contains("World"))
            label.text="Hello Nope!"
        else
            label.text="Hello World!"

    }
}