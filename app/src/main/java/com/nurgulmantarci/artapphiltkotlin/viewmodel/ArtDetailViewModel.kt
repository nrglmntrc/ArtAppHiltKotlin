package com.nurgulmantarci.artapphiltkotlin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nurgulmantarci.artapphiltkotlin.repo.ArtRepositoryInterface
import com.nurgulmantarci.artapphiltkotlin.roomdb.Art
import com.nurgulmantarci.artapphiltkotlin.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtDetailViewModel @Inject constructor(
        private val repository : ArtRepositoryInterface
): ViewModel() {

    private var insertArtMsg=MutableLiveData<Resource<Art>>()
    val insertArtMessage: LiveData<Resource<Art>> get() = insertArtMsg


    private val selectedImage= MutableLiveData<String>()
    val selectedImageUrl: LiveData<String> get() = selectedImage

    fun setSelectedImage(url : String){
        selectedImage.postValue(url)
    }


    fun resetInsertArtMsg(){
        insertArtMsg=MutableLiveData<Resource<Art>>()
    }


    fun makeArt(name: String, artistName: String, year : String){
        if(name.isEmpty() || artistName.isEmpty() || year.isEmpty()){
            insertArtMsg.postValue(Resource.error("Enter field", null))
            return
        }

        val yearInt= try {
            year.toInt()
        }catch (e : Exception){
            insertArtMsg.postValue((Resource.error("year should be number", null)))
            return
        }

        val art=Art(name,artistName,yearInt,selectedImage.value ?: "")
        insertArt(art)
        setSelectedImage("")
        insertArtMsg.postValue(Resource.success(art))
    }

    fun insertArt(art: Art) =viewModelScope.launch {
        repository.insertArt(art)
    }

}