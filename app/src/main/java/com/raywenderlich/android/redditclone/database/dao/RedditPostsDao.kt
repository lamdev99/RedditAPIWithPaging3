package com.raywenderlich.android.redditclone.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.raywenderlich.android.redditclone.models.RedditPost


@Dao
interface RedditPostsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePosts(redditPosts: List<RedditPost>)

    @Query("select * from redditPosts")
    fun getPosts(): PagingSource<Int, RedditPost>
}