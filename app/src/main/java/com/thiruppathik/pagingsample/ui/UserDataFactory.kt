package com.thiruppathik.pagingsample.ui

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import android.content.Context
import com.thiruppathik.pagingsample.Injection
import com.thiruppathik.pagingsample.api.UserService
import com.thiruppathik.pagingsample.model.User

/**
 * Created by Thiruppathi.K on 6/23/2018.
 */


class UserDataFactory(_context: Context) : DataSource.Factory<Int, User>() {
    private val context: Context

    init {
        context = _context
    }

    val dataSourceLiveData: MutableLiveData<UserNetworkDataSource> = MutableLiveData()
    override fun create(): DataSource<Int, User> {
        val userDataSource = UserNetworkDataSource(UserService.create(), Injection.provideCache(context))
        dataSourceLiveData.postValue(userDataSource)
        return userDataSource
    }
}