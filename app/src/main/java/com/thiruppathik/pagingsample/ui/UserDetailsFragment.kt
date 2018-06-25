package com.thiruppathik.pagingsample.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.thiruppathik.pagingsample.R
import com.thiruppathik.pagingsample.model.User
import kotlinx.android.synthetic.main.user_detail_fragment.*

/**
 * Created by Thiruppathi.K on 6/24/2018.
 */

class UserDetailFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.user_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (arguments != null && arguments!!.containsKey("userdata")) {
            val user: User? = arguments!!.getSerializable("userdata") as User
            name.text = user?.firstName + " " + user?.lastName
            val imageUrl = user?.avatar
            if (null != imageUrl) {
                image.visibility = View.VISIBLE
                Glide.with(activity!!)
                        .load(imageUrl)
                        .apply(RequestOptions().placeholder(R.drawable.default_image).circleCrop())
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(image)
            } else {
                Glide.with(activity!!).clear(image)
                image.setImageResource(R.drawable.default_image)
            }
        }
    }
}