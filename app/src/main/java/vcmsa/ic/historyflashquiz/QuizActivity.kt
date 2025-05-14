package vcmsa.ic.historyflashquiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class QuizActivity : AppCompatActivity() {
    private lateinit var flashcards: List<Flashcard>
    private var currentIndex = 0
    private var score = 0
    private val userAnswers = mutableListOf<Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        flashcards = listOf(
            Flashcard("Nelson Mandela was the president in 1994.", true),
            Flashcard("World War II ended in 1945.", true),
            Flashcard("The Berlin Wall fell in 1989.", true),
            Flashcard("The Titanic sank in 1911.", false),
            Flashcard("The first moon landing was in 1969.", true)
        )

        updateQuestion()

        val trueButton = findViewById<Button>(R.id.trueButton)
        val falseButton = findViewById<Button>(R.id.falseButton)
        val nextButton = findViewById<Button>(R.id.nextButton)

        trueButton.setOnClickListener {
            checkAnswer(true)
            userAnswers.add(true)
        }

        falseButton.setOnClickListener {
            checkAnswer(false)
            userAnswers.add(false)
        }

        nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % flashcards.size
            if (currentIndex == 0) {
                val intent = Intent(this, ScoreActivity::class.java).apply {
                    putExtra("SCORE", score)
                    putExtra("TOTAL", flashcards.size)
                    putExtra("USER_ANSWERS", userAnswers.toBooleanArray()) // FIXED HERE
                }
                startActivity(intent)
                finish()
            } else {
                updateQuestion()
            }
        }
    }

    private fun updateQuestion() {
        val questionText = findViewById<TextView>(R.id.questionText)
        questionText.text = flashcards[currentIndex].question
        findViewById<TextView>(R.id.feedbackText).text = ""
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val feedbackText = findViewById<TextView>(R.id.feedbackText)
        val correctAnswer = flashcards[currentIndex].answer

        if (userAnswer == correctAnswer) {
            feedbackText.text = "Correct!"
            score++
        } else {
            feedbackText.text = "Incorrect!"
        }
    }
}

data class Flashcard(
    val question: String,
    val answer: Boolean
)