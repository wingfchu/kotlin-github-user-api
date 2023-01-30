package com.wendychu.github_user_api.ui.social

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.wendychu.github_user_api.R
import com.wendychu.github_user_api.data.model.UserItemModel
import com.wendychu.github_user_api.databinding.FragmentUserSocialBinding
import com.wendychu.github_user_api.ui.search.UsersAdapter
import com.wendychu.github_user_api.utils.setTitle
import com.wendychu.github_user_api.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserSocialFragment : Fragment(R.layout.fragment_user_social){
    private val binding by viewBinding(FragmentUserSocialBinding::bind)
    private val viewModel by viewModels<UserSocialViewModel>()
    private val adapter = UsersAdapter(::onUserItemClick)

    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        username = arguments?.getString("user").orEmpty()
        val type = if (arguments?.getInt("type")  == 0) {
            viewModel.getFollowerList(username)
            SocialType.Followers
        } else {
            viewModel.getFollowingList(username)
            SocialType.Following
        }

        setTitle("${username}'s ${type.name}")
        setupRecyclerView()
        setupObserver()
    }

    private fun setupRecyclerView() {
        binding.rvUserSocial.adapter = adapter
    }

    private fun setupObserver() {
        viewModel.userList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun onUserItemClick(user: UserItemModel) {
        val bundle = bundleOf("user" to username)
        findNavController().navigate(R.id.action_userSocialFragment_to_userDetailFragment, bundle)
    }
}