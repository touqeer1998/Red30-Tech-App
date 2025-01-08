package com.example.red30.viewbased

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.red30.MainNavGraphDirections
import com.example.red30.MainViewModel
import com.example.red30.data.favorites
import com.example.red30.databinding.FragmentFavoritesBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class FavoritesFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModel()

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

        val adapter = SessionItemAdapter(
            onSessionItemClick = { sessionId ->
                viewModel.getSessionInfoById(sessionId = sessionId)
                findNavController().navigate(
                    MainNavGraphDirections.actionGlobalToSessionDetailFragment()
                )
            },
            onFavoriteClick = { sessionId ->
                viewModel.toggleFavorite(sessionId = sessionId)
            }
        )
        binding.recyclerview.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collect { uiState ->
                    if (!uiState.isLoading) {
                        adapter.setItems(uiState.favorites)
                    }

                    if (uiState.snackbarMessage != null) {
                        Snackbar.make(
                            binding.root,
                            uiState.snackbarMessage,
                            Snackbar.LENGTH_SHORT
                        ).show()
                        viewModel.shownSnackbar()
                    }
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
