package com.thiruppathik.pagingsample.data

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.content.Context
import com.thiruppathik.pagingsample.model.DataLoadState
import com.thiruppathik.pagingsample.model.User
import com.thiruppathik.pagingsample.ui.UserDataFactory

/**
 * Created by Thiruppathi.K on 6/23/2018.
 */

class UserRepositoryImpl(_context: Context) : UserRepository {
    private val userDataFactory: UserDataFactory
    private lateinit var users: LiveData<PagedList<User>>

    init {
        userDataFactory = UserDataFactory(_context)
    }

    companion object {
        private val PAGE_SIZE: Int = 3
    }

    override fun getDataLoadStatus(): LiveData<DataLoadState> {
        return Transformations.switchMap(userDataFactory.dataSourceLiveData) { dataSource -> dataSource.loadState }
    }

    override fun getUsers(): LiveData<PagedList<User>> {
        val config: PagedList.Config = PagedList.Config.Builder()
                .setInitialLoadSizeHint(PAGE_SIZE * 2)
                .setPageSize(PAGE_SIZE).build()

        users = LivePagedListBuilder(userDataFactory, config).setInitialLoadKey(1).build()
        return users
    }
}