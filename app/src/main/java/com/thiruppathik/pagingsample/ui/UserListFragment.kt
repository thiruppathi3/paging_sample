package com.thiruppathik.pagingsample.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.thiruppathik.pagingsample.Injection
import com.thiruppathik.pagingsample.R
import com.thiruppathik.pagingsample.model.User
import kotlinx.android.synthetic.main.user_list_fragment.*


class UserListFragment : Fragment() {
    private lateinit var viewModel: SearchUsersViewModel
    private val adapter = UsersAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.user_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!, Injection.provideViewModelFactory(activity!!))
                .get(SearchUsersViewModel::class.java)

        userlistview.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        userlistview.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        setupScrollListener()

        initAdapter()
        val query = savedInstanceState?.getInt(LAST_SEARCH_QUERY) ?: DEFAULT_QUERY
        viewModel.searchRepo(query)
        initSearch(query)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(LAST_SEARCH_QUERY, viewModel.lastRequestedPage()!!)
    }

    private fun initAdapter() {
        userlistview.adapter = adapter
        viewModel.users.observe(this, Observer<List<User>> {
            Log.d("Activity", "list: ${it?.size}")
            showEmptyView(it?.size == 0)
            adapter.submitList(it)
        })

        viewModel.networkErrors.observe(this, Observer<String> {
            Toast.makeText(activity, "\uD83D\uDE28 Wooops ${it}", Toast.LENGTH_LONG).show()
        })
    }

    private fun initSearch(page: Int) {
        viewModel.searchRepo(page)
        adapter.submitList(null)
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

    private fun setupScrollListener() {
        val layoutManager = userlistview.layoutManager as LinearLayoutManager
        userlistview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = layoutManager.itemCount
                val visibleItemCount = layoutManager.childCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                viewModel.listScrolled(visibleItemCount, lastVisibleItem, totalItemCount)
            }
        })
    }

    companion object {
        private const val LAST_SEARCH_QUERY: String = "last_search_query"
        private const val DEFAULT_QUERY = 1
    }
}