package com.example.quizapp.android

import android.app.Application
import com.example.quizapp.questions.QuestionViewModel
import com.example.quizapp.questions.questions.QuestionType
import com.example.quizapp.questions.questions.QuizColorQuestions
import com.example.quizapp.questions.questions.QuizCountingQuestions
import com.example.quizapp.questions.questions.QuizShapeQuestions

class MainApplication : Application() {
    var questionViewModel: QuestionViewModel =
        QuestionViewModel(QuizShapeQuestions(QuestionType.ShapeQuestions))

    fun setViewModel(questionType: QuestionType) {
        questionViewModel = when (questionType) {
            QuestionType.ShapeQuestions -> QuestionViewModel(QuizShapeQuestions(QuestionType.ShapeQuestions))
            QuestionType.ColorQuestions -> QuestionViewModel(QuizColorQuestions(QuestionType.ColorQuestions))
            QuestionType.PictureQuestions -> QuestionViewModel(QuizCountingQuestions(QuestionType.PictureQuestions))
        }
    }
}