package com.example.quizapp.questions.components

interface QuestionGenerator {
    fun generateInterface(components: List<QuestionComponent>, currentScreen: Int? = null)
}