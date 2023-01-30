package com.wendychu.github_user_api.ui.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.app.ShareCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.wendychu.github_user_api.R
import com.wendychu.github_user_api.data.remote.model.UserDetailResponse
import com.wendychu.github_user_api.databinding.FragmentUserDetailBinding
import com.wendychu.github_user_api.ui.social.SocialType
import com.wendychu.github_user_api.ui.social.UserSocialViewModel
import com.wendychu.github_user_api.utils.NumberFormatter
import com.wendychu.github_user_api.utils.loadImage
import com.wendychu.github_user_api.utils.setTextAndVisibility
import com.wendychu.github_user_api.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailFragment : Fragment(R.layout.fragment_user_detail) {

    private val binding by viewBinding(FragmentUserDetailBinding::bind)
    private val viewModel by viewModels<UserDetailViewModel>()
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setupObserver()
        setupListener()
        username = arguments?.getString("user").orEmpty()
        viewModel.getUserDetail(username)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_user_detail, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_share -> {
                viewModel.shareProfile()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupObserver() {
        viewModel.userDetail.observe(viewLifecycleOwner, ::setupUI)
    }

    private fun shareProfile(url: String) {
        ShareCompat.IntentBuilder.from(requireActivity())
            .setType("text/plain")
            .setChooserTitle("Share Github Profile")
            .setText(url)
            .startChooser()
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

    private fun setupListener() {
        with(binding) {
            btnRepo.setOnClickListener {
                navigateToReposFragment()
            }
            btnFollowers.setOnClickListener {
                navigateToSocialFragment(SocialType.Followers)
            }
            btnFollowing.setOnClickListener {
                navigateToSocialFragment(SocialType.Following)
            }
        }
    }

    private fun navigateToReposFragment() {
        val bundle = bundleOf("user" to username)
        findNavController().navigate(R.id.action_userDetailFragment_to_repoListFragment, bundle)
    }

    private fun navigateToSocialFragment(type: SocialType) {
        val bundle = bundleOf("user" to username, "type" to type.ordinal)
        findNavController().navigate(R.id.action_userDetailFragment_to_userSocialFragment, bundle)
    }

    private fun getFollowersFollowing(followers: Int, following: Int): String {
        val followersStr = NumberFormatter.formatWithSuffix(followers)
        val followingStr = NumberFormatter.formatWithSuffix(following)
        return "$followersStr followers ▪ $followingStr following"
    }
}