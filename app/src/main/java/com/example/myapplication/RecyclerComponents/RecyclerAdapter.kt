package com.example.myapplication.RecyclerComponents

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Model.PhotoFragment
import com.example.myapplication.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recyclerview_item_row.view.*

class RecyclerAdapter(private val photos: ArrayList<Photo>, val supportFragmentManager: FragmentManager) : RecyclerView.Adapter<RecyclerAdapter.PhotoHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.PhotoHolder {
    val inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_row, parent,false)
    return PhotoHolder(inflatedView,supportFragmentManager)
  }

  override fun getItemCount(): Int = photos.size

  override fun onBindViewHolder(holder: RecyclerAdapter.PhotoHolder, position: Int) {
    val itemPhoto = photos[position]
    holder.bindPhoto(itemPhoto)
  }

  //1
  class PhotoHolder(private val view: View, val supportFragmentManager: FragmentManager) : RecyclerView.ViewHolder(view), View.OnClickListener {
    private var photo: Photo? = null

    init {
      view.setOnClickListener(this)
    }

    fun bindPhoto(photo: Photo) {
      this.photo = photo
      Picasso.with(view.context).load(photo.url).into(view.itemImage)
      view.itemDate.text = photo.convertDateToHumanDate();
      view.itemDescription.text = photo.explanation
    }

    override fun onClick(v: View) {
      var fragment: PhotoFragment? = null
      fragment = PhotoFragment.newInstance(photo)
      val fm = supportFragmentManager
      val ft = fm.beginTransaction()
      ft.addToBackStack(null)
      ft.replace(R.id.recyclerHomie, fragment!!, PhotoFragment.GALAXYFRAGMENT_TAG)
      ft.commit()
      /*val context = itemView.context
      val showPhotoIntent = Intent(context, PhotoFragment::class.java)
      showPhotoIntent.putExtra(PHOTO_KEY, photo)
      context.startActivity(showPhotoIntent)*/
    }

    companion object {
      private val PHOTO_KEY = "PHOTO"
    }
  }

}