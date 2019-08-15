package com.example.myapplication

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.view.get
import kotlinx.android.synthetic.main.activity_about.*
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import com.example.myapplication.databinding.ActivityAboutBinding


class About : AppCompatActivity() {
    private lateinit var binding: ActivityAboutBinding
    private val me : MyName = MyName("Giovanni Minelli")
    companion object {
        const val EXTRA_TASK_DESCRIPTION = "task"
        const val DATA_BACK = false

        fun newIntent(context: Context) = Intent(context, About::class.java)
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
        //setContentView(R.layout.activity_about)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_about)
        binding.myName = me
        supportActionBar!!.setDisplayHomeAsUpEnabled(true);

        binding.nickBtn.setOnClickListener{
            changeNick(it)
        }
    }

    fun changeNick(view: View){
        binding.apply {
            //nickText.text = nickInput.text
            me.nickname = nickInput.text.toString()
            binding.invalidateAll()
            nickInput.visibility = View.GONE
            nickText.visibility = View.VISIBLE
            //or this
            binding.nickBtn.visibility = View.GONE
        }

        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

data class MyName(var name: String = "", var nickname: String = "")
