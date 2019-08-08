package com.example.myapplication

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import java.io.IOException
import java.util.ArrayList
import androidx.recyclerview.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.Model.PhotoFragment
import com.example.myapplication.RecyclerComponents.ImageRequester
import com.example.myapplication.RecyclerComponents.Photo
import com.example.myapplication.RecyclerComponents.RecyclerAdapter
import kotlinx.android.synthetic.main.activity_recycler.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class Recycler : AppCompatActivity() {
    companion object {
        const val EXTRA_TASK_DESCRIPTION = "task"
        const val DATA_BACK = false

        fun newIntent(context: Context) = Intent(context, Recycler::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
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
            R.id.action_change_recycler_manager ->{changeLayoutManager()}
        }
        return false
    }

    private var photosList: ArrayList<Photo> = ArrayList()
    private lateinit var imageRequester: ImageRequester
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: RecyclerAdapter
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var recyclerViewLazy: RecyclerView

    private val lastVisibleItemPosition: Int
        get() = if (findViewById<RecyclerView>(R.id.recyclerView).layoutManager == linearLayoutManager) {
            linearLayoutManager.findLastVisibleItemPosition()
        } else {
            gridLayoutManager.findLastVisibleItemPosition()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true);
        //try to restore state else carica main fragment recycler
        recyclerViewLazy = findViewById<RecyclerView>(R.id.recyclerView)
        val fm = supportFragmentManager
        val trovato = fm.findFragmentByTag(PhotoFragment.GALAXYFRAGMENT_TAG)
        val ft = fm.beginTransaction()
        if (trovato != null) {
            //fai partire il fragment della galassia
            ft.replace(R.id.recyclerHomie, trovato, PhotoFragment.GALAXYFRAGMENT_TAG)
            ft.commit()
        } else{
            //riempi recyclerView con la lista di galassie
            linearLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            recyclerViewLazy.layoutManager = linearLayoutManager
            adapter = RecyclerAdapter(photosList,supportFragmentManager)
            recyclerViewLazy.adapter = adapter
            setRecyclerViewScrollListener()
            gridLayoutManager = GridLayoutManager(this, 2)
            setRecyclerViewItemTouchListener()
            imageRequester = ImageRequester(this)
        }
    }

private fun changeLayoutManager() {
   if (recyclerViewLazy.layoutManager == linearLayoutManager) {
     //1
       recyclerViewLazy.layoutManager = gridLayoutManager
     //2
     if (photosList.size == 1) {
       requestPhoto()
     }
   } else {
     //3
       recyclerViewLazy.layoutManager = linearLayoutManager
   }
 }

private fun setRecyclerViewScrollListener() {
    recyclerViewLazy.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            val totalItemCount = recyclerView.layoutManager!!.itemCount
            if (!imageRequester.isLoadingData && totalItemCount == lastVisibleItemPosition + 1) {
                requestPhoto()
            }
        }
    })
}

private fun setRecyclerViewItemTouchListener() {
    val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, viewHolder1: RecyclerView.ViewHolder): Boolean {
            return false
        }
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
            val position = viewHolder.adapterPosition
            photosList.removeAt(position)
            recyclerViewLazy.adapter!!.notifyItemRemoved(position)
        }
    }
    val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
    itemTouchHelper.attachToRecyclerView(recyclerViewLazy)
}

override fun onStart() {
    super.onStart()
    if (photosList.size == 0) {
        requestPhoto()
    }

}

    private fun requestPhoto() = runOnUiThread {runBlocking {
        try {
            var a = awaitCallback { imageRequester.getPhoto(it) }
            photosList.add(a)
            adapter.notifyItemInserted(photosList.size-1)
        } catch (e: IOException) {
            e.printStackTrace()
        }}
    }
    //metodo 1 dichiari l'interfaccia all'interno della classe activity in modo da poter usare la runOnUiThread
    //la chiamate di receivedNewPhoto() arriva da getPhoto
    fun receivedNewPhoto(newPhoto: Photo) {
        runOnUiThread {
            photosList.add(newPhoto)
            adapter.notifyItemInserted(photosList.size-1)
        }
    }
    //metodo 2 dichiari la funzione di callback all'interno della classe activity in modo da poter usare la runOnUiThread
    //la chiamata di callback arriva da getPhoto
    suspend fun <T> awaitCallback(block: (ImageRequester.CallBeBack<Photo>) -> T) : Photo =
            suspendCancellableCoroutine { cont ->
            block(object : ImageRequester.CallBeBack<Photo> {
                override fun onComplete(result: Photo){
                    cont.resume(result)
                }
                override fun onException(e: Exception?) {
                    e?.let { cont.resumeWithException(it) }
                }
            })
        }

}
