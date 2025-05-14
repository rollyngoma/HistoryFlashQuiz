package vcmsa.ic.historyflashquiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

class ScoreActivity : AppCompatActivity() {
    private val allQuestions = listOf(
        "1. Nelson Mandela was the president in 1994." to true,
        "2. World War II ended in 1945." to true,
        "3. The Berlin Wall fell in 1989." to true,
        "4. The Titanic sank in 1911." to false,
        "5. The first moon landing was in 1969." to true
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        val score = intent.getIntExtra("SCORE", 0)
        val total = intent.getIntExtra("TOTAL", 5)
        val userAnswers = intent.getBooleanArrayExtra("USER_ANSWERS")?.toList() ?: emptyList() // FIXED HERE

        val scoreText = findViewById<TextView>(R.id.scoreText)
        val feedbackText = findViewById<TextView>(R.id.feedbackText)
        val reviewButton = findViewById<Button>(R.id.reviewButton)
        val exitButton = findViewById<Button>(R.id.exitButton)

        scoreText.text = "Your score: $score out of $total"
        feedbackText.text = if (score >= 3) "Great job!" else "Keep practising!"

        reviewButton.setOnClickListener {
            showReviewDialog(userAnswers)
        }

        exitButton.setOnClickListener {
            finishAffinity()
        }
    }

    private fun showReviewDialog(userAnswers: List<Boolean>) {
        val reviewMessage = buildReviewMessage(userAnswers)

        AlertDialog.Builder(this)
            .setTitle("Review Answers")
            .setMessage(reviewMessage)
            .setPositiveButton("OK", null)
            .show()
    }

    private fun buildReviewMessage(userAnswers: List<Boolean>): String {
        return allQuestions.zip(userAnswers).joinToString("\n\n") { (questionWithAnswer, userAnswer) ->
            val (question, correctAnswer) = questionWithAnswer
            val isCorrect = userAnswer == correctAnswer
            """
            $question
            Your answer: ${if (userAnswer) "True" else "False"}
            Correct answer: ${if (correctAnswer) "True" else "False"}
            ${if (isCorrect) "✓ Correct" else "✗ Wrong"}
            """.trimIndent()
        }
    }
}