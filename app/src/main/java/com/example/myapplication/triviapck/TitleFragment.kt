package com.example.myapplication.triviapck

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentTitleBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [TitleFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [TitleFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class TitleFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentTitleBinding>(inflater, R.layout.fragment_title, container, false)
        setHasOptionsMenu(true)
        binding.playButton.setOnClickListener(
            //Navigation.findNavController(it).navigate(R.id.action_titleFragment_to_gameFragment)
            //it.findNavController().navigate(R.id.action_titleFragment_to_gameFragment)
            //it.findNavController().navigate(TitleFragmentDirections.actionTitleFragmentToGameFragment())
            Navigation.createNavigateOnClickListener(R.id.action_titleFragment_to_gameFragment))
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.overflow_menu,menu)
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        //l'opzione selezionata ha un id particolare: è il nome del fragment
        return NavigationUI.onNavDestinationSelected(item!!,
            view!!.findNavController()) || super.onOptionsItemSelected(item)
    }
}
