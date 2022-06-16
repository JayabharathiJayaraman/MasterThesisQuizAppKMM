package com.example.quizapp.questions.models

data class QuizColorDataQuestions(
    var comment: String = "",
    override val questionnaireIsAnswered: QuestionnaireIsAnswered = QuestionnaireIsAnswered(),
    val questionnaireWithColor: QuestionnaireWithColor = QuestionnaireWithColor(),
) : QuestionData


data class QuestionnaireWithColor(
    val answers: MutableList<AnswerWithPhoto> = mutableListOf()
)

data class AnswerWithPhoto(
    var answer: QuestionnaireAnswer? = null,
    var id: String = "",
    val text: String = "",
    val photoUri: String? = null,
    var comment: String = "",
)