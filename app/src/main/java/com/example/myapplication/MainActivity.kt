package com.example.myapplication

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.Lifecycle


class MainActivity : AppCompatActivity() {
    private val PREFS_TASKS = "prefs_tasks"
    private val KEY_TASKS_LIST = "tasks_list"
    private val ADD_TASK_REQUEST = 1

    private val taskList = mutableListOf<String>()
    //private val adapter by lazy { makeAdapter(taskList) }
    //private val tickReceiver by lazy { makeBroadcastReceiver() }

    /*private fun makeAdapter(list: List<String>): ArrayAdapter<String> =
        ArrayAdapter(this, android.R.layout.simple_list_item_1, list)*/
    /*private fun makeBroadcastReceiver(): BroadcastReceiver {
        return object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent?) {
                if (intent?.action == Intent.ACTION_TIME_TICK)
                    dateTimeTextView.text = getCurrentTimeStamp()
            }
        }
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //taskListView.adapter = adapter
        //taskListView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ -> taskSelected(position) }
        /*val savedList = getSharedPreferences(PREFS_TASKS, Context.MODE_PRIVATE).getString(KEY_TASKS_LIST, null)
        if (savedList != null) {
            val items = savedList.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            taskList.addAll(items)
        }*/
    }
    override fun onResume() {
        super.onResume()
        //registerReceiver(tickReceiver, IntentFilter(Intent.ACTION_TIME_TICK))
    }

    override fun onPause() {
        super.onPause()
        /*try {
            unregisterReceiver(tickReceiver)
        } catch (e: IllegalArgumentException) {
            Log.e(MainActivity.TAG, "Time tick Receiver not registered", e)
        }*/
    }

    fun switchActivity(view: View) {
        var intent : Intent? = null
        when(view.id){
            R.id.inter -> {intent = Interaction.newIntent(getApplicationContext())}
            R.id.dice -> {intent = DiceRoller.newIntent(getApplicationContext())}
            R.id.frag -> {intent = FragmentSwitcher.newIntent(getApplicationContext())}
            R.id.recy -> {intent = Recycler.newIntent(getApplicationContext())}
            R.id.mvvm -> {intent = ViewModelPattern.newIntent(getApplicationContext())}
            R.id.about -> {intent = About.newIntent(getApplicationContext())}
            R.id.cons -> {intent = Constraint.newIntent(getApplicationContext())}
            R.id.triv -> {intent = Trivia.newIntent(getApplicationContext())}
            R.id.dess -> {intent = Bakery.newIntent(getApplicationContext())}
            R.id.buzz -> {intent = Buzzer.newIntent(getApplicationContext())}
            R.id.sleep -> {intent = Sleepy.newIntent(getApplicationContext())}
        }
        Log.w("a","b")
        startActivityForResult(intent!!, ADD_TASK_REQUEST)
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == ADD_TASK_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                /*val task = TaskDescriptionActivity.getNewTask(data)
                task?.let {
                    taskList.add(task)
                    adapter.notifyDataSetChanged()
                }*/
            }
        }
    }


}
