package com.icebeal.gitxclone.data.db

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.icebeal.gitxclone.data.model.User

@Dao
interface UserDao {

    @Insert
    suspend fun insert(user:User)

    @Delete
    suspend fun delete(user: User)

    @Query("SELECT EXISTS (SELECT 1 FROM user_favorite WHERE username=:username)")
    fun checkFavorite(username:String) : LiveData<Int>

    @Query("SELECT * FROM user_favorite")
    fun queryAll() : LiveData<List<User>>

    @Query("SELECT * FROM user_favorite")
    fun queryAllCP() : Cursor

    @Query("SELECT * FROM user_favorite")
    fun query() : List<User>

}