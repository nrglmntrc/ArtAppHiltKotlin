package com.nurgulmantarci.artapphiltkotlin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nurgulmantarci.artapphiltkotlin.model.ImageResponse
import com.nurgulmantarci.artapphiltkotlin.repo.ArtRepositoryInterface
import com.nurgulmantarci.artapphiltkotlin.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageApiViewModel @Inject constructor(
        private val repository : ArtRepositoryInterface
): ViewModel() {

    private val images= MutableLiveData<Resource<ImageResponse>>()
    val imageList : LiveData<Resource<ImageResponse>> get() = images


    fun searchForImage(searchString: String){
        if(searchString.isEmpty()){
            return
        }

        images.value = Resource.loading(null)
        viewModelScope.launch {
            val response=repository.searchImage(searchString)
            images.value=response
        }
    }

}