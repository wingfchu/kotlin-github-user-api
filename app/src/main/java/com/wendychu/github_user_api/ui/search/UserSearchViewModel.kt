package com.wendychu.github_user_api.ui.search

import android.util.Log
import androidx.lifecycle.*
import com.wendychu.github_user_api.data.Resource
import com.wendychu.github_user_api.data.mapData
import com.wendychu.github_user_api.data.model.UserItemModel
import com.wendychu.github_user_api.data.remote.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserSearchViewModel @Inject constructor(
    private val repository: GithubRepository
) : ViewModel() {

    private val _userSearchResult =
        MutableLiveData<List<UserItemModel>>(emptyList())
    val userSearchResult = _userSearchResult as LiveData<List<UserItemModel>>

    fun bindSearchFlow(queryFlow: StateFlow<String>) {
        viewModelScope.launch {
            queryFlow.debounce(500)
                .filter { it.isNotEmpty() }
                .distinctUntilChanged()
                .flowOn(Dispatchers.Main)
                .flatMapLatest { query ->
                    repository.searchUser(query).map {
                        it.mapData { data ->
                            data.items.map {
                                UserItemModel(it.id, it.login, it.avatarUrl)
                            }
                        }
                    }
                }
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            _userSearchResult.postValue(result.data)
                        }
                        is Resource.Error -> {
                            Log.d("LOG", result.message)
                        }
                    }
                }
        }
    }

}