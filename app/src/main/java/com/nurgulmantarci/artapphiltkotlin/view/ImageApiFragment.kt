package com.nurgulmantarci.artapphiltkotlin.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.nurgulmantarci.artapphiltkotlin.R
import com.nurgulmantarci.artapphiltkotlin.adapter.ImageRecyclerAdapter
import com.nurgulmantarci.artapphiltkotlin.databinding.FragmentImageApiBinding
import com.nurgulmantarci.artapphiltkotlin.util.Status
import com.nurgulmantarci.artapphiltkotlin.viewmodel.ArtDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ImageApiFragment @Inject constructor(
        private val imageRecyclerAdapter: ImageRecyclerAdapter
): Fragment(R.layout.fragment_image_api){

    lateinit var viewModel: ArtDetailViewModel

    private var fragmentBinding: FragmentImageApiBinding?=null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel=ViewModelProvider(requireActivity()).get(ArtDetailViewModel::class.java)

        val binding= FragmentImageApiBinding.bind(view)
        fragmentBinding=binding

        var job: Job? =null

        binding.searchText.addTextChangedListener {
            job?.cancel()
            job = lifecycleScope.launch {
                delay(1000)
                it?.let {
                    if (it.toString().isNotEmpty()) {
                        viewModel.searchForImage(it.toString())
                    }
                }
            }
        }

        val callBack= object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(callBack)


        subscribeToObservers()

        binding.imageRecyclerView.adapter=imageRecyclerAdapter
        binding.imageRecyclerView.layoutManager=GridLayoutManager(requireContext(),3)

        imageRecyclerAdapter.setOnItemClickListener {
            findNavController().popBackStack()
            viewModel.setSelectedImage(it)
        }

    }


    private fun subscribeToObservers(){
        viewModel.imageList.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.SUCCESS->{
                    val urls=it.data?.hits?.map {
                        imageResult ->  imageResult.previewURL
                    }

                    imageRecyclerAdapter.images=urls ?: listOf()

                    fragmentBinding?.progressBar?.visibility=View.GONE
                }

                Status.ERROR -> {
                    Toast.makeText(requireContext(),it.message ?: "Error",Toast.LENGTH_LONG).show()
                    fragmentBinding?.progressBar?.visibility = View.GONE
                }

                Status.LOADING -> {
                    fragmentBinding?.progressBar?.visibility = View.VISIBLE
                }
            }
        })
    }

    override fun onDestroy() {
        fragmentBinding=null
        super.onDestroy()
    }
}