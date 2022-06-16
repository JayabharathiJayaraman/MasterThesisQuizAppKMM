package com.example.quizapp.questions.components

import com.example.quizapp.questions.models.AnswerWithPhoto
import com.example.quizapp.questions.models.QuestionnaireAnswer

enum class ComponentType {
    TITLEBIG,
    TITLESMALL,
    BODY,
    QUESTION,
    QUESTIONRESULT,
    IMAGE
}
interface QuestionComponent {
    val type: ComponentType
    val id: String
}

class FormComponentText(
    override val type: ComponentType,
    override val id: String,
    val text: String
) :  QuestionComponent

class QuestionComponentQuestionnaire(
    override val type: ComponentType,
    override val id: String,
    val text: List<String>,
    var answer: QuestionnaireAnswer?
) : QuestionComponent

class QuestionComponentQuestionnaireResult(
    override val type: ComponentType,
    override val id: String,
    val answers: MutableList<AnswerWithPhoto>?,
) : QuestionComponent

class QuestionnaireComponentImage(
    override val type: ComponentType,
    override val id: String,
    val image: String,
) : QuestionComponent