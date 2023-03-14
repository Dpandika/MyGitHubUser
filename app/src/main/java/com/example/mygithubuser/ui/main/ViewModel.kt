package com.example.mygithubuser.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mygithubuser.api.APIConfig
import com.example.mygithubuser.data.User
import com.example.mygithubuser.data.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModel : ViewModel() {
    val listUser = MutableLiveData<ArrayList<User>>()

    fun setSearchUsers(query: String) {
        APIConfig.apiInstance
            .getSearchUsers(query)
            .enqueue(object : Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful) {
                        listUser.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    t.message?.let { Log.d("Failure", it) }
                }
            })
    }

    fun getSearchUsers(): LiveData<ArrayList<User>> {
        return listUser
    }
}