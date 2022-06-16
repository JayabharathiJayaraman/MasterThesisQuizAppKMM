package com.example.quizapp.questions.models

interface QuestionData {
    val questionnaireIsAnswered: QuestionnaireIsAnswered
}
data class QuestionnaireIsAnswered(
    var answered: Boolean? = null,
)
enum class QuestionnaireAnswer {
    CorrectAns,
    WrongAns1,
    WrongAns2,
}