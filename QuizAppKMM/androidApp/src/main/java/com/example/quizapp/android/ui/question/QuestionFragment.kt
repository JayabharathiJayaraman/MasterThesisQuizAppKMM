package com.example.quizapp.android.ui.question

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.quizapp.android.MainApplication
import com.example.quizapp.android.R
import com.example.quizapp.android.databinding.FragmentQuestionBinding
import com.example.quizapp.android.questions.AndroidQuestionGenerator
import com.example.quizapp.questions.QuestionViewModel
import com.example.quizapp.questions.components.QuestionComponent

class QuestionFragment : Fragment() {
    private var binding: FragmentQuestionBinding? = null
    private var questionGenerator: AndroidQuestionGenerator? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.questionGenerator = AndroidQuestionGenerator(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_question, container, false)
        binding = FragmentQuestionBinding.bind(view)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val application = (activity?.application as MainApplication)
        val viewModel = application.questionViewModel
      /*  val uriFromCamera = arguments?.getString("uri")
        uriFromCamera?.let {
            viewModel.setSoilStructurePhoto(
                FormSoilStructure.ID_SOILSTRUCTUREIMAGE,
                it
            )
        }*/

        lifecycleScope.launchWhenStarted {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect(::updateView)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        binding = null
    }

    private fun updateView(state: QuestionViewModel.State) {
        println("StateJV: $state")

        displayComponents(state.components, state.currentScreen)
    }

    private fun displayComponents(components: List<QuestionComponent>, currentScreen: Int) {
        if (binding?.scrollView?.childCount == 0) {
            binding?.scrollView?.addView(questionGenerator?.createInterface(components))
        } else {
            questionGenerator?.updateInterface(components, currentScreen)
        }
    }
}