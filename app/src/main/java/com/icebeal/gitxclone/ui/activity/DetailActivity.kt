package com.icebeal.gitxclone.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayoutMediator
import com.icebeal.gitxclone.R
import com.icebeal.gitxclone.data.model.User
import com.icebeal.gitxclone.databinding.ActivityDetailBinding
import com.icebeal.gitxclone.ui.adapter.SectionPagerAdapter
import com.icebeal.gitxclone.ui.provider.UserProvider
import com.icebeal.gitxclone.ui.viewModel.DetailViewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel

    private var isFavorite = false
    private var link:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        val username = DetailActivityArgs.fromBundle(intent.extras as Bundle).username
        detailViewModel.getUserDetail(username)

        link = "https://www.icebeal.com/user/$username"

        supportActionBar?.title = username
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        detailViewModel.userDetail().observe(this, {

            if(it!=null){

                with(binding){

                    Glide.with(this@DetailActivity)
                        .load(it.avatar)
                        .apply(RequestOptions().override(300, 300))
                        .into(detailAvatar)

                    tvName.text = it.name
                    tvDetailUsername.text = it.username
                    tvBio.text = it.bio
                    tvFollower.text = getString(R.string.followers_num, it.followers)
                    tvFollowing.text = getString(R.string.following_num, it.following)
                    tvCompany.text = it.company
                    tvLocation.text = it.location
                    tvEmail.text = it.email
                    tvRepository.text = resources.getQuantityString(R.plurals.repo, it.repos, it.repos, it.repos)

                    detailProgressBar.visibility = View.GONE

                    favoriteListener(User(it.username, it.avatar))

                }

            }

        })

        val adapter = SectionPagerAdapter(this)
        adapter.username = username

        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabs, binding.viewPager){tab, position ->

            tab.text = resources.getString(TITLE[position])

        }.attach()

        detailViewModel.checkFavorite(username).observe(this, {

            if(it==1){

                isFavorite = true
                binding.btFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)


            } else {

                isFavorite = false
                binding.btFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)

            }

        })

        binding.btRepo.setText(getString(R.string.bt_repo, username))

        binding.btRepo.setOnClickListener {

            val toRepo = Intent(this, RepositoryActivity::class.java)
            toRepo.putExtra(RepositoryActivity.USERNAME, username)

            startActivity(toRepo)

        }

    }

    private fun favoriteListener(user:User){

        binding.btFavorite.setOnClickListener {

            if (isFavorite){

                detailViewModel.delete(user)
                contentResolver.notifyChange(UserProvider.CONTENT_URI, null)

            } else {

                detailViewModel.insert(user)
                contentResolver.notifyChange(UserProvider.CONTENT_URI, null)

            }

        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.detail_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){

            android.R.id.home -> finish()

            R.id.menu_share -> {

                val sendIntent:Intent = Intent().apply {

                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, getString(R.string.share_message, link))
                    type = "text/plain"

                }

                val shareIntent = Intent.createChooser(sendIntent, null)

                startActivity(shareIntent)

            }

        }

        return super.onOptionsItemSelected(item)
    }

    companion object{

        @StringRes
        private val TITLE = intArrayOf(
            R.string.tab_title_1,
            R.string.tab_title_2
        )

    }

}