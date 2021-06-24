package com.nurgulmantarci.artapphiltkotlin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nurgulmantarci.artapphiltkotlin.repo.ArtRepositoryInterface
import com.nurgulmantarci.artapphiltkotlin.roomdb.Art
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtViewModel @Inject constructor(
        private val repository :ArtRepositoryInterface
):ViewModel() {

    val artList=repository.getArt()

    fun deleteArt(art : Art) =viewModelScope.launch{
        repository.deleteArt(art)
    }





}