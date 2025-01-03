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
import com.example.red30.databinding.FragmentSpeakersBinding
import kotlinx.coroutines.launch

private const val TAG = "SpeakersFragment"

class SpeakersFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels<MainViewModel> {
        MainViewModelFactory(conferenceRepository = ConferenceRepository(requireActivity()))
    }

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

        val adapter = SpeakerItemAdapter {
            findNavController().navigate(
                SpeakersFragmentDirections.actionSpeakersFragmentToSpeakerDetailFragment()
            )
        }
        binding.recyclerview.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.speakers
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collect { speakers ->
                    Log.d(TAG, speakers.size.toString())
                    adapter.setItems(speakers)
                }
       }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
