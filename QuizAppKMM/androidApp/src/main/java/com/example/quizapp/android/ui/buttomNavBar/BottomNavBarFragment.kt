package com.example.quizapp.android.ui.buttomNavBar

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.widget.ImageView
import android.widget.LinearLayout
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.quizapp.android.MainApplication
import com.example.quizapp.android.R
import com.example.quizapp.android.databinding.FragmentBottomNavBarBinding

class BottomNavBarFragment : Fragment() {
    private var fragmentBottomNavBarBinding: FragmentBottomNavBarBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bottom_nav_bar, container, false)
        val binding = FragmentBottomNavBarBinding.bind(view)
        val application = (activity?.application as MainApplication)
        var currentScreen: Int = application.questionViewModel.state.value.currentScreen
        val totalScreens: Int = application.questionViewModel.state.value.totalScreens

        binding.bottomNavbarBack.setOnClickListener {
            application.questionViewModel.previousScreen()
            currentScreen = application.questionViewModel.state.value.currentScreen
            setContent(binding, totalScreens, currentScreen)
            application.questionViewModel.question.data.questionnaireIsAnswered.answered = null
        }

        binding.bottomNavbarForward.setOnClickListener {
            val isAnswered: Boolean? =
                application.questionViewModel.question.data.questionnaireIsAnswered.answered

            if (isAnswered == false) {
                showDialog()
            } else {
                application.questionViewModel.nextScreen()
                currentScreen = application.questionViewModel.state.value.currentScreen
                setContent(binding, totalScreens, currentScreen)
            }
        }

        view.post {
            setContent(binding, totalScreens, currentScreen)
        }

        return view
    }

    private  fun getStringById(context: Context, id: String?) : String{
        val res: Resources = context.resources
        return res.getString(res.getIdentifier(id,"string", context.packageName))
    }

    private fun showDialog() {
        val customDialog = AlertDialog.Builder(context)
        customDialog.setMessage(getString(R.string.answer_required))
            .setPositiveButton(R.string.ok,
                DialogInterface.OnClickListener { dialog, _ ->
                    dialog.dismiss()
                })

        customDialog.create()
        customDialog.show()
    }

    private fun setContent(
        binding: FragmentBottomNavBarBinding,
        totalScreens: Int,
        currentScreen: Int
    ) {
        setProgress(binding, totalScreens, currentScreen)
        setText(binding, totalScreens, currentScreen)
    }

    private fun setProgress(
        binding: FragmentBottomNavBarBinding,
        totalScreens: Int,
        currentScreen: Int
    ) {
        binding.progressLayout.removeAllViews()
        val usableContainerWidth = (binding.progressLayout.width * 0.8).toInt()
        for (i in 0 until totalScreens) {
            val progressItem = ImageView(this.requireContext())
            progressItem.layoutParams =
                LinearLayout.LayoutParams(usableContainerWidth / totalScreens, 50)
            (progressItem.layoutParams as LinearLayout.LayoutParams).setMargins(2, 0, 2, 0)
            progressItem.setBackgroundResource(if (i <= currentScreen) R.color.tree_green else R.color.colorPurple)
            if (i == 0) {
                progressItem.background = getDrawableWithRadius(R.color.tree_green, true)
            }
            if (i == totalScreens - 1) {
                if (i == currentScreen) {
                    progressItem.background = getDrawableWithRadius(R.color.tree_green, false)
                } else {
                    progressItem.background =
                        getDrawableWithRadius(R.color.colorPurple, false)
                }
            }
            binding.progressLayout.addView(progressItem)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setText(
        binding: FragmentBottomNavBarBinding,
        totalScreens: Int,
        currentScreen: Int
    ) {
        binding.bottomNavbarProgressText.text =
            "${currentScreen + 1} ${getString(R.string.bottom_navbar_text)} $totalScreens"
    }

    private fun getDrawableWithRadius(color: Int, isStart: Boolean): Drawable {
        val gradientDrawable = GradientDrawable()
        if (isStart) {
            gradientDrawable.cornerRadii = floatArrayOf(20f, 20f, 0f, 0f, 0f, 0f, 20f, 20f)
        } else {
            gradientDrawable.cornerRadii = floatArrayOf(0f, 0f, 20f, 20f, 20f, 20f, 0f, 0f)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            gradientDrawable.setColor(
                resources.getColor(
                    color,
                    getActivity()?.getTheme()
                )
            )
        } else {
            gradientDrawable.setColor(resources.getColor(color))
        }
        return gradientDrawable
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentBottomNavBarBinding = null
    }
}
