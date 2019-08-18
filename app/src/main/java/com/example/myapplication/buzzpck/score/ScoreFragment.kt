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

package com.example.myapplication.buzzpck.score

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myapplication.buzzpck.score.ScoreFragmentDirections
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentScoreBBinding

/**
 * Fragment where the final score is shown, after the game is over
 */
class ScoreFragment : Fragment() {
    private lateinit var viewModel: ScoreViewModel
    private lateinit var viewModelFactory: ScoreViewModelFactory

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        // Inflate view and obtain an instance of the binding class.
        val binding: FragmentScoreBBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_score_b,
                container,
                false
        )
        viewModelFactory = ScoreViewModelFactory(ScoreFragmentArgs.fromBundle(arguments).score)
        //View model con il costruttore si fa con il facory
        //stai chiedendo al ViewModelProviders di farti una istanza di ScoreViewModel::class.java per this Fragment con
        //il modello specificato dalla factory
        //Solitamente non è necessario fare un ViewModel con il costruttore ma è sufficente fare un setter in un secondo momento
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ScoreViewModel::class.java)

        /*-----------------------------------
        //esempio nella classe GameFragment
        binding.dataScoreViewModel = viewModel
        binding.setLifecycleOwner(this)
        -------------------------------------*/
        // Get args using by navArgs property delegate
        var args = ScoreFragmentArgs.fromBundle(arguments)
        binding.scoreTextB.text = args.score.toString()
        binding.playAgainButtonB.setOnClickListener { onPlayAgain() }

        return binding.root
    }

    private fun onPlayAgain() {
        findNavController().navigate(ScoreFragmentDirections.actionRestart())
    }
}
