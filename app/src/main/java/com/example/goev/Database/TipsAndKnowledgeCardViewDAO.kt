package com.example.goev.Database
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.goev.Database.TipsAndKnowledgeCardView

@Dao
interface TipsAndKnowledgeCardViewDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(cv: TipsAndKnowledgeCardView)


}