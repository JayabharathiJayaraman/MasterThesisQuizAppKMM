package com.example.quizapp.android.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.quizapp.android.R
import com.example.quizapp.android.databinding.FragmentHomeScreenFragmentBinding
import com.example.quizapp.android.databinding.FragmentNewQuizBinding

class HomeScreenFragment : Fragment() {
    private var fragmentHomeScreenFragmentBinding: FragmentHomeScreenFragmentBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =   inflater.inflate(R.layout.fragment_home_screen_fragment, container, false)
        val binding = FragmentHomeScreenFragmentBinding.bind(view)
        fragmentHomeScreenFragmentBinding = binding

        binding.startButton?.setOnClickListener {
            view.findNavController().navigate(R.id.navigate_from_homeScreenFragment_to_newQuizFragment)
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentHomeScreenFragmentBinding = null
    }
}