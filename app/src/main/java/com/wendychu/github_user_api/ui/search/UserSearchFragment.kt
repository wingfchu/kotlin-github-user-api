package com.wendychu.github_user_api.ui.search

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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
            setResultLayout(it)
        }
    }

    private fun setupListener() {
        viewModel.bindSearchFlow(binding.searchView.getQueryChangeFlow())
    }

    private fun onUserItemClick(user: UserItemModel) {
        val bundle = bundleOf("user" to user.username)
        findNavController().navigate(R.id.action_userSearchFragment_to_userDetailFragment, bundle)
    }

    private fun setResultLayout(list: List<UserItemModel>){
        if (list.isEmpty()){
            binding.tvNoResult.visibility = View.VISIBLE
            binding.rvUserSearch.visibility = View.GONE
        } else {
            binding.tvNoResult.visibility = View.GONE
            binding.rvUserSearch.visibility = View.VISIBLE
            adapter.submitList(list)
        }

    }
}