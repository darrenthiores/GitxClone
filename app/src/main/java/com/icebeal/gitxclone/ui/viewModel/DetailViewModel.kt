package com.icebeal.gitxclone.ui.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.icebeal.gitxclone.data.db.UserDb
import com.icebeal.gitxclone.data.model.User
import com.icebeal.gitxclone.data.model.UserDetail
import com.icebeal.gitxclone.data.repositories.UserRepository
import com.icebeal.gitxclone.data.retrofit.RetrofitBuilder
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : AndroidViewModel(application){

    private val userDetail = MutableLiveData<UserDetail>()

    private val userFollower = MutableLiveData<ArrayList<User>>()
    private val userFollowing = MutableLiveData<ArrayList<User>>()

    private val userDao = UserDb.getInstance(application).userDao()
    private val repository = UserRepository(userDao)

    fun insert(user: User) = viewModelScope.launch {

        repository.insert(user)

    }

    fun delete(user: User) = viewModelScope.launch {

        repository.delete(user)

    }

    fun checkFavorite(username: String) : LiveData<Int> = repository.checkFavorite(username)

    fun getUserDetail(username:String) = viewModelScope.launch{

        RetrofitBuilder.api.getDetail(username=username)
            .enqueue(object : Callback<UserDetail>{

                override fun onResponse(call: Call<UserDetail>, response: Response<UserDetail>) {

                    userDetail.postValue(response.body())

                }

                override fun onFailure(call: Call<UserDetail>, t: Throwable) {

                    Log.d("Exception", t.message.toString())

                }

            })

    }

    fun userDetail() : LiveData<UserDetail> = userDetail

    fun getFollower(username: String) = viewModelScope.launch {

        RetrofitBuilder.api.getFollower(username = username)
            .enqueue(object : Callback<ArrayList<User>>{

                override fun onResponse(call: Call<ArrayList<User>>, response: Response<ArrayList<User>>) {

                    userFollower.postValue(response.body())

                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {

                    Log.d("Exception", t.message.toString())

                }

            })

    }

    fun userFollower() : LiveData<ArrayList<User>> = userFollower

    fun getFollowing(username: String) = viewModelScope.launch {

        RetrofitBuilder.api.getFollowing(username = username)
            .enqueue(object : Callback<ArrayList<User>>{

                override fun onResponse(call: Call<ArrayList<User>>, response: Response<ArrayList<User>>) {

                    userFollowing.postValue(response.body())

                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {

                    Log.d("Exception", t.message.toString())

                }

            })

    }

    fun userFollowing() : LiveData<ArrayList<User>> = userFollowing

}