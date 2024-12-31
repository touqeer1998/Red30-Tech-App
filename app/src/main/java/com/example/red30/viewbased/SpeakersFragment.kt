package com.example.red30.viewbased

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.red30.data.Speaker
import com.example.red30.databinding.FragmentSpeakersBinding
import kotlinx.serialization.json.Json

class SpeakersFragment : Fragment() {

    private var _binding: FragmentSpeakersBinding? = null
    private val binding get() = _binding!!

    private val items: List<Speaker> by lazy {
        Json.decodeFromString<List<Speaker>>("""
            [
    {
      "name": "Aksh Khatri",
      "title": "Controls System Lead",
      "organization": "KinetEco Inc.",
      "image_url": "https://i.pravatar.cc/150?img=67",
      "bio": "Aksh Kahatri balances his serious job with another serious job: Keeping The Solar Bowler busy creating new ways to power his bowling alley.",
      "id": 1
    },
    {
      "name": "Ally Bode",
      "title": "UX Designer",
      "organization": "Globe Bank International",
      "image_url": "https://i.pravatar.cc/150?img=49",
      "bio": "Ally Bode believes in humans, and spends her time utilizing ai and robotics to make products more human-manageable. ",
      "id": 2
    },
    {
      "name": "Anna Rossi",
      "title": "Product Development Specialist",
      "organization": "Two Trees Olive Oil",
      "image_url": "https://i.pravatar.cc/150?img=47",
      "bio": "Anna Rossi's favorite part of her job is getting to invent things with people and companies from all over the world. She also heads up the annual customer maker contest, featuring people who make amazing things with Two Trees Olive Oil.",
      "id": 3
    },
    {
      "name": "Aran Nguyen",
      "title": "LEX Designer",
      "organization": "Binaryville",
      "image_url": "https://i.pravatar.cc/150?img=33",
      "bio": "Aran Nguyen is widely known as a champion of accessible education, receiving awards for ingenuity in online learning for students with special needs. ",
      "id": 4
    },
    {
      "name": "Ashley Hackett",
      "title": "Chief Innovation Officer",
      "organization": "The Landon Hotel",
      "image_url": "https://i.pravatar.cc/150?img=67",
      "bio": "Ashley Hackett calls herself a \"permanently remote\" employee as part of the executive team that manages a worldwide accommodation business.",
      "id": 5
    },
    {
      "name": "Beverley Armstrong",
      "title": "Broadcast Engineer",
      "organization": "Red30 Design",
      "image_url": "https://i.pravatar.cc/150?img=26",
      "bio": "While Beverley Armstrong is known as a tech head, she also has a secret musical side playing Viennese oboe and other woodwinds. ",
      "id": 6
    },
    {
      "name": "Daniel Rohan",
      "title": "Cyber Security Specialist",
      "organization": "Hansel & Petal",
      "image_url": "https://i.pravatar.cc/150?img=59",
      "bio": "Daniel Rohan has been with Hansel & Petal for six years. His leadership in AI security is well-documented in his many articles for top security publications.",
      "id": 7
    },
    {
      "name": "Daron Seeley",
      "title": "Sr. Solutions Architect",
      "organization": "Two Trees Olive Oil",
      "image_url": "https://i.pravatar.cc/150?img=11",
      "bio": "Daron Seeley loves solving problems, which makes the job of Solutions Architect a paradisiacal dream come true\u2014especially when he gets to play with robots. ",
      "id": 8
    }
  ]
        """.trimIndent()
        )
    }

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

        val adapter = SpeakerItemAdapter(items) {
            findNavController().navigate(
                SpeakersFragmentDirections.actionSpeakersFragmentToSpeakerDetailFragment()
            )
        }
        binding.recyclerview.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
