package com.thiruppathik.pagingsample.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.thiruppathik.pagingsample.model.User

/**
 * Created by Thiruppathi.K on 6/24/2018.
 */

@Database(
        entities = [User::class],
        version = 1,
        exportSchema = false
)
abstract class UserDatabase : RoomDatabase() {

    abstract fun reposDao(): UserDao

    companion object {

        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getInstance(context: Context): UserDatabase =
                INSTANCE ?: synchronized(this) {
                    INSTANCE
                            ?: buildDatabase(context).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        UserDatabase::class.java, "User.db")
                        .build()
    }
}