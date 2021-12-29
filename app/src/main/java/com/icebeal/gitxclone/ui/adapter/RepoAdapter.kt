package com.icebeal.gitxclone.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.icebeal.gitxclone.R
import com.icebeal.gitxclone.data.model.UserRepos
import com.icebeal.gitxclone.databinding.RepositoryLayoutBinding
import com.icebeal.gitxclone.ui.activity.RepositoryDetail

class RepoAdapter : RecyclerView.Adapter<RepoAdapter.RepoViewHolder>() {

    private val repoList = ArrayList<UserRepos>()

    fun setData(item:ArrayList<UserRepos>){

        repoList.clear()
        repoList.addAll(item)

        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoAdapter.RepoViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.repository_layout, parent, false)

        return RepoViewHolder(view)

    }

    override fun onBindViewHolder(holder: RepoAdapter.RepoViewHolder, position: Int) {

        holder.bind(repoList[position])

    }

    override fun getItemCount(): Int = repoList.size

    class RepoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private val binding = RepositoryLayoutBinding.bind(itemView)

        fun bind(userRepos: UserRepos){

            with(itemView){

                binding.tvRepoName.text = resources.getString(R.string.repo_name, userRepos.repos_name)
                binding.tvRepoDescription.text = resources.getString(R.string.repo_description, userRepos.description)

                if(userRepos.fork){

                    binding.tvFork.text = resources.getString(R.string.forked)
                    binding.forkIcon.setImageResource(R.drawable.ic_baseline_account_tree_24)

                } else {

                    binding.tvFork.text = resources.getString(R.string.master)
                    binding.forkIcon.setImageResource(R.drawable.ic_baseline_lock_24)

                }

                binding.tvWatch.text = resources.getQuantityString(R.plurals.repo_watch, userRepos.watch, userRepos.watch, userRepos.watch)
                binding.tvStar.text = resources.getQuantityString(R.plurals.repo_star, userRepos.star, userRepos.star, userRepos.star)

                binding.tvLanguage.text = resources.getString(R.string.repo_language, userRepos.language)

                setOnClickListener {

                    val toRepoWeb = Intent(context, RepositoryDetail::class.java)
                    toRepoWeb.putExtra(RepositoryDetail.URL, userRepos.url)

                    context.startActivity(toRepoWeb)

                }

            }

        }

    }

}