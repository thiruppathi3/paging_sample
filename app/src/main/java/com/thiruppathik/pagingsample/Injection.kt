package com.thiruppathik.pagingsample

import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import com.thiruppathik.pagingsample.api.UserService
import com.thiruppathik.pagingsample.data.UserRepository
import com.thiruppathik.pagingsample.db.UserDatabase
import com.thiruppathik.pagingsample.db.UserLocalCache
import com.thiruppathik.pagingsample.ui.ViewModelFactory
import java.util.concurrent.Executors

object Injection {
    private fun provideCache(context: Context): UserLocalCache {
        val database = UserDatabase.getInstance(context)
        return UserLocalCache(database.reposDao(), Executors.newSingleThreadExecutor())
    }

    private fun provideGithubRepository(context: Context): UserRepository {
        return UserRepository(UserService.create(), provideCache(context))
    }

    fun provideViewModelFactory(context: Context): ViewModelProvider.Factory {
        return ViewModelFactory(provideGithubRepository(context))
    }

}