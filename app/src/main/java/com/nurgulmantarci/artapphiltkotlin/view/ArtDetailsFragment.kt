package com.nurgulmantarci.artapphiltkotlin.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.nurgulmantarci.artapphiltkotlin.R
import com.nurgulmantarci.artapphiltkotlin.databinding.FragmentArtDetailsBinding
import com.nurgulmantarci.artapphiltkotlin.util.Status
import com.nurgulmantarci.artapphiltkotlin.viewmodel.ArtDetailViewModel
import com.nurgulmantarci.artapphiltkotlin.viewmodel.ArtViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ArtDetailsFragment @Inject constructor(
        val glide: RequestManager
): Fragment(R.layout.fragment_art_details) {

    private var fragmentBinding: FragmentArtDetailsBinding?=null

    lateinit var viewModel: ArtDetailViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel= ViewModelProvider(requireActivity()).get(ArtDetailViewModel::class.java)
        val binding=FragmentArtDetailsBinding.bind(view)
        fragmentBinding=binding

        subscribeToObservers()


        binding.artImageView.setOnClickListener{
            findNavController().navigate(ArtDetailsFragmentDirections.actionArtDetailsFragmentToImageApiFragment())
        }

        val callBack= object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                viewModel.setSelectedImage("")
                findNavController().popBackStack()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(callBack)

        binding.saveButton.setOnClickListener{
            viewModel.makeArt(binding.nameText.text.toString(), binding.artistText.text.toString(), binding.yearText.text.toString())
        }
    }

    private fun subscribeToObservers(){
        viewModel.selectedImageUrl.observe(viewLifecycleOwner, Observer { url ->
            fragmentBinding?.let {
                glide.load(url).into(it.artImageView)
            }
        })


        viewModel.insertArtMessage.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.SUCCESS->{
                    Toast.makeText(requireActivity(),"Success",Toast.LENGTH_LONG).show()
                    findNavController().navigateUp()
                    viewModel.resetInsertArtMsg()
                }

                Status.ERROR->{
                    Toast.makeText(requireActivity(),it.message ?: "Error",Toast.LENGTH_LONG).show()
                }

                Status.LOADING ->{

                }
            }
        })
    }

    override fun onDestroy() {
        fragmentBinding=null
        super.onDestroy()
    }

}