package com.icebeal.consumerapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.icebeal.consumerapp.R
import com.icebeal.consumerapp.databinding.ListLayoutBinding
import com.icebeal.consumerapp.model.User

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private val mainList = ArrayList<User>()

    fun setData(item:ArrayList<User>){

        mainList.clear()
        mainList.addAll(item)

        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_layout, parent, false)

        return UserViewHolder(view)

    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {

        holder.bind(mainList[position])

    }

    override fun getItemCount(): Int = mainList.size

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private val binding = ListLayoutBinding.bind(itemView)

        fun bind(user:User){

            with(binding){

                Glide.with(itemView.context)
                    .load(user.avatar)
                    .apply(RequestOptions().override(90, 90))
                    .into(avatar)

                tvUsername.text = user.username

            }

        }

    }
}