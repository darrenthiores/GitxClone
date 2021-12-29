package com.icebeal.gitxclone.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.icebeal.gitxclone.R
import com.icebeal.gitxclone.databinding.ActivityRepositoryDetailBinding

class RepositoryDetail : AppCompatActivity() {

    private lateinit var binding:ActivityRepositoryDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRepositoryDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.repo_detail_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val url = intent.getStringExtra(URL)

        with(binding.webView){

            loadUrl(url.toString())
            settings.javaScriptEnabled = true

        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == android.R.id.home){

            finish()

        }

        return super.onOptionsItemSelected(item)
    }

    companion object{

        const val URL = "url"

    }

}