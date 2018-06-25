package com.thiruppathik.pagingsample

import android.content.Context
import com.thiruppathik.pagingsample.db.UserDatabase
import com.thiruppathik.pagingsample.db.UserLocalCache
import java.util.concurrent.Executors

/**
 * Created by Thiruppathi.K on 6/24/2018.
 */


object Injection {
    fun provideCache(context: Context): UserLocalCache {
        val database = UserDatabase.getInstance(context)
        return UserLocalCache(database.reposDao(), Executors.newSingleThreadExecutor())
    }
}