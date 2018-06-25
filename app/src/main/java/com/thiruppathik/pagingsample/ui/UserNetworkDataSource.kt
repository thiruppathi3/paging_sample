package com.thiruppathik.pagingsample.ui

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PageKeyedDataSource
import android.util.Log
import com.thiruppathik.pagingsample.api.UserService
import com.thiruppathik.pagingsample.db.UserLocalCache
import com.thiruppathik.pagingsample.model.DataLoadState
import com.thiruppathik.pagingsample.model.User
import java.io.IOException

/**
 * Created by Thiruppathi.K on 6/23/2018.
 */


class UserNetworkDataSource(_userService: UserService, _cache: UserLocalCache) : PageKeyedDataSource<Int, User>() {

    private val userService: UserService
    private val cache: UserLocalCache
    val loadState: MutableLiveData<DataLoadState>

    init {
        userService = _userService
        cache = _cache
        loadState = MutableLiveData()
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, User>) {
        var result: Pair<List<User>, Int?> = getData(1, false)
        callback.onResult(result.first, null, 2)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, User>) {

        var result: Pair<List<User>, Int?> = getData(params.key, true)
        callback.onResult(result.first, result.second)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, User>) {
        var result: Pair<List<User>, Int?> = getData(params.key, false)
        callback.onResult(result.first, result.second)
    }

    fun getData(page: Int, isPrev: Boolean): Pair<List<User>, Int?> {
        var data: List<User>? = ArrayList<User>()
        var index: Int? = null
        try {
            loadState.postValue(DataLoadState.LOADING)
            val response = userService.getUsers(page).execute()
            data = response.body()!!.data as ArrayList<User>
            if (data != null) {
                data.forEach {
                    it.page = response.body()!!.page
                }
                val prevKey = (if (page > 1) page - 1 else null)
                val nextKey = (if (page < response.body()!!.totalPages) page + 1 else null)
                cache.insert(data, {
                    Log.d("DataInserted in Cache", "page 1")
                })
                loadState.postValue(DataLoadState.LOADED)

                index = if (isPrev) prevKey else nextKey
            } else {
                loadState.postValue(DataLoadState.FAILED)
                data = emptyList()
                index = null
            }
            return Pair(data, index)
        } catch (ex: IOException) {
            data = cache.userDataByPage(page)
            if (data != null && data.isNotEmpty()) {
                val prevKey = (if (page > 1) page - 1 else null)
                val nextKey = page + 1
                loadState.postValue(DataLoadState.LOADED)
                index = if (isPrev) prevKey else nextKey
            } else {
                data = emptyList()
                index = null
                loadState.postValue(DataLoadState.FAILED)
            }
            return Pair(data, index)
        }
    }
}