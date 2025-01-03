package com.example.red30.viewbased

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.red30.MainViewModel
import com.example.red30.MainViewModelFactory
import com.example.red30.data.ConferenceRepository
import com.example.red30.databinding.FragmentSessionsBinding
import kotlinx.coroutines.launch

private const val TAG = "SessionsFragment"

class SessionsFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels<MainViewModel>() {
        MainViewModelFactory(conferenceRepository = ConferenceRepository(requireActivity()))
    }

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

        val adapter = SessionItemAdapter {
            findNavController().navigate(
                SessionsFragmentDirections.actionSessionsFragmentToSessionDetailFragment()
            )
        }
        binding.recyclerview.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.sessionInfos
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collect { sessionInfos ->
                    Log.d(TAG, sessionInfos.size.toString())
                    adapter.setItems(sessionInfos)
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
