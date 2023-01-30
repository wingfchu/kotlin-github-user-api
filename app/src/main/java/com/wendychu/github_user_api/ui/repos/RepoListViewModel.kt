package com.wendychu.github_user_api.ui.repos

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wendychu.github_user_api.data.Resource
import com.wendychu.github_user_api.data.mapData
import com.wendychu.github_user_api.data.model.RepoItemModel
import com.wendychu.github_user_api.data.remote.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class RepoListViewModel @Inject constructor(private val repository: GithubRepository) : ViewModel(){
    private val _repoList = MutableLiveData<List<RepoItemModel>>(emptyList())
    val repoList : LiveData<List<RepoItemModel>> get() = _repoList

    fun loadRepo(userName: String) {
        viewModelScope.launch {
            repository.getUserRepos(userName).map {
                it.mapData { data ->
                    data.map {  item ->
                        RepoItemModel(
                            item.id,
                            item.htmlUrl,
                            item.name,
                            item.description.orEmpty(),
                            item.language.orEmpty(),
                            item.stargazersCount,
                            item.forksCount,
                            item.fork,
                            item.owner.login,
                            item.owner.avatarUrl,
                            LocalDateTime.ofInstant(
                                Instant.parse(item.createdAt),
                                ZoneId.systemDefault()
                            ),
                            LocalDateTime.ofInstant(
                                Instant.parse(item.updatedAt),
                                ZoneId.systemDefault()
                            )

                        )
                    }
                }
            }.collect{ result ->
                when (result){
                    is Resource.Success -> {
                        _repoList.postValue(result.data)
                    }
                    else -> {
                    }
                }
            }
        }
    }
}