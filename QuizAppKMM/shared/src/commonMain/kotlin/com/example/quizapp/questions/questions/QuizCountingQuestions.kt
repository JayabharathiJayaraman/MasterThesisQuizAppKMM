package com.example.quizapp.questions.questions

import com.example.quizapp.questions.QuestionViewModel
import com.example.quizapp.questions.components.*
import com.example.quizapp.questions.models.*

data class QuizCountingQuestions(
    override val type: QuestionType = QuestionType.PictureQuestions,
    override val data: QuestionData = QuizDataCountingQuestions(),
) : Question {
    override var screens: List<QuestionScreen> = listOf(
        QuestionScreen(
            components = listOf<QuestionComponent>(
                FormComponentText(
                    id = "TitleSmall1",
                    type = ComponentType.TITLESMALL,
                    text = "CountingQuestion1"
                ),
                QuestionComponentQuestionnaire(
                    id = ID_COUNTINGQUESTION1,
                    type = ComponentType.QUESTION,
                    text = listOf(
                        "CountingQuestion1Answer1",
                    ),
                    answer = (data as? QuizDataCountingQuestions)?.questionnaireWithCounting?.answers?.firstOrNull {
                        it.id == ID_COUNTINGQUESTION1
                    }?.answer
                ),
            )
        ),
        QuestionScreen(
            components = listOf<QuestionComponent>(
                FormComponentText(
                    id = "TitleSmall2",
                    type = ComponentType.TITLESMALL,
                    text = "CountingQuestion2"
                ),
                QuestionComponentQuestionnaire(
                    id = ID_COUNTINGQUESTION2,
                    type = ComponentType.QUESTION,
                    text = listOf(
                        "CountingQuestion2Answer1",
                    ),
                    answer = (data as? QuizDataCountingQuestions)?.questionnaireWithCounting?.answers?.firstOrNull {
                        it.id == ID_COUNTINGQUESTION2
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
                    id = ID_COUNTINGQUESTIONNAIRERESULT,
                    type = ComponentType.QUESTIONRESULT,
                    answers = (data as? QuizDataCountingQuestions)?.questionnaireWithCounting?.answers
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
        with(state.question.data as? QuizDataCountingQuestions) {
            val existingAnswer = this?.questionnaireWithCounting?.answers?.firstOrNull { it.id == id }

            if (existingAnswer != null) {
                val newAnswer = existingAnswer.copy(answer = answer, text = text)
                val index = this?.questionnaireWithCounting?.answers?.indexOf(existingAnswer)
                index?.let { this?.questionnaireWithCounting?.answers?.set(index, newAnswer) }
            } else {
                val newAnswer = AnswerWithPhoto(answer, id, text)
                this?.questionnaireWithCounting?.answers?.add(newAnswer)
            }
        }

        (screens[state.currentScreen].components.firstOrNull { it.id == id } as QuestionComponentQuestionnaire).answer =
            answer

        return state
    }

    companion object {
        const val ID_COUNTINGQUESTION1 = "COUNTINGQUESTION1"
        const val ID_COUNTINGQUESTION2 = "COUNTINGQUESTION2"
        const val ID_COUNTINGQUESTIONNAIRERESULT = "COUNTINGQUESTIONNAIRERESULT"
    }
}