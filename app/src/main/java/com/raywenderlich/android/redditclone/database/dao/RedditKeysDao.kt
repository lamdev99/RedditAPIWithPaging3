package com.raywenderlich.android.redditclone.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.raywenderlich.android.redditclone.models.RedditKeys

@Dao
interface RedditKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveRedditKeys(redditKey: RedditKeys)

    @Query("select * from redditKeys order by id desc")
    suspend fun getRedditKeys(): List<RedditKeys>
}