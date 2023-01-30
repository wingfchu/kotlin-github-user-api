package com.wendychu.github_user_api.ui.repos

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import com.wendychu.github_user_api.R
import com.wendychu.github_user_api.data.model.RepoItemModel
import com.wendychu.github_user_api.databinding.FragmentRepoListBinding
import com.wendychu.github_user_api.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepoListFragment : Fragment(R.layout.fragment_repo_list) {
    private val binding by viewBinding(FragmentRepoListBinding::bind)
    private val viewModel by viewModels<RepoListViewModel>()
    private val adapter = ReposAdapter(::onRepoItemClick)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObserver()
        viewModel.loadRepo(arguments?.get("user").toString())
    }

    private fun setupRecyclerView() {
        binding.rvRepoList.apply {
            addItemDecoration(
                DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            )
            adapter = this@RepoListFragment.adapter
        }
    }

    private fun setupObserver() {
        viewModel.repoList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun onRepoItemClick(item: RepoItemModel) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.url))
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(intent)
        }
    }
}