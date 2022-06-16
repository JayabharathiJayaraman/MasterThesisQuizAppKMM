package com.example.quizapp.questions.questions

import com.example.quizapp.questions.QuestionViewModel
import com.example.quizapp.questions.components.*
import com.example.quizapp.questions.models.*
import com.example.quizapp.questions.models.AnswerWithPhoto

data class QuizColorQuestions(
    override val type: QuestionType = QuestionType.ColorQuestions,
    override val data: QuestionData = QuizColorDataQuestions(),
) : Question {
    override var screens: List<QuestionScreen> = listOf(
        QuestionScreen(
            components = listOf<QuestionComponent>(
                FormComponentText(
                    id = "TitleSmall1",
                    type = ComponentType.TITLESMALL,
                    text = "ColorQuestion1"
                ),
                QuestionComponentQuestionnaire(
                    id = ID_COLORGQUESTION1,
                    type = ComponentType.QUESTION,
                    text = listOf(
                        "ColorQuestion1Answer1",
                    ),
                            answer = (data as? QuizColorDataQuestions)?.questionnaireWithColor?.answers?.firstOrNull {
                        it.id == ID_COLORGQUESTION1
                    }?.answer
                ),
            )
        ),

        QuestionScreen(
            components = listOf<QuestionComponent>(
                FormComponentText(
                    id = "TitleSmall2",
                    type = ComponentType.TITLESMALL,
                    text = "ColorQuestion2"
                ),
                QuestionComponentQuestionnaire(
                    id = ID_COLORGQUESTION2,
                    type = ComponentType.QUESTION,
                    text = listOf(
                        "ColorQuestion2Answer1",
                    ),
                    answer = (data as? QuizColorDataQuestions)?.questionnaireWithColor?.answers?.firstOrNull {
                        it.id == ID_COLORGQUESTION2
                    }?.answer
                )
            ),
        ),

        QuestionScreen(
            components = listOf<QuestionComponent>(
                FormComponentText(
                    id = "ResultTitle",
                    type = ComponentType.TITLEBIG,
                    text = "Result",
                ),
                QuestionnaireComponentImage(
                    id = "trophyImage",
                    type = ComponentType.IMAGE,
                    image = "trophy"
                ),
                QuestionComponentQuestionnaireResult(
                    id = ID_COLORQUESTIONNAIRERESULT,
                    type = ComponentType.QUESTIONRESULT,
                    answers = (data as? QuizColorDataQuestions)?.questionnaireWithColor?.answers
                ),
            )
        )

    )

    override fun setQuestionnaireAnswer(
        id: String,
        answer: QuestionnaireAnswer,
        text: String,
        state: QuestionViewModel.State
    ): QuestionViewModel.State {
        with(state.question.data as? QuizColorDataQuestions) {
            val existingAnswer = this?.questionnaireWithColor?.answers?.firstOrNull { it.id == id }

            if (existingAnswer != null) {
                val newAnswer = existingAnswer.copy(answer = answer, text = text)
                val index = this?.questionnaireWithColor?.answers?.indexOf(existingAnswer)
                index?.let { this?.questionnaireWithColor?.answers?.set(index, newAnswer) }
            } else {
                val newAnswer = AnswerWithPhoto(answer, id, text)
                this?.questionnaireWithColor?.answers?.add(newAnswer)
            }
        }

        (screens[state.currentScreen].components.firstOrNull { it.id == id } as QuestionComponentQuestionnaire).answer =
            answer

        return state
    }

    companion object {
        const val ID_COLORGQUESTION1 = "COLORQUESTION1"
        const val ID_COLORGQUESTION2 = "COLORQUESTION2"
        const val ID_COLORQUESTIONNAIRERESULT = "COLORQUESTIONNAIRERESULT"
    }
}