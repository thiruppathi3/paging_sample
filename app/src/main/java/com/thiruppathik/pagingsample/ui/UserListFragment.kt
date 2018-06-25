package com.thiruppathik.pagingsample.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.thiruppathik.pagingsample.R
import com.thiruppathik.pagingsample.model.DataLoadState
import kotlinx.android.synthetic.main.user_list_fragment.*

/**
 * Created by Thiruppathi.K on 6/24/2018.
 */


class UserListFragment : Fragment() {
    private var adapter: UserPagedListAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return if (view != null) view else
            inflater.inflate(R.layout.user_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState == null) {
            adapter = UserPagedListAdapter(activity as ItemClickListener)
            userlistview.adapter = adapter

            val userListViewModel = ViewModelProviders.of(this).get(UserListViewModel::class.java)
            userlistview.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            userlistview.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
            userListViewModel.dataLoadStatus().observe(this, Observer { dataLoadStatus ->
                if (dataLoadStatus == DataLoadState.FAILED) {
                    Log.d("DataLoadStatus", "Failed")
                    if (adapter != null && adapter!!.itemCount > 0) {
                        showEmptyView(false)
                        Toast.makeText(activity, "We have completed loading your data!!", Toast.LENGTH_SHORT).show()
                    } else {
                        showEmptyView(true)
                    }
                }
            })

            userListViewModel.getUserList().observe(this, Observer { pagedList ->
                if (adapter != null)
                    adapter!!.submitList(pagedList)
            })
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }


    private fun showEmptyView(show: Boolean) {
        if (show) {
            emptyview.visibility = View.VISIBLE
            userlistview.visibility = View.GONE
        } else {
            emptyview.visibility = View.GONE
            userlistview.visibility = View.VISIBLE
        }
    }
}