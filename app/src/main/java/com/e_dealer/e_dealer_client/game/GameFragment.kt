package com.e_dealer.e_dealer_client.game

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import com.e_dealer.e_dealer_client.databinding.FragmentGameBinding
import com.google.firebase.database.FirebaseDatabase

class GameFragment : Fragment() {

    private var _binding : FragmentGameBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentGameBinding.inflate(inflater, container, false)
        val view = binding.root

        val database = FirebaseDatabase.getInstance()
        val gameLsRef = database.reference.child("Games")

        var serializedData = ""

        gameLsRef.get().addOnSuccessListener {

            serializedData = it.value.toString()
            gameLs = Game.deserializeList(serializedData)
            savedGameData.value = true

            Log.i("firebase", "Got value ${it.value.toString()}")
        }.addOnFailureListener {
            Log.e("firebase", "Error getting data", it)
        }

        binding.composeViewGameList.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                GameScreen()
            }
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}