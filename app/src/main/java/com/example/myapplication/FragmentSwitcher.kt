package com.example.myapplication

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.Model.PhotoFragment
import com.example.myapplication.RecyclerComponents.Photo
import kotlinx.android.synthetic.main.fragment_photo.*
import org.json.JSONObject

class FragmentSwitcher : AppCompatActivity() {
    companion object {
        const val EXTRA_TASK_DESCRIPTION = "task"
        const val DATA_BACK = false

        fun newIntent(context: Context) = Intent(context, FragmentSwitcher::class.java)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                if(fragment.isShown) supportFragmentManager!!.popBackStack()
                else {
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
        }
        return false
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_switcher)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true);
        //try to restore state else carica main fragment
        val fm = supportFragmentManager
        val trovato = fm.findFragmentByTag(PhotoFragment.CITTAFRAGMENT_TAG)
        val ft = fm.beginTransaction()
        if (trovato != null) {
            ft.replace(R.id.homie, trovato,PhotoFragment.CITTAFRAGMENT_TAG)
        } else
            ft.replace(R.id.homie,MainFragment(),null)
        ft.commit()
    }

    fun switchFrag(v: View) {
        var fragment: PhotoFragment? = null
        val city = JSONObject()
        city.put("date","")
        city.put("explation","Roma")
        city.put("url","")
        fragment = PhotoFragment.newInstance(Photo(city))
        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        ft.addToBackStack(null)

        ft.replace(R.id.homie, fragment!!, PhotoFragment.CITTAFRAGMENT_TAG)
        ft.commit()

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }
}

class MainFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.main_fragment_switcher, container, false)
    }
}
