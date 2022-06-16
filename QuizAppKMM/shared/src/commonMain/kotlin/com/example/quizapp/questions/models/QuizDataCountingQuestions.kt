package com.example.quizapp.questions.models

data class QuizDataCountingQuestions(
    var comment: String = "",
    val questionnaireWithCounting: QuestionnaireWithCounting = QuestionnaireWithCounting(),
    override val questionnaireIsAnswered: QuestionnaireIsAnswered = QuestionnaireIsAnswered(),
) : QuestionData

data class QuestionnaireWithCounting(
    val answers: MutableList<AnswerWithPhoto> = mutableListOf()
)