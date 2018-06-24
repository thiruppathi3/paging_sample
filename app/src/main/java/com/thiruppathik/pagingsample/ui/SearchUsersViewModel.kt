package com.thiruppathik.pagingsample.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.thiruppathik.pagingsample.data.UserRepository
import com.thiruppathik.pagingsample.model.User
import com.thiruppathik.pagingsample.model.UserSearchResult

class SearchUsersViewModel(private val repository: UserRepository) : ViewModel() {

    companion object {
        private const val VISIBLE_THRESHOLD = 5
    }

    private val queryLiveData = MutableLiveData<Int>()
    private val repoResult: LiveData<UserSearchResult> = Transformations.map(queryLiveData, {
        repository.search(it)
    })

    val users: LiveData<List<User>> = Transformations.switchMap(repoResult,
            { it -> it.data })
    val networkErrors: LiveData<String> = Transformations.switchMap(repoResult,
            { it -> it.networkErrors })

    fun searchRepo(page: Int) {
        queryLiveData.postValue(page)
    }

    fun listScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int) {
        if (visibleItemCount + lastVisibleItemPosition + VISIBLE_THRESHOLD >= totalItemCount) {
            val page = lastRequestedPage()
            if (page != null) {
                repository.requestMore(page)
            }
        }
    }

    fun lastRequestedPage(): Int? = queryLiveData.value
}