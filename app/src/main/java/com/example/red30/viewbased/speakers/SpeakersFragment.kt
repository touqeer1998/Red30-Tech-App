package com.example.red30.viewbased.speakers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.red30.viewbased.MainViewViewModel
import com.example.red30.data.speakers
import com.example.red30.databinding.FragmentSpeakersBinding
import com.example.red30.viewbased.getAppLayoutManager
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class SpeakersFragment : Fragment() {

    private val viewModel: MainViewViewModel by activityViewModel()

    private var _binding: FragmentSpeakersBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSpeakersBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = SpeakerItemAdapter()
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = getAppLayoutManager(
            context = requireContext()
        )

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collect { uiState ->
                    if (!uiState.isLoading) {
                        adapter.setItems(uiState.speakers)
                    }
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
