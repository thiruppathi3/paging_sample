package com.thiruppathik.pagingsample.ui

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.thiruppathik.pagingsample.R
import com.thiruppathik.pagingsample.model.User
import kotlinx.android.synthetic.main.user_list_item.view.*

/**
 * Created by Thiruppathi.K on 6/23/2018.
 */


class UserPagedListAdapter(_itemClickListener: ItemClickListener) : PagedListAdapter<User, UserViewHolder>(diffCallback) {
    val itemClickListener: ItemClickListener?

    init {
        itemClickListener = _itemClickListener
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        var userData = getItem(position)
        if (userData != null) {
            holder.bindTo(userData, itemClickListener!!)
        } else {
            holder.clear()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder = UserViewHolder(parent)

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean = oldItem == newItem
        }
    }
}

class UserViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.user_list_item, parent, false)) {
    private var itemClickListener: ItemClickListener? = null

    init {
        itemView.container.setOnClickListener {
            if (itemClickListener != null) {
                itemClickListener!!.onItemClick(adapterPosition, item)
            }
        }
    }


    var item: User? = null

    fun clear() {
        itemView.name.text = null
        itemView.image.setImageURI(null)
    }

    fun bindTo(item: User?, itemClickListener: ItemClickListener) {
        this.item = item
        this.itemClickListener = itemClickListener
        itemView.name.text = item?.firstName + " " + item?.lastName
                ?: "Oops.. Something went wrong :("

        val imageUrl = item?.avatar
        if (null != imageUrl) {
            itemView.image.visibility = View.VISIBLE
            Glide.with(itemView.context)
                    .load(imageUrl)
                    .apply(RequestOptions().circleCrop().placeholder(R.drawable.default_image))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(itemView.image)
        } else {
            Glide.with(itemView.context).clear(itemView.image)
            itemView.image.setImageResource(R.drawable.default_image)
        }
    }
}