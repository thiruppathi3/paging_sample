package com.thiruppathik.pagingsample.ui

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList
import com.thiruppathik.pagingsample.data.UserRepository
import com.thiruppathik.pagingsample.data.UserRepositoryImpl
import com.thiruppathik.pagingsample.model.DataLoadState
import com.thiruppathik.pagingsample.model.User

/**
 * Created by Thiruppathi.K on 6/23/2018.
 */


class UserListViewModel(_application: Application) : AndroidViewModel(_application) {

    private val userRepository: UserRepository

    init {
        userRepository = UserRepositoryImpl(_application)
    }

    fun getUserList(): LiveData<PagedList<User>> {
        return userRepository.getUsers()
    }

    fun dataLoadStatus(): LiveData<DataLoadState> {
        return userRepository.getDataLoadStatus()
    }
}