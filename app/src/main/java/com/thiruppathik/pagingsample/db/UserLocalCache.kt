package com.thiruppathik.pagingsample.db

import android.arch.lifecycle.LiveData
import android.util.Log
import com.thiruppathik.pagingsample.model.User
import java.util.concurrent.Executor

class UserLocalCache(private val userDao: UserDao, private val ioExecutor: Executor) {
    fun insert(userData: List<User>, insertFinished: () -> Unit) {
        ioExecutor.execute {
            Log.d("UserCache", "inserting ${userData.size} data")
            userDao.insert(userData)
            insertFinished()
        }
    }

    fun userDataByPage(page: Int): LiveData<List<User>> {
        return userDao.userDataByPage(page)
    }
}