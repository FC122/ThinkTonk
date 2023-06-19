package repository

import android.content.Context
import androidx.room.Room
import data.QuestionDao
import data.QuizDao
import data.QuizDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import model.Question
import model.Quiz

class QuizRepository private constructor(context: Context) {
    private val database: QuizDatabase = Room.databaseBuilder(
        context.applicationContext,
        QuizDatabase::class.java, "quiz-database"
    ).build()

    private val questionDao: QuestionDao = database.questionDao()
    private val quizDao: QuizDao = database.quizDao()

    suspend fun insertQuestion(question: Question) {
        questionDao.insert(question)
    }

    suspend fun getQuestionsByQuizId(quizId: Long): List<Question> {
        return questionDao.getQuestionsByQuizId(quizId)
    }

    suspend fun insertQuiz(quiz: Quiz): Long {
        return quizDao.insert(quiz)
    }

    suspend fun getAllQuizzes(): List<Quiz> = withContext(Dispatchers.IO) {
        return@withContext quizDao.getAllQuizzes()
    }

    suspend fun getQuizById(quizId: Long):Quiz {
        return quizDao.getQuizById(quizId)
    }

    companion object {
        @Volatile
        private var instance: QuizRepository? = null

        fun getInstance(context: Context): QuizRepository {
            return instance ?: synchronized(this) {
                instance ?: QuizRepository(context).also { instance = it }
            }
        }
    }
}
