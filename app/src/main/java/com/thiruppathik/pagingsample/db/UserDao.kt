package com.thiruppathik.pagingsample.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.thiruppathik.pagingsample.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: List<User>)

    @Query("SELECT * FROM users WHERE page= " + ":page ORDER BY id ASC")
    fun userDataByPage(page: Int): LiveData<List<User>>
}