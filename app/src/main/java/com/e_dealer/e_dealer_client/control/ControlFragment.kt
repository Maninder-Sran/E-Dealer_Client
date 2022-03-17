package com.e_dealer.e_dealer_client.control

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import com.e_dealer.e_dealer_client.databinding.FragmentControlBinding
import com.e_dealer.e_dealer_client.score.Player
import com.google.firebase.database.FirebaseDatabase

class ControlFragment : Fragment() {

    private var _binding : FragmentControlBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentControlBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.switchManualControl.setOnClickListener{
            retrievePlayerData()

            binding.composeViewControlList.apply {
                setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
                setContent {
                    ControlScreen()
                }
            }

            binding.shuffleDeckFab.show()
            binding.shuffleDeckFab.setOnClickListener {
                shuffleDeck.value = true
            }
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun retrievePlayerData() {
        val database = FirebaseDatabase.getInstance()
        val playerLsRef = database.reference.child("Players")

        var serializedData = ""

        playerLsRef.get().addOnSuccessListener {
            serializedData = it.value.toString()

            if (serializedData.length > 2) {
                playerLs = Player.deserializeList(serializedData)
                savedStateData.value = true
            }
        }
    }
}