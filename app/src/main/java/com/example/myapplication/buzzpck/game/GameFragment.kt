/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.myapplication.buzzpck.game
import android.os.Bundle
import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.myapplication.buzzpck.game.GameFragmentDirections
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentGameBBinding

/**
 * Fragment where the game is played
 */
class GameFragment : Fragment() {

    private lateinit var gvm : GameViewModel

    private lateinit var binding: FragmentGameBBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_game_b,
                container,
                false
        )
        Log.i("GameFragment","linking")
        gvm = ViewModelProviders.of(this).get(GameViewModel::class.java)
/*----------------------------------*/
        binding.dataGameViewModel = gvm
        binding.setLifecycleOwner(this)
/*----------------------------------
puoi o usare questi metodi per aggiornare gli elementi grafici
    VIEWS <--> UI CONTROLLER <--> VIEWMODEL
oppure crei un oggetto viewmodel direttamente nell'xml (VIEWS) e inietti direttamente i dati in vista
    VIEWS <--> VIEWMODEL
        binding.correctButtonB.setOnClickListener {
            gvm.onCorrect()
        }
        binding.skipButtonB.setOnClickListener {
            gvm.onSkip()
        }
gvm.score.observe(this, Observer {  newScore ->binding.scoreTextB.text = newScore.toString()})
gvm.word.observe( this, Observer {  newWord -> binding.wordTextB.text = newWord})
/*----------------------------------*/*/
//ho implementato solo due observer ma potrei farli tutti lasciando l'UI CONTROLLER vuoto
        gvm.seconds.observe(this, Observer {  seconds -> binding.timerTextB.text = DateUtils.formatElapsedTime(gvm.seconds.value?:0)})
        gvm.gameFinisher.observe(this, Observer {  flag -> if(flag){gameFinished();gvm.gameComplete()}})

        return binding.root

    }

    /**
     * Called when the game is finished
     */
    private fun gameFinished() {
        val action = GameFragmentDirections.actionGameToScore((gvm.score.value)?:0)
        findNavController(this).navigate(action)
    }
}
