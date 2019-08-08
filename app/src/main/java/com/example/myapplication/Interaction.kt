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
import android.view.View
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_dice_roller.*
import kotlinx.android.synthetic.main.activity_interaction.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class Interaction : AppCompatActivity() {
    companion object {
        const val EXTRA_TASK_DESCRIPTION = "task"
        const val DATA_BACK = false

        fun newIntent(context: Context) = Intent(context, Interaction::class.java)
    }

    override fun onNavigateUp(): Boolean {
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
        return true
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interaction)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true);
        button2.setOnClickListener {  Toast.makeText(this, "weee",Toast.LENGTH_LONG).show() }
        button3.setOnClickListener {  Snackbar.make(snackLayout,"wooo", Snackbar.LENGTH_SHORT).show() }
        button4.setOnClickListener {
            var dialog: Dialog = Dialog(this)
            //dialog.setContentView(R.layout.dialogLayout);
            dialog.setTitle("waaa")
            dialog.show()
        }
        button5.setOnClickListener {confirmFireMissiles()}
    }

    fun confirmFireMissiles() {
        val newFragment = FireMissilesDialogFragment()
        newFragment.show(supportFragmentManager, "missiles")
    }

}
class FireMissilesDialogFragment : DialogFragment() {
    override  fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            builder.setMessage(getString(R.string.fire)+"?")
                .setPositiveButton(R.string.fire,
                    DialogInterface.OnClickListener { dialog, id ->
                        Log.i("info","ok")
                    })
                .setNegativeButton(R.string.cancel,
                    DialogInterface.OnClickListener { dialog, id ->
                        Log.i("info","cancel")
                    })
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}