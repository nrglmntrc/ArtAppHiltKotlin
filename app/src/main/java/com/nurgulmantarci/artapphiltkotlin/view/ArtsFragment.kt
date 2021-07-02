package com.nurgulmantarci.artapphiltkotlin.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nurgulmantarci.artapphiltkotlin.R
import com.nurgulmantarci.artapphiltkotlin.adapter.ArtRecyclerAdapter
import com.nurgulmantarci.artapphiltkotlin.databinding.FragmentArtsBinding
import com.nurgulmantarci.artapphiltkotlin.viewmodel.ArtViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ArtsFragment @Inject constructor(
        val artRecyclerAdapter: ArtRecyclerAdapter
): Fragment(R.layout.fragment_arts) {

    private var fragmentBinding: FragmentArtsBinding?=null
    lateinit var viewModel : ArtViewModel

    private val swipeCallBack = object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT){
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val layoutPosition=viewHolder.layoutPosition
            val selectedArt=artRecyclerAdapter.arts[layoutPosition]
            viewModel.deleteArt(selectedArt)
        }

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel=ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)


        val binding=FragmentArtsBinding.bind(view)
        fragmentBinding=binding

        subscribeToObservers()

        binding.recyclerViewArt.adapter=artRecyclerAdapter
        binding.recyclerViewArt.layoutManager=LinearLayoutManager(requireContext())
        ItemTouchHelper(swipeCallBack).attachToRecyclerView(binding.recyclerViewArt)

        binding.fab.setOnClickListener{
            findNavController().navigate(ArtsFragmentDirections.actionArtsFragmentToArtDetailsFragment())
        }
    }




    private fun subscribeToObservers(){
        viewModel.artList.observe(viewLifecycleOwner, {
            artRecyclerAdapter.arts=it
        })

    }

    override fun onDestroy() {
        fragmentBinding=null
        super.onDestroy()
    }
}