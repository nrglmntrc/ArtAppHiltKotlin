package com.nurgulmantarci.artapphiltkotlin.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.nurgulmantarci.artapphiltkotlin.R
import com.nurgulmantarci.artapphiltkotlin.databinding.FragmentArtDetailsBinding

class ArtDetailsFragment: Fragment(R.layout.fragment_art_details) {

    private var fragmentBinding: FragmentArtDetailsBinding?=null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding=FragmentArtDetailsBinding.bind(view)
        fragmentBinding=binding

        binding.artImageView.setOnClickListener{
            findNavController().navigate(ArtDetailsFragmentDirections.actionArtDetailsFragmentToImageApiFragment())
        }
    }

}