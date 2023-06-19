package model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.thinktonk.Answer

@Entity(
    tableName = "questions",
    foreignKeys = [ForeignKey(
        entity = Quiz::class,
        parentColumns = ["id"],
        childColumns = ["quizId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Question(
    @PrimaryKey(autoGenerate = true)
    var id:Long = 0,
    @ColumnInfo(name="quizId")
    val quizId:Long,
    @ColumnInfo(name="question")
    val question:String,
    @ColumnInfo(name="answers")
    val answer: String,
    @ColumnInfo(name="correctAnswerIndex")
    val correctAnswerIndex: Int,
    @ColumnInfo(name="url")
    val url:String
)
