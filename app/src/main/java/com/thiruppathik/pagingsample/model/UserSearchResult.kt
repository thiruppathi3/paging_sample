package com.thiruppathik.pagingsample.model

import android.arch.lifecycle.LiveData

data class UserSearchResult(
        val data: LiveData<List<User>>,
        val networkErrors: LiveData<String>)