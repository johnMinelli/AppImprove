package com.example.myapplication.Model

import android.app.Activity
import android.icu.lang.UCharacter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.RecyclerComponents.Photo
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_photo.*

class PhotoFragment : Fragment() {

    private var descrizione: String? = null
    private var id_img : Int = 0
    private var url_img : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            if(arguments!!.getInt(ARG_ID_IMG) != 0){
                id_img = arguments!!.getInt(ARG_ID_IMG)
            }else{
                url_img = arguments!!.getString(ARG_URL_IMG)
            }
            descrizione = arguments!!.getString(ARG_DESCR)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_photo, container, false)
        val imageView = v.findViewById(R.id.photoImageView) as ImageView
        val txt_descr = v.findViewById(R.id.photoDescription) as TextView
        if(id_img != 0){
            imageView.setImageResource(id_img)
        }else{
            Picasso.with(requireActivity().applicationContext).load(url_img).into(v.findViewById(R.id.photoImageView) as ImageView)
        }
        txt_descr.text = descrizione
        return v
    }








    companion object {

        val CITTAFRAGMENT_TAG = "CITTAFRAGMENT_TAG"
        val GALAXYFRAGMENT_TAG = "CITTAFRAGMENT_TAG"

        private val ARG_TYPE = "json"
        private val ARG_ID_IMG = "IDimg"
        private val ARG_URL_IMG = "URLimg"
        private val ARG_NOME = "nome"
        private val ARG_DESCR = "descr"


        fun newInstance(elem: Photo?): PhotoFragment {
            val fragment = PhotoFragment()
            val args = Bundle()
            if (elem?.explanation.equals("Roma")) {
                args.putBoolean(ARG_TYPE, false)
                args.putInt(ARG_ID_IMG, R.drawable.roma)
                args.putString(ARG_NOME, "Roma")
                args.putString(ARG_DESCR, "Lorem impsum")
            }else{
                args.putBoolean(ARG_TYPE, true)
                args.putString(ARG_URL_IMG, elem?.url)
                args.putString(ARG_NOME, "Galassia")
                args.putString(ARG_DESCR, elem?.explanation)
            }
            fragment.arguments = args
            return fragment
        }
    }



}