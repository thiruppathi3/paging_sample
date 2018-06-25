package com.thiruppathik.pagingsample.api

import com.google.gson.annotations.SerializedName
import com.thiruppathik.pagingsample.model.User

/**
 * Created by Thiruppathi.K on 6/23/2018.
 */

data class UserResponse(var page: Int,
                        @SerializedName("per_page") var perPage: Int,
                        var total: Int,
                        @SerializedName("total_pages") var totalPages: Int,
                        var data: List<User>)