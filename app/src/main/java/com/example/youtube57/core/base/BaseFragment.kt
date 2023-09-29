package com.example.youtube57.core.base

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.youtube57.R
import com.example.youtube57.presentation.noconnection.NoConnectionFragment


abstract class BaseFragment<VB : ViewBinding, VM : ViewModel> : Fragment() {

    protected var _binding: VB? = null
    protected val binding get() = _binding!!
    protected abstract val viewModel: VM

    protected abstract fun inflaterViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = inflaterViewBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hasConnection()
    }

    private fun hasConnection() {
        val cm =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val connectionInfo = cm.activeNetwork
        if (connectionInfo != null) {
            initRecycler()
        } else
            findNavController().navigate(R.id.noConnectionFragment)
    }

    abstract fun initRecycler()

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}