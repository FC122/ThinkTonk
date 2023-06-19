package model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quizes")
data class Quiz(
    @PrimaryKey(autoGenerate = true)
    var id: Long=0,
    @ColumnInfo(name="name")
    val name:String,
    @ColumnInfo(name="themes")
    val themes:String,
)
