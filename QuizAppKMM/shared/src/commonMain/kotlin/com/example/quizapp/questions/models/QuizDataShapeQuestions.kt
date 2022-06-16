package com.example.quizapp.questions.models


data class QuizDataShapeQuestions(
    override val questionnaireIsAnswered: QuestionnaireIsAnswered = QuestionnaireIsAnswered(),
    val questionnaireWithShape: questionnaireWithShape = questionnaireWithShape(),
    var comment: String = "",
) : QuestionData

data class questionnaireWithShape(
    val answers: MutableList<AnswerWithPhoto> = mutableListOf()
)