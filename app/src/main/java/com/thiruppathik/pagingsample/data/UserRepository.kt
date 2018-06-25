package com.thiruppathik.pagingsample.data

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList
import com.thiruppathik.pagingsample.model.DataLoadState
import com.thiruppathik.pagingsample.model.User

/**
 * Created by Thiruppathi.K on 6/23/2018.
 */
interface UserRepository {
    fun getUsers(): LiveData<PagedList<User>>
    fun getDataLoadStatus(): LiveData<DataLoadState>
}