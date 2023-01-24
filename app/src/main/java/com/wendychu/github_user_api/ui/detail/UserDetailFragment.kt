package com.wendychu.github_user_api.ui.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.wendychu.github_user_api.R
import com.wendychu.github_user_api.data.remote.model.UserDetailResponse
import com.wendychu.github_user_api.databinding.FragmentUserDetailBinding
import com.wendychu.github_user_api.utils.NumberFormatter
import com.wendychu.github_user_api.utils.loadImage
import com.wendychu.github_user_api.utils.setTextAndVisibility
import com.wendychu.github_user_api.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailFragment : Fragment(R.layout.fragment_user_detail) {

    private val binding by viewBinding(FragmentUserDetailBinding::bind)
    private val viewModel by viewModels<UserDetailViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setupObserver()
        viewModel.getUserDetail(arguments?.getString("user").orEmpty())
    }

    private fun setupObserver() {
        viewModel.userDetail.observe(viewLifecycleOwner, ::setupUI)
    }

    private fun setupUI(userDetail: UserDetailResponse) {
        with(binding) {
            tvName.setTextAndVisibility(userDetail.name)
            tvUserName.text = userDetail.login
            ivUserPic.loadImage(userDetail.avatarUrl)
            tvBio.setTextAndVisibility(userDetail.bio)
            tvCompany.setTextAndVisibility(userDetail.company)
            tvLocation.setTextAndVisibility(userDetail.location)
            tvBlog.setTextAndVisibility(userDetail.blog)
            tvFollowersFollowing.setTextAndVisibility(
                getFollowersFollowing(
                    userDetail.followers,
                    userDetail.following
                )
            )
            tvRepoCount.text = userDetail.publicRepos.toString()
            tvFollowersCount.text = NumberFormatter.formatWithSuffix(userDetail.followers)
            tvFollowingCount.text = NumberFormatter.formatWithSuffix(userDetail.following)
        }
    }

    private fun getFollowersFollowing(followers: Int, following: Int): String {
        val followersStr = NumberFormatter.formatWithSuffix(followers)
        val followingStr = NumberFormatter.formatWithSuffix(following)
        return "$followersStr followers ▪ $followingStr following"
    }
}