package com.wendychu.github_user_api.ui.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.wendychu.github_user_api.R
import com.wendychu.github_user_api.data.model.UserItemModel
import com.wendychu.github_user_api.databinding.FragmentUserSearchBinding
import com.wendychu.github_user_api.utils.getQueryChangeFlow
import com.wendychu.github_user_api.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class UserSearchFragment : Fragment(R.layout.fragment_user_search) {

    private val binding by viewBinding(FragmentUserSearchBinding::bind)
    private val viewModel by viewModels<UserSearchViewModel>()
    private val adapter = UsersAdapter(::onUserItemClick)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObserver()
        setupListener()
    }

    private fun setupRecyclerView() {
        binding.rvUserSearch.adapter = adapter
    }

    private fun setupObserver() {
        viewModel.userSearchResult.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun setupListener() {
        viewModel.bindSearchFlow(binding.searchView.getQueryChangeFlow())
    }

    private fun onUserItemClick(user: UserItemModel) {
    }
}