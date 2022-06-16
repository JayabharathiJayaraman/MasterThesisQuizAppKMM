package com.example.quizapp.android.questions

import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.quizapp.android.MainApplication
import com.example.quizapp.android.R
import com.example.quizapp.android.databinding.*
import com.example.quizapp.questions.components.*
import com.example.quizapp.questions.models.AnswerWithPhoto
import com.example.quizapp.questions.models.QuestionnaireAnswer

class AndroidQuestionGenerator(private val context: Context) :
    QuestionGenerator {
    private var mainView: LinearLayout = LinearLayout(context).also {
        val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(40, 0, 40, 0)
        it.layoutParams = params
        it.orientation = LinearLayout.VERTICAL
    }
    private var currentScreenRendered: Int = 0
    override fun generateInterface(components: List<QuestionComponent>, currentScreen: Int?) {
        for (component in components) {
            when (component.type) {
                ComponentType.TITLEBIG -> {
                    val bigTitle = (component as FormComponentText)
                    mainView.createOrUpdateBigTitleLabel(getStringById(this.context, bigTitle.text), bigTitle.id)
                }
                ComponentType.TITLESMALL -> {
                    val smallTitle = (component as FormComponentText)
                    mainView.createOrUpdateSmallTitleLabel(getStringById(this.context,smallTitle.text), smallTitle.id)
                }
                ComponentType.QUESTION -> {
                    val questionnaire = (component as QuestionComponentQuestionnaire)
                    mainView.createOrUpdateQuestionnaire(
                        questionnaire.id,
                        getStringByIdList(this.context,  questionnaire.text.toString()),
                        questionnaire.answer
                    )
                }
                ComponentType.IMAGE -> {
                    val image = (component as QuestionnaireComponentImage)
                    mainView.createOrUpdateImage(image.id, image.image)
                }
                ComponentType.QUESTIONRESULT -> {
                    val questionnaireResult = (component as QuestionComponentQuestionnaireResult)
                    mainView.createOrUpdateQuestionnaireResult(
                        questionnaireResult.id,
                        questionnaireResult.answers
                    )
                }
                else -> println("unknown")
            }
        }
    }

    fun createInterface(components: List<QuestionComponent>): View {
        generateInterface(components)
        return mainView
    }

    fun updateInterface(components: List<QuestionComponent>, currentScreen: Int) {
        if (currentScreen != currentScreenRendered) {
            mainView.removeAllViews()
            currentScreenRendered = currentScreen
        }
        generateInterface(components)
    }

    private fun ViewGroup.createOrUpdateBigTitleLabel(text: String, id: String) {
        val binding: QuestionBigTitleBinding =
            QuestionBigTitleBinding.inflate(LayoutInflater.from(context))
        this.findViewWithTag(id) ?: binding.formBigTitleLabelContainer.rootView.apply { tag = id }
            .also { this.addView(it) }
        binding.bigTitleLabelTextview.text = text
    }

    private fun ViewGroup.createOrUpdateSmallTitleLabel(text: String, id: String) {
        val binding: QuestionSmallTitleBinding =
            QuestionSmallTitleBinding.inflate(LayoutInflater.from(context))

        this.findViewWithTag(id) ?: binding.formSmallTitleLabelContainer.rootView.apply { tag = id }
            .also { this.addView(it) }
        binding.title.text = text
    }

    private fun ViewGroup.createOrUpdateImage(id: String, imageName: String) {
        val binding: QuestionImageBinding = QuestionImageBinding.inflate(LayoutInflater.from(context))

        this.findViewWithTag(id) ?: binding.formImageviewContainer.rootView.apply { tag = id }
            .also { this.addView(it) }

        binding.imageview.setImageResource(getImageResource(imageName))
    }

    private fun ViewGroup.createOrUpdateQuestionnaire(
        id: String,
        text: List<String>,
        answer: QuestionnaireAnswer?
    ) {
        val binding: QuestionnaireChecklistBinding =
            QuestionnaireChecklistBinding.inflate(LayoutInflater.from(context))

        this.findViewWithTag(id) ?: binding.radioGroup.rootView.apply { tag = id }
            .also {
                binding.radioButtonSad.text = text[0]
                binding.radioButtonIndifferent.text = text[1]
                binding.radioButtonHappy.text = text[2]
                println("Answer when empty: $answer")
                if (answer == null) {
                    setQuestionnaireAnswered(false)
                }
                binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
                    when (checkedId) {
                        binding.radioButtonSad.id -> {
                            getApplication().questionViewModel.setQuestionnaireAnswer(
                                id,
                                QuestionnaireAnswer.WrongAns2,
                                text[0]
                            )
                            setQuestionnaireAnswered(true)
                        }
                        binding.radioButtonIndifferent.id -> {
                            getApplication().questionViewModel.setQuestionnaireAnswer(
                                id,
                                QuestionnaireAnswer.WrongAns1,
                                text[1]
                            )
                            setQuestionnaireAnswered(true)
                        }
                        binding.radioButtonHappy.id -> {
                            getApplication().questionViewModel.setQuestionnaireAnswer(
                                id,
                                QuestionnaireAnswer.CorrectAns,
                                text[2]
                            )
                            setQuestionnaireAnswered(true)
                        }
                    }
                }
                when (answer) {
                    QuestionnaireAnswer.CorrectAns -> binding.radioGroup.check(binding.radioButtonHappy.id)
                    QuestionnaireAnswer.WrongAns1-> binding.radioGroup.check(binding.radioButtonIndifferent.id)
                    QuestionnaireAnswer.WrongAns2 -> binding.radioGroup.check(binding.radioButtonSad.id)
                    else -> {
                        binding.radioGroup.check(-1)
                    }
                }
                this.addView(it)
            }
    }

    private fun ViewGroup.setQuestionnaireAnswered(isAnswered: Boolean) {
        getApplication().questionViewModel.question.data.questionnaireIsAnswered.answered = isAnswered
    }

    private fun ViewGroup.getApplication(): MainApplication {
        return context.applicationContext as MainApplication
    }

    private fun ViewGroup.createOrUpdateQuestionnaireResult(
        id: String,
        answers: MutableList<AnswerWithPhoto>?,
    ) {
        val binding: QuestionGroundProfileResultBinding =
            QuestionGroundProfileResultBinding.inflate(LayoutInflater.from(context))
        this.findViewWithTag(id) ?: binding.questionnaireResultContainer.rootView.apply { tag = id }
            .also {
                if (answers != null) {
                    for (i in 0 until answers.size) {
                        val imageLayout: QuestionQuestionnaireResultItemBinding =
                            QuestionQuestionnaireResultItemBinding.inflate(
                                LayoutInflater.from(context)
                            )
                        when (answers[i].answer) {
                            QuestionnaireAnswer.CorrectAns -> {
                                imageLayout.tableRow.setBackgroundResource(R.drawable.questionnaire_correctans_selected)
                            }
                            QuestionnaireAnswer.WrongAns1 -> {
                                imageLayout.tableRow.setBackgroundResource(R.drawable.questionnaire_wrongans1_selected)
                            }
                            QuestionnaireAnswer.WrongAns2 -> {
                                imageLayout.tableRow.setBackgroundResource(R.drawable.questionnaire_wrongans2_selected)
                            }
                            null -> println("Null")
                        }
                        imageLayout.textView.text = answers[i].text
                        binding.questionnaireResultContainer.addView(imageLayout.questionnaireResultItem)
                    }
                }
                this.addView(it)
            }
    }

    private fun ViewGroup.getImageResource(name: String): Int {
        return context.resources.getIdentifier("drawable/$name", null, context.packageName)
    }

    private  fun getStringById(context: Context, id: String?) : String{
        val res: Resources = context.resources
        return res.getString(res.getIdentifier(id,"string", context.packageName))
    }
    private  fun getStringByIdList(context: Context, id: String?) : List<String>{
        val res: Resources = context.resources
        var idArray: List<String> = emptyList()
        when(id) {
            "[ShapeQuestion1Answer1]" -> idArray = res.getStringArray(R.array.ShapeQuestion1Answer1).toList()
            "[ShapeQuestion2Answer1]" -> idArray = res.getStringArray(R.array.ShapeQuestion2Answer1).toList()
            "[ColorQuestion1Answer1]" -> idArray = res.getStringArray(R.array.ColorQuestion1Answer1).toList()
            "[ColorQuestion2Answer1]" -> idArray = res.getStringArray(R.array.ColorQuestion2Answer1).toList()
            "[CountingQuestion1Answer1]" -> idArray = res.getStringArray(R.array.CountingQuestion1Answer1).toList()
            "[CountingQuestion2Answer1]" -> idArray = res.getStringArray(R.array.CountingQuestion2Answer1).toList()
            else -> print("No Key in strings file")
        }
            return idArray
        }
}