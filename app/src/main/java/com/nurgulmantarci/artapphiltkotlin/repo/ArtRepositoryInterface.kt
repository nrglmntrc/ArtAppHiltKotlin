package com.nurgulmantarci.artapphiltkotlin.repo

import androidx.lifecycle.LiveData
import com.nurgulmantarci.artapphiltkotlin.model.ImageResponse
import com.nurgulmantarci.artapphiltkotlin.roomdb.Art
import com.nurgulmantarci.artapphiltkotlin.util.Resource
import dagger.Provides



interface ArtRepositoryInterface {

    suspend fun insertArt(art: Art)

    suspend fun deleteArt(art: Art)

    fun getArt(): LiveData<List<Art>>

    suspend fun searchImage(imageString: String) : Resource<ImageResponse>



}