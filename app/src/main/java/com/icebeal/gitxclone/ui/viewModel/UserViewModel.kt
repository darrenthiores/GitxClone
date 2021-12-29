package com.icebeal.gitxclone.ui.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.icebeal.gitxclone.data.db.UserDb
import com.icebeal.gitxclone.data.model.Search
import com.icebeal.gitxclone.data.model.User
import com.icebeal.gitxclone.data.model.UserRepos
import com.icebeal.gitxclone.data.repositories.UserRepository
import com.icebeal.gitxclone.data.retrofit.RetrofitBuilder
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel(application:Application) : AndroidViewModel(application) {

    private val listUser = MutableLiveData<ArrayList<User>>()
    private val listRepo = MutableLiveData<ArrayList<UserRepos>>()

    private val userDao = UserDb.getInstance(application).userDao()
    private val repository = UserRepository(userDao)

    fun queryAll() : LiveData<List<User>> = repository.queryAll()

    fun searchUser(username:String) = viewModelScope.launch {

        RetrofitBuilder.api.getUser(username)
            .enqueue(object : Callback<Search>{

                override fun onResponse(call: Call<Search>, response: Response<Search>) {

                    listUser.postValue(response.body()?.search)

                }

                override fun onFailure(call: Call<Search>, t: Throwable) {

                    Log.d("Exception", t.message.toString())

                }


            })

    }

    fun getUser() : LiveData<ArrayList<User>> = listUser

    fun setRepo(username: String) = viewModelScope.launch {

        RetrofitBuilder.api.getRepos(username)
            .enqueue(object : Callback<ArrayList<UserRepos>>{

                override fun onResponse(
                    call: Call<ArrayList<UserRepos>>,
                    response: Response<ArrayList<UserRepos>>
                ) {

                    listRepo.postValue(response.body())

                }

                override fun onFailure(call: Call<ArrayList<UserRepos>>, t: Throwable) {

                    Log.d("Exception", t.message.toString())

                }

            })

    }

    fun getRepo() : LiveData<ArrayList<UserRepos>> = listRepo

}