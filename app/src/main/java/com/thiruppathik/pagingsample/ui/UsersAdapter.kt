package com.thiruppathik.pagingsample.ui

import android.support.v7.recyclerview.extensions.ListAdapter
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

class UsersAdapter : ListAdapter<User, UsersAdapter.UserViewHolder>(USER_DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position)
        if (user != null) {
            holder.bind(user)
        }
    }

    companion object {
        private val USER_DIFF_CALLBACK = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
                    oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
                    oldItem == newItem
        }
    }

    class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var user: User? = null

        init {
            view.setOnClickListener {
                //                user?.url?.let { url ->
//                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//                    view.context.startActivity(intent)
//                }
            }
        }

        fun bind(userData: User?) {
            if (userData == null) {
                itemView.name.text = "MR/MRS XXXX"
            } else {
                showRepoData(userData)
            }
        }

        private fun showRepoData(userData: User) {
            this.user = userData
            itemView.name.text = userData.firstName + " " + userData.lastName
            val imageUrl = userData.avatar
            if (null != imageUrl) {
                itemView.image.visibility = View.VISIBLE
                Glide.with(itemView.context)
                        .load(imageUrl)
                        .apply(RequestOptions().circleCrop())
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(itemView.image)
//                            .placeholder(R.drawable.empty_placeholder))
            } else {
                Glide.with(itemView.context).clear(itemView.image)
//            itemView.image.setImageResource(R.drawable.empty_placeholder)
            }

        }

        companion object {
            fun create(parent: ViewGroup): UserViewHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.user_list_item, parent, false)
                return UserViewHolder(view)
            }
        }
    }
}