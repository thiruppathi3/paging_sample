package com.thiruppathik.pagingsample.data

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.thiruppathik.pagingsample.api.UserService
import com.thiruppathik.pagingsample.api.searchUsers
import com.thiruppathik.pagingsample.db.UserLocalCache
import com.thiruppathik.pagingsample.model.UserSearchResult

class UserRepository(private val service: UserService, private val cache: UserLocalCache) {

    private var lastRequestedPage = 1

    private val networkErrors = MutableLiveData<String>()

    private var isRequestInProgress = false

    fun search(page: Int): UserSearchResult {
        Log.d("UserRepository", "Requested page: $page")
        lastRequestedPage = 1
        requestAndSaveData(page)

        val data = cache.userDataByPage(page)

        return UserSearchResult(data, networkErrors)
    }

    fun requestMore(page: Int) {
        requestAndSaveData(page)
    }

    private fun requestAndSaveData(page: Int) {
        if (isRequestInProgress) return

        isRequestInProgress = true
        searchUsers(service, page, { userData ->
            cache.insert(userData, {
                lastRequestedPage++
                isRequestInProgress = false
            })
        }, { error ->
            networkErrors.postValue(error)
            isRequestInProgress = false
        })
    }

//    companion object {
//        private const val NETWORK_PAGE_SIZE = 50
//    }
}