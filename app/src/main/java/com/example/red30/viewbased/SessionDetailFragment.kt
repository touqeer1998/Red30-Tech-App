package com.example.red30.viewbased

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.red30.MainViewModel
import com.example.red30.R
import com.example.red30.data.ConferenceDataUiState
import com.example.red30.data.initial
import com.example.red30.databinding.FragmentSessionDetailBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class SessionDetailFragment : Fragment(), MenuProvider {

    private val viewModel: MainViewModel by activityViewModel()

    private var _binding: FragmentSessionDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSessionDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.title = ""
        (requireActivity() as MenuHost).addMenuProvider(
            this,
            viewLifecycleOwner,
            Lifecycle.State.RESUMED
        )

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collect { uiState ->
                    if (uiState is ConferenceDataUiState.Loaded) {
                        updateUi(uiState)
                    }
                }
        }
    }

    private fun updateUi(uiState: ConferenceDataUiState.Loaded) {
        uiState.selectedSession?.let { it
            val session = it.session
            val speaker = it.speaker

            with(binding) {
                name.text = session.name
                track.text = session.track
                roomName.text = session.roomName
                speakerName.text = speaker.name
                speakerImage.letter = speaker.initial
                speakerImage.imageUrl = speaker.imageUrl
                speakerTitle.text = speaker.title
                description.text = session.description
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.session_detail_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.save_session -> {
                true
            }

            else -> false
        }
    }
}
