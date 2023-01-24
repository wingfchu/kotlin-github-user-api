package com.wendychu.github_user_api.ui.detail

import android.util.Log
import androidx.lifecycle.*
import com.wendychu.github_user_api.data.Resource
import com.wendychu.github_user_api.data.remote.GithubRepository
import com.wendychu.github_user_api.data.remote.model.UserDetailResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val repository: GithubRepository
) : ViewModel() {

    private val _userDetail = MutableLiveData<UserDetailResponse>()
    val userDetail: LiveData<UserDetailResponse> get() = _userDetail

    fun getUserDetail(userName: String) {
        viewModelScope.launch {
            repository.getUserDetail(userName).collect{ result ->
                when (result){
                    is Resource.Success -> {
                        _userDetail.postValue(result.data)
                    }
                    is Resource.Error -> {
                        Log.d("LOG", result.message)
                    }
                }
            }
        }
    }

}