package com.example.myapplication

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.*
import kotlinx.android.synthetic.main.activity_view_model.*

class ViewModelPattern : AppCompatActivity() {
    companion object {
        const val EXTRA_TASK_DESCRIPTION = "task"
        const val DATA_BACK = false

        fun newIntent(context: Context) = Intent(context, ViewModelPattern::class.java)
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

    private val viewModel: MyViewModel by lazy {
        ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MyViewModel::class.java)
    }
    private val changeObserver = Observer<Int> { value -> value?.let { incrementCount(value) } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_model)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true);
        viewModel.restoreState(savedInstanceState)
        viewModel.changeNotifier.observe(this, changeObserver)
        lifecycle.addObserver(viewModel)
        my_container.setOnClickListener { viewModel.increment() }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.saveState(outState)
    }
    private fun incrementCount(value: Int) {
        my_text.text = (value).toString()
    }
}

class MyViewModel(private var count: Int = 0) : ViewModel(), LifecycleObserver {
    companion object { const val COUNT_KEY = "CountKey" }

    val changeNotifier = MutableLiveData<Int>()

    fun increment() { changeNotifier.value = ++count }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME) fun onResume() { increment() }

    fun saveState(outState: Bundle) {
        outState.putInt(COUNT_KEY, count)
    }

    fun restoreState(inState: Bundle?) {
        inState?.let { count = inState.getInt(COUNT_KEY) }
    }
}

