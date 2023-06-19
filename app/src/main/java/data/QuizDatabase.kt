package data

import androidx.room.Database
import androidx.room.RoomDatabase
import model.Question
import model.Quiz


@Database(entities = [Question::class, Quiz::class], version = 1)
abstract class QuizDatabase : RoomDatabase() {
    abstract fun questionDao(): QuestionDao
    abstract fun quizDao(): QuizDao
}
