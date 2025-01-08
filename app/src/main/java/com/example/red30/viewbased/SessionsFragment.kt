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
import com.example.red30.data.Day
import com.example.red30.data.sessionInfosByDay
import com.example.red30.databinding.FragmentSessionsBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class SessionsFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModel()

    private var _binding: FragmentSessionsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSessionsBinding.inflate(inflater, container, false)
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

        binding.tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewModel.setDay(if (tab?.position == 0) Day.Day1 else Day.Day2)
                binding.recyclerview.smoothScrollToPosition(0)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // do nothing
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // do nothing
            }
        })

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collect { uiState ->
                    if (!uiState.isLoading) {
                        adapter.setItems(uiState.sessionInfosByDay)
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
