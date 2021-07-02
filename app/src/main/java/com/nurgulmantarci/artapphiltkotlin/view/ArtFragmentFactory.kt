package com.nurgulmantarci.artapphiltkotlin.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.nurgulmantarci.artapphiltkotlin.adapter.ArtRecyclerAdapter
import com.nurgulmantarci.artapphiltkotlin.adapter.ImageRecyclerAdapter
import javax.inject.Inject


class ArtFragmentFactory @Inject constructor(
        private val artRecyclerAdapter: ArtRecyclerAdapter,
        private val glide: RequestManager,
        private val imageRecyclerAdapter: ImageRecyclerAdapter
) : FragmentFactory(){

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {

        return when(className){
            ArtsFragment::class.java.name-> ArtsFragment(artRecyclerAdapter)
            ImageApiFragment::class.java.name-> ImageApiFragment(imageRecyclerAdapter)
            ArtDetailsFragment::class.java.name-> ArtDetailsFragment(glide)
            else -> super.instantiate(classLoader, className)
        }

    }
}