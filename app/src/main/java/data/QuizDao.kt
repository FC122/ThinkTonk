package data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import model.Question
import model.Quiz


@Dao
interface QuizDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(quiz: Quiz):Long

    @Query("SELECT * FROM quizes")
    suspend fun getAllQuizzes(): List<Quiz>

    @Query("SELECT * FROM quizes WHERE id=:quizId")
    suspend fun getQuizById(quizId:Long):Quiz

    // Add other necessary queries or operations here
}