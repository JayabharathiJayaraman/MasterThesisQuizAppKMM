package com.example.quizapp.questions

import com.example.quizapp.questions.questions.*

class QuestionFactory() {
    fun createForm(type: QuestionType): Question {
        return when (type) {
            QuestionType.ShapeQuestions -> QuizShapeQuestions()
            QuestionType.ColorQuestions -> QuizColorQuestions()
            QuestionType.PictureQuestions -> QuizCountingQuestions()
        }
    }
    /* fun createForm(): FormSoilStructure {
         return FormSoilStructure()
     }*/
}