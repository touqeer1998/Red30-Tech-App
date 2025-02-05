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
import com.example.red30.R
import com.example.red30.data.Day
import com.example.red30.data.sessionInfosByDay
import com.example.red30.databinding.FragmentSessionsBinding
import com.example.red30.viewbased.getAppLayoutManager
import com.example.red30.viewbased.makeAppSnackbar
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class SessionsFragment : Fragment() {

    private val viewModel: MainViewViewModel by activityViewModel()
    private lateinit var adapter: SessionItemAdapter

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

        setUpDayChips()
        adapter = setUpAdapter()

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collect { uiState ->
                    if (!uiState.isLoading) {
                        adapter.setItems(uiState.sessionInfosByDay)
                    }

                    if (uiState.shouldAnimateScrollToTop) {
                        binding.nestedScrollView.fling(0)
                        binding.nestedScrollView.smoothScrollTo(0, 0)
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

        binding.recyclerview.apply {
            setHasFixedSize(true)
            layoutManager = getAppLayoutManager(context = requireContext())
            this.adapter = adapter
        }

        binding.nestedScrollView.setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
            if (scrollY > oldScrollY) {
                viewModel.onScrollComplete()
            }
        }
        return adapter
    }

    private fun setUpDayChips() {
        binding.chipLayout.setOnCheckedStateChangeListener { group, checkedIds ->
            viewModel.setDay(
                if (checkedIds.contains(R.id.day1Chip)) Day.Day1 else Day.Day2
            )
        }
    }

    private fun displaySnackbar(snackbarMessage: Int) {
        makeAppSnackbar(binding.root, snackbarMessage).show()
        viewModel.shownSnackbar()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}