package com.thiruppathik.pagingsample.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.thiruppathik.pagingsample.model.User

/**
 * Created by Thiruppathi.K on 6/24/2018.
 */

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: List<User>)

    @Query("SELECT * FROM users WHERE page= " + ":page ORDER BY id ASC")
    fun userDataByPage(page: Int): List<User>

    @Query("SELECT * FROM users WHERE id= " + ":id")
    fun userDataById(id: Int): User
}