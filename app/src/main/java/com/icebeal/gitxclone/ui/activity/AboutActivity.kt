package com.icebeal.gitxclone.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.icebeal.gitxclone.R
import com.icebeal.gitxclone.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {

    private lateinit var binding:ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.about_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        with(binding.webView){

            loadUrl("https://github.com/about")
            settings.javaScriptEnabled = true

        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == android.R.id.home){

            finish()

        }

        return super.onOptionsItemSelected(item)
    }

}