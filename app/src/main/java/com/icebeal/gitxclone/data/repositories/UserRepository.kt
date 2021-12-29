package com.icebeal.gitxclone.data.repositories

import androidx.lifecycle.LiveData
import com.icebeal.gitxclone.data.db.UserDao
import com.icebeal.gitxclone.data.model.User

class UserRepository(private val userDao: UserDao) {

    suspend fun insert(user:User){

        userDao.insert(user)

    }

    suspend fun delete(user: User){

        userDao.delete(user)

    }

    fun checkFavorite(username:String) : LiveData<Int>{

        return userDao.checkFavorite(username)

    }

    fun queryAll() : LiveData<List<User>>{

        return userDao.queryAll()

    }

}