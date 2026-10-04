package com.example.planetquiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class AnswersFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(
            R.layout.fragment_answers,
            container,
            false
        )

        val questionText =
            view.findViewById<TextView>(R.id.answerQuestion)

        val resultText =
            view.findViewById<TextView>(R.id.resultText)

        val detailText =
            view.findViewById<TextView>(R.id.detailText)

        val nextButton =
            view.findViewById<Button>(R.id.nextButton)

        val question =
            arguments?.getString("question") ?: ""

        val selectedAnswer =
            arguments?.getString("selectedAnswer") ?: ""

        val correctAnswer =
            arguments?.getString("correctAnswer") ?: ""

        val questionNumber =
            arguments?.getInt("questionNumber") ?: 0

        // Display the question
        questionText.text = question

        // Display correct or wrong
        if (selectedAnswer == correctAnswer) {

            resultText.setText(R.string.correct)

        } else {

            resultText.text =
                "${getString(R.string.wrong)}\nThe correct answer is $correctAnswer."
        }

        // Display the detailed explanation
        detailText.text = when (correctAnswer) {

            "JUPITER" ->
                getString(R.string.jupiter_details)

            "SATURN" ->
                getString(R.string.saturn_details)

            "URANUS" ->
                getString(R.string.uranus_details)

            else -> ""
        }

        // Go to the next question or back to the question selection screen
        if (questionNumber < 2) {

            nextButton.setText(R.string.next_question)

            nextButton.setOnClickListener {

                val questionsFragment =
                    QuestionsFragment()

                val bundle = Bundle()

                bundle.putInt(
                    "questionNumber",
                    questionNumber + 1
                )

                questionsFragment.arguments = bundle

                parentFragmentManager.beginTransaction()
                    .replace(
                        R.id.fragment_container,
                        questionsFragment
                    )
                    .commit()
            }

        } else {

            // Last question: return directly to question selection
            nextButton.setText(R.string.finish)

            nextButton.setOnClickListener {

                val questionsFragment =
                    QuestionsFragment()

                parentFragmentManager.popBackStack(
                    null,
                    androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
                )

                parentFragmentManager.beginTransaction()
                    .replace(
                        R.id.fragment_container,
                        questionsFragment
                    )
                    .commit()
            }
        }

        return view
    }
}