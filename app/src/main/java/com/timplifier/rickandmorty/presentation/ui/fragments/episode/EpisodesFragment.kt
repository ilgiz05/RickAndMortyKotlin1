package com.timplifier.rickandmorty.presentation.ui.fragments.episode

import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.timplifier.rickandmorty.R
import com.timplifier.rickandmorty.base.BaseFragment
import com.timplifier.rickandmorty.common.extensions.isInternetConnectionAvailable
import com.timplifier.rickandmorty.common.extensions.submitData
import com.timplifier.rickandmorty.databinding.FragmentEpisodesBinding
import com.timplifier.rickandmorty.presentation.ui.adapters.EpisodesAdapter
import com.timplifier.rickandmorty.utils.PaginationScrollListener
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class EpisodesFragment :
    BaseFragment<FragmentEpisodesBinding, EpisodeViewModel>(R.layout.fragment_episodes) {
    override val binding by viewBinding(FragmentEpisodesBinding::bind)
    override val viewModel: EpisodeViewModel by viewModel()
    private val episodesAdapter = EpisodesAdapter()

    override fun setupViews() {
        setupAdapter()
    }

    private fun setupAdapter() {
        binding.recyclerview.apply {

            adapter = episodesAdapter
            val linearLayoutManager = LinearLayoutManager(context)
            layoutManager = linearLayoutManager
            addOnScrollListener(object :
                PaginationScrollListener(
                    linearLayoutManager,
                    { if (requireContext().isInternetConnectionAvailable(requireContext())) viewModel.fetchEpisodes() }) {

                override fun isLoading() = viewModel.isLoading
            }
            )
        }

    }


    override fun setupObserver() {
        subscribeToEpisodes()
        subscribeToLocalEpisodes()
    }

    private fun subscribeToLocalEpisodes() {

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.episodesLocalState.observe(viewLifecycleOwner) {
                episodesAdapter.submitData(it)
            }
        }
    }

    private fun subscribeToEpisodes() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.episodesState.observe(viewLifecycleOwner) {
                episodesAdapter.submitData(it.results)

            }
        }

    }

    override fun setupRequest() {

        if (viewModel.episodesState.value == null && requireContext().isInternetConnectionAvailable(
                requireContext()
            )
        )

            viewModel.fetchEpisodes()
        else
            viewModel.getEpisodes()
    }
}
