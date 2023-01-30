package com.wendychu.github_user_api.ui.social

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wendychu.github_user_api.data.Resource
import com.wendychu.github_user_api.data.mapData
import com.wendychu.github_user_api.data.model.UserItemModel
import com.wendychu.github_user_api.data.remote.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserSocialViewModel @Inject constructor(private val repository: GithubRepository) : ViewModel(){

    private val _userList = MutableLiveData<List<UserItemModel>>(emptyList())
    val userList : LiveData<List<UserItemModel>> get() = _userList

    fun getFollowerList(userName: String){
        viewModelScope.launch {
            repository.getUserFollowers(userName).map {
                it.mapData { data ->
                    data.map { item ->
                        UserItemModel(item.id, item.login, item.avatarUrl)
                    }
                }
            }.collect{
                updateUserList(it)
            }
        }

    }

    fun getFollowingList(userName: String){
        viewModelScope.launch {
            repository.getUserFollowing(userName).map {
                it.mapData { data ->
                    data.map { item ->
                        UserItemModel(item.id, item.login, item.avatarUrl)
                    }
                }
            }.collect{
                updateUserList(it)
            }
        }
    }

    private fun updateUserList(result: Resource<List<UserItemModel>>){
        when (result){
            is Resource.Success -> {
                _userList.postValue(result.data)
            }
            else -> {
            }
        }
    }
}