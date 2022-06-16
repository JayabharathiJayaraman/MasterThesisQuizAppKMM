package com.example.quizapp.android.ui.newQuiz

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.quizapp.android.MainApplication
import com.example.quizapp.android.R
import com.example.quizapp.android.databinding.FragmentNewQuizBinding
import com.example.quizapp.questions.questions.QuestionType


class NewQuizFragment : Fragment() {
    private var fragmentNewQuizBinding: FragmentNewQuizBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_new_quiz, container, false)
        val binding = FragmentNewQuizBinding.bind(view)
        val application = (activity?.application as MainApplication)
        binding.quiz1Container.setOnClickListener {
            application.setViewModel(questionType  = QuestionType.ShapeQuestions)
            view.findNavController().navigate(R.id.navigate_from_newQuizFragment_to_questionFragment)
        }

        binding.quiz2Container.setOnClickListener {
            application.setViewModel(questionType = QuestionType.ColorQuestions)
            view.findNavController().navigate(R.id.navigate_from_newQuizFragment_to_questionFragment)
        }

        binding.quiz3Container.setOnClickListener {
            application.setViewModel(questionType = QuestionType.PictureQuestions)
            view.findNavController().navigate(R.id.navigate_from_newQuizFragment_to_questionFragment)
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentNewQuizBinding = null
    }
}