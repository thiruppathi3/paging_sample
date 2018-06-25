package com.thiruppathik.pagingsample.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by Thiruppathi.K on 6/23/2018.
 */

@Entity(tableName = "users")
data class User(@PrimaryKey @field:SerializedName("id") var id: Int,
                @field:SerializedName("first_name") var firstName: String,
                @field:SerializedName("last_name") var lastName: String,
                @field:SerializedName("avatar") var avatar: String,
                @field:SerializedName("page") var page: Int) : Serializable