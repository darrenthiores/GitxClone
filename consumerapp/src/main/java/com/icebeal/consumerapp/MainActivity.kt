package com.icebeal.consumerapp

import android.database.ContentObserver
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.icebeal.consumerapp.adapter.UserAdapter
import com.icebeal.consumerapp.databinding.ActivityMainBinding
import com.icebeal.consumerapp.helper.MappingHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userAdapter = UserAdapter()
        userAdapter.notifyDataSetChanged()

        with(binding.rvSearch){

            adapter = userAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)

        }

        val handlerThread = HandlerThread("observer")
        handlerThread.start()

        val handler = Handler(handlerThread.looper)

        val myObserver = object : ContentObserver(handler){

            override fun onChange(selfChange: Boolean) {
                getData()
            }

        }

        contentResolver.registerContentObserver(URI, true, myObserver)

        getData()

    }

    fun getData(){

        GlobalScope.launch(Dispatchers.Main) {

            val query = async(Dispatchers.IO) {

                contentResolver.query(URI, null, null, null, null)

            }

            val cursor = query.await()

            if(cursor!=null){

                val user = MappingHelper.mapCursorToArrayList(cursor)
                userAdapter.setData(user)

                if(user.isEmpty()){

                    binding.imgEmpty.visibility = View.VISIBLE

                } else {

                    binding.imgEmpty.visibility = View.GONE

                }

            }

        }

    }

    companion object{

        private const val AUTHORITY = "com.icebeal.favorite"
        private const val TABLE_DAO = "user_favorite"

        const val USERNAME = "username"
        const val AVATAR = "avatar"

        val URI : Uri = Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_DAO)
            .build()

    }

}