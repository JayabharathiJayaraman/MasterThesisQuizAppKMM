package com.example.quizapp.questions.questions

import com.example.quizapp.questions.QuestionViewModel
import com.example.quizapp.questions.components.*
import com.example.quizapp.questions.models.AnswerWithPhoto
import com.example.quizapp.questions.models.QuestionData
import com.example.quizapp.questions.models.QuestionnaireAnswer
import com.example.quizapp.questions.models.QuizDataShapeQuestions

data class QuizShapeQuestions(
    override val type: QuestionType = QuestionType.ShapeQuestions,
    override val data: QuestionData = QuizDataShapeQuestions(),
) : Question {
    override var screens: List<QuestionScreen> = listOf(
        QuestionScreen(
            components = listOf<QuestionComponent>(
                FormComponentText(
                    id = "TitleSmall1",
                    type = ComponentType.TITLESMALL,
                    text = "ShapeQuestion1"
                ),
                QuestionComponentQuestionnaire(
                    id = ID_SHAPEQUESTION1,
                    type = ComponentType.QUESTION,
                    text = listOf(
                        "ShapeQuestion1Answer1",
                    ),
                    answer = (data as? QuizDataShapeQuestions )?.questionnaireWithShape?.answers?.firstOrNull {
                        it.id == ID_SHAPEQUESTION1
                    }?.answer
                ),
            )
        ),

        QuestionScreen(
            components = listOf<QuestionComponent>(
                FormComponentText(
                    id = "TitleSmall2",
                    type = ComponentType.TITLESMALL,
                    text = "ShapeQuestion2"
                ),
                QuestionComponentQuestionnaire(
                    id = ID_SHAPEQUESTION2,
                    type = ComponentType.QUESTION,
                    text = listOf("ShapeQuestion2Answer1"),
                    answer = (data as? QuizDataShapeQuestions )?.questionnaireWithShape?.answers?.firstOrNull {
                        it.id == ID_SHAPEQUESTION2
                    }?.answer
                ),
            )
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
                    id = ID_SHAPEQUESTIONNAIRERESULT,
                    type = ComponentType.QUESTIONRESULT,
                    answers = (data as? QuizDataShapeQuestions)?.questionnaireWithShape?.answers
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
        with(state.question.data as? QuizDataShapeQuestions) {
            val existingAnswer = this?.questionnaireWithShape?.answers?.firstOrNull { it.id == id }

            if (existingAnswer != null) {
                val newAnswer = existingAnswer.copy(answer = answer, text = text)
                val index = this?.questionnaireWithShape?.answers?.indexOf(existingAnswer)
                index?.let { this?.questionnaireWithShape?.answers?.set(index, newAnswer) }
            } else {
                val newAnswer = AnswerWithPhoto(answer, id, text)
                this?.questionnaireWithShape?.answers?.add(newAnswer)
            }
        }

        (screens[state.currentScreen].components.firstOrNull { it.id == id } as QuestionComponentQuestionnaire).answer =
            answer

        return state
    }

    companion object {
        const val ID_SHAPEQUESTION1 = "SHAPEQUESTION1"
        const val ID_SHAPEQUESTION2 = "SHAPEQUESTION2"
        const val ID_SHAPEQUESTIONNAIRERESULT = "SHAPEQUESTIONNAIRERESULT"
    }
}