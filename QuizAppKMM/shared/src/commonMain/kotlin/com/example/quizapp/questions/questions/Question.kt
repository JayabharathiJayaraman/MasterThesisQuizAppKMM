package com.example.quizapp.questions.questions

import com.example.quizapp.questions.QuestionViewModel
import com.example.quizapp.questions.components.QuestionScreen
import com.example.quizapp.questions.models.QuestionData
import com.example.quizapp.questions.models.QuestionnaireAnswer

interface Question {
    val screens: List<QuestionScreen>
    val type: QuestionType
    val data: QuestionData

    fun setQuestionnaireAnswer(
        id: String,
        answer: QuestionnaireAnswer,
        text: String,
        state: QuestionViewModel.State
    ): QuestionViewModel.State
}

enum class QuestionType {
    ShapeQuestions,
    ColorQuestions,
    PictureQuestions,
}