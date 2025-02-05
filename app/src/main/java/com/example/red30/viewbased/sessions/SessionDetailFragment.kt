package com.example.red30.viewbased.sessions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.red30.viewbased.MainViewViewModel
import com.example.red30.R
import com.example.red30.data.Day
import com.example.red30.data.SessionInfo
import com.example.red30.data.duration
import com.example.red30.databinding.FragmentSessionDetailBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class SessionDetailFragment : Fragment() {

    private val viewModel: MainViewViewModel by activityViewModel()

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

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.selectedSession
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collect { session ->
                    session?.let { updateUi(it) }
                }
        }
    }

    private fun updateUi(sessionInfo: SessionInfo) {
        with(sessionInfo) {
            with(binding) {
                name.text = session.name
                track.text = session.track
                roomName.text = session.roomName
                description.text = session.description

                speakerName.text = speaker.name
                speakerImage.name = speaker.name
                speakerImage.imageUrl = speaker.imageUrl
                speakerTitle.text = speaker.title

                dayItem.setValues(
                    iconResourceId = R.drawable.ic_calendar_today,
                    labelResourceId = R.string.day_label,
                    text = if (session.day == Day.Day1)
                        getString(R.string.day_1_label)
                    else
                        getString(R.string.day_2_label)
                )
                startTime.setValues(
                    iconResourceId = R.drawable.ic_schedule,
                    labelResourceId = R.string.start_time_label,
                    text = session.startTime
                )
                endTime.setValues(
                    iconResourceId = R.drawable.ic_schedule,
                    labelResourceId = R.string.end_time_label,
                    text = session.endTime
                )
                duration.setValues(
                    iconResourceId = R.drawable.ic_hourglass_empty,
                    labelResourceId = R.string.time_duration_label,
                    text = "${session.duration} minutes"
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
