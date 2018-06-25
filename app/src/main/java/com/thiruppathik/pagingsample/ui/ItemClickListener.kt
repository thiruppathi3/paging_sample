package com.thiruppathik.pagingsample.ui

import com.thiruppathik.pagingsample.model.User

/**
 * Created by Thiruppathi.K on 6/24/2018.
 */

interface ItemClickListener {
    public fun onItemClick(position: Int, user: User?)
}