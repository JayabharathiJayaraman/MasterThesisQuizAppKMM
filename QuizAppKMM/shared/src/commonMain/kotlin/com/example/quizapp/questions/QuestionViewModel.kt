package com.example.quizapp.questions

import com.example.quizapp.questions.components.QuestionComponent
import com.example.quizapp.questions.components.QuestionComponentQuestionnaire
import com.example.quizapp.questions.models.QuestionnaireAnswer
import com.example.quizapp.questions.questions.Question
import com.example.quizapp.utilites.ViewModelState
import com.example.quizapp.utilites.ViewModelStateImpl

class QuestionViewModel constructor(
    val question: Question
) : ViewModelState<QuestionViewModel.State> by ViewModelStateImpl(State(question)) {
    fun nextScreen() {
        if (state.value.currentScreen < state.value.question.screens.size - 1) {
            updateStateAndSave {
                copy(currentScreen = currentScreen + 1)
            }
        }
    }

    fun previousScreen() {
        if (state.value.currentScreen > 0) {
            updateStateAndSave {
                copy(currentScreen = currentScreen - 1)
            }
        }
    }

    private fun updateStateAndSave(state: State.() -> State) {
        updateState(state).also(::save)
    }

    private fun save(state: State) {
        println("Saving state: $state")
    }

    data class State(
        val question: Question,
        val currentScreen: Int = 0,
        val counter: Int = 0,
    ) {
        val components: List<QuestionComponent> = question.screens[currentScreen].components
        val totalScreens: Int = question.screens.size
    }

    fun setQuestionnaireAnswer(id: String, answer: QuestionnaireAnswer, text: String) =
        state.value.components.firstOrNull {
            it is QuestionComponentQuestionnaire
        }.let {
            updateStateAndSave {
                question.setQuestionnaireAnswer(id, answer, text, state.value)
                    .copy(counter = counter + 1)
            }
        }
}