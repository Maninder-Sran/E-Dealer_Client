package com.e_dealer.e_dealer_client.score

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import com.e_dealer.e_dealer_client.databinding.FragmentScoreBinding
import com.google.firebase.database.FirebaseDatabase

class ScoreFragment : Fragment() {

    private var _binding : FragmentScoreBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScoreBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.composeViewScoreList.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
               ScoreScreen(activity)
            }
        }

        binding.addPlayerFab.setOnClickListener {
            createPlayer.value = true
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onPause() {
        val jsonLs = Player.serializeList(playerLs)
        val database = FirebaseDatabase.getInstance()
        val playerLsRef = database.reference

        playerLsRef.child("Players").setValue(jsonLs).addOnSuccessListener {
            Log.i("firebase", "Got value ${jsonLs.toString()}")
        }.addOnFailureListener {
            Log.e("firebase", "Error getting data", it)
        }
        super.onPause()
    }

    override fun onResume() {
        val database = FirebaseDatabase.getInstance()
        val playerLsRef = database.reference.child("Players")

        var serializedData = ""

        playerLsRef.get().addOnSuccessListener {
            serializedData = it.value.toString()

            if (serializedData.length > 2) {
                playerLs = Player.deserializeList(serializedData)
                savedStateData.value = true
            }
            Log.i("firebase", "Got value ${it.value.toString()}")
        }.addOnFailureListener {
            Log.e("firebase", "Error getting data", it)
        }

        super.onResume()

    }
}