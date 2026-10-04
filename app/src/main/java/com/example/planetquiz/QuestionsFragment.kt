package com.example.planetquiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class QuestionsFragment : Fragment() {

    private val questions = arrayOf(
        R.string.largest_planet,
        R.string.most_moons,
        R.string.spins_on_side
    )

    private val correctAnswers = arrayOf(
        "JUPITER",
        "SATURN",
        "URANUS"
    )

    private val planets = arrayOf(
        "MERCURY",
        "VENUS",
        "EARTH",
        "MARS",
        "JUPITER",
        "SATURN",
        "URANUS",
        "NEPTUNE"
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(
            R.layout.fragment_questions,
            container,
            false
        )

        val selectedQuestion =
            arguments?.getInt("questionNumber", -1) ?: -1

        val questionText =
            view.findViewById<TextView>(R.id.questionText)

        val radioGroup =
            view.findViewById<RadioGroup>(R.id.radioGroup)

        val nextButton =
            view.findViewById<Button>(R.id.nextButton)

        if (selectedQuestion == -1) {

            // QUESTION SELECTION SCREEN

            questionText.setText(R.string.choose_question)

            radioGroup.removeAllViews()

            for (i in questions.indices) {

                val button = Button(requireContext())

                button.setText(questions[i])
                button.textSize = 16f

                button.setOnClickListener {

                    val bundle = Bundle()

                    bundle.putInt(
                        "questionNumber",
                        i
                    )

                    val questionFragment =
                        QuestionsFragment()

                    questionFragment.arguments = bundle

                    parentFragmentManager.beginTransaction()
                        .replace(
                            R.id.fragment_container,
                            questionFragment
                        )
                        .addToBackStack(null)
                        .commit()
                }

                radioGroup.addView(button)
            }

            nextButton.visibility = View.GONE

        } else {

            // ANSWER OPTIONS SCREEN

            questionText.setText(questions[selectedQuestion])

            radioGroup.removeAllViews()

            for (planet in planets) {

                val radioButton =
                    RadioButton(requireContext())

                radioButton.text = planet
                radioButton.textSize = 16f
                radioButton.tag = planet

                radioGroup.addView(radioButton)
            }

            nextButton.setText(R.string.submit_answer)

            nextButton.visibility = View.VISIBLE

            nextButton.setOnClickListener {

                val selectedId =
                    radioGroup.checkedRadioButtonId

                if (selectedId != -1) {

                    val selectedButton =
                        view.findViewById<RadioButton>(selectedId)

                    val selectedAnswer =
                        selectedButton.tag.toString()

                    val correctAnswer =
                        correctAnswers[selectedQuestion]

                    val bundle = Bundle()

                    bundle.putString(
                        "question",
                        getString(questions[selectedQuestion])
                    )

                    bundle.putString(
                        "selectedAnswer",
                        selectedAnswer
                    )

                    bundle.putString(
                        "correctAnswer",
                        correctAnswer
                    )

                    bundle.putInt(
                        "questionNumber",
                        selectedQuestion
                    )

                    val answersFragment =
                        AnswersFragment()

                    answersFragment.arguments = bundle

                    parentFragmentManager.beginTransaction()
                        .replace(
                            R.id.fragment_container,
                            answersFragment
                        )
                        .addToBackStack(null)
                        .commit()
                }
            }
        }

        return view
    }
}