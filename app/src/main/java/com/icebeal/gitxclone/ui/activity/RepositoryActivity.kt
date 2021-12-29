package com.icebeal.gitxclone.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.icebeal.gitxclone.R
import com.icebeal.gitxclone.databinding.ActivityRepositoryBinding
import com.icebeal.gitxclone.ui.adapter.RepoAdapter
import com.icebeal.gitxclone.ui.viewModel.UserViewModel

class RepositoryActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRepositoryBinding
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRepositoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        val username = intent.getStringExtra(USERNAME)
        userViewModel.setRepo(username.toString())

        supportActionBar?.title = getString(R.string.repo_title, username)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val repoAdapter = RepoAdapter()
        repoAdapter.notifyDataSetChanged()

        with(binding.rvRepos){

            adapter = repoAdapter
            layoutManager = LinearLayoutManager(this@RepositoryActivity)

        }

        userViewModel.getRepo().observe(this, {

            if(it!=null){

                repoAdapter.setData(it)
                binding.imgEmpty.visibility = View.GONE

            }

        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == android.R.id.home){

            finish()

        }

        return super.onOptionsItemSelected(item)
    }

    companion object{

        const val USERNAME = "username"

    }

}