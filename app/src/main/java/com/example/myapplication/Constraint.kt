package com.example.myapplication

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.databinding.DataBindingUtil
import kotlinx.android.synthetic.main.activity_constraint.*

class Constraint : AppCompatActivity() {
    companion object {
        const val EXTRA_TASK_DESCRIPTION = "task"
        const val DATA_BACK = false

        fun newIntent(context: Context) = Intent(context, Constraint::class.java)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                if (DATA_BACK) {
                    val taskDescription = ""
                    if (!taskDescription.isEmpty()) {
                        val result = Intent()
                        result.putExtra(EXTRA_TASK_DESCRIPTION, taskDescription)
                        setResult(Activity.RESULT_OK, result)
                    } else {
                        setResult(Activity.RESULT_CANCELED)
                    }
                }
                finish()
            }
        }
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_constraint)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true);
        setListener()
    }

    private fun setListener(){
        val clickableList: List<View> = listOf(box_one,box_two,box_three,box_four,box_five, red_btn, yellow_btn, blue_btn)
        for (v in clickableList){
            v.setOnClickListener { colorize(it) }
        }
    }

    private fun colorize(view: View){
        when(view.id){
            R.id.box_one ->{view.setBackgroundColor(Color.GRAY)}
            R.id.box_two ->{view.setBackgroundColor(Color.BLUE)}
            R.id.box_three ->{view.setBackgroundColor(Color.GREEN)}
            R.id.box_four ->{view.setBackgroundColor(Color.GREEN)}
            R.id.box_five ->{view.setBackgroundColor(Color.GREEN)}

            R.id.red_btn ->{box_three.setBackgroundColor(Color.RED)}
            R.id.yellow_btn ->{box_four.setBackgroundColor(Color.YELLOW)}
            R.id.blue_btn ->{box_five.setBackgroundColor(Color.BLUE)}

        }

    }
}
