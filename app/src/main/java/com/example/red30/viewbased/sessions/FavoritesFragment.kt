package com.example.red30.viewbased.sessions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.red30.MainNavGraphDirections
import com.example.red30.viewbased.MainViewViewModel
import com.example.red30.data.favorites
import com.example.red30.databinding.FragmentFavoritesBinding
import com.example.red30.viewbased.getAppLayoutManager
import com.example.red30.viewbased.gone
import com.example.red30.viewbased.makeAppSnackbar
import com.example.red30.viewbased.visible
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class FavoritesFragment : Fragment() {

    private val viewModel: MainViewViewModel by activityViewModel()
    private var adapter: SessionItemAdapter? = null

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = setUpAdapter()

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collect { uiState ->
                    if (!uiState.isLoading && uiState.favorites.isNotEmpty()) {
                        adapter?.setItems(uiState.favorites)
                        binding.recyclerview.visible()
                        binding.emptyFavorites.gone()
                    } else {
                        binding.recyclerview.gone()
                        binding.emptyFavorites.visible()
                    }

                    if (uiState.snackbarMessage != null) {
                        displaySnackbar(uiState.snackbarMessage)
                    }
                }
        }
    }

    private fun setUpAdapter(): SessionItemAdapter {
        val adapter = SessionItemAdapter(
            onSessionItemClick = { sessionId ->
                viewModel.setSelectedSessionId(sessionId)
                findNavController().navigate(
                    MainNavGraphDirections.actionGlobalToSessionDetailFragment()
                )
            },
            onFavoriteClick = { sessionId ->
                viewModel.toggleFavorite(sessionId)
            }
        )
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = getAppLayoutManager(
            context = requireContext()
        )
        return adapter
    }

    private fun displaySnackbar(snackbarMessage: Int) {
        makeAppSnackbar(binding.root, snackbarMessage).show()
        viewModel.shownSnackbar()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
        _binding = null
    }
}
