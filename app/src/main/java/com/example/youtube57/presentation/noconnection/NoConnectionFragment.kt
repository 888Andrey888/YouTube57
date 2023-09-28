package com.example.youtube57.presentation.noconnection

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.youtube57.R
import com.example.youtube57.databinding.FragmentNoConnectionBinding

class NoConnectionFragment : Fragment() {

    private lateinit var binding: FragmentNoConnectionBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoConnectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnTryAgain.setOnClickListener {
            hasConnection()
        }
    }

    private fun hasConnection() {
        val cm =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val connectionInfo = cm.activeNetwork
        if (connectionInfo != null) {
            findNavController().navigate(R.id.playlistsFragment)
        }
    }
}