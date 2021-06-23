package com.nurgulmantarci.artapphiltkotlin.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.nurgulmantarci.artapphiltkotlin.R
import com.nurgulmantarci.artapphiltkotlin.databinding.FragmentArtsBinding

class ArtsFragment: Fragment(R.layout.fragment_arts) {

   private var fragmentBinding: FragmentArtsBinding?=null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding=FragmentArtsBinding.bind(view)
        fragmentBinding=binding

        binding.fab.setOnClickListener{
            findNavController().navigate(ArtsFragmentDirections.actionArtsFragmentToArtDetailsFragment())
        }
    }

    override fun onDestroy() {
        fragmentBinding=null
        super.onDestroy()
    }
}