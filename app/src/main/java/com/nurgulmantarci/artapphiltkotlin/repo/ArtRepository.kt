package com.nurgulmantarci.artapphiltkotlin.repo

import androidx.lifecycle.LiveData
import com.nurgulmantarci.artapphiltkotlin.api.RetrofitAPI
import com.nurgulmantarci.artapphiltkotlin.model.ImageResponse
import com.nurgulmantarci.artapphiltkotlin.roomdb.Art
import com.nurgulmantarci.artapphiltkotlin.roomdb.ArtDao
import com.nurgulmantarci.artapphiltkotlin.util.Resource
import javax.inject.Inject

class ArtRepository @Inject constructor(
        private val artDao: ArtDao,
        private val retrofitAPI: RetrofitAPI
) : ArtRepositoryInterface {
    override suspend fun insertArt(art: Art) {
        artDao.insertArt(art)
    }

    override suspend fun deleteArt(art: Art) {
        artDao.deleteArt(art)
    }

    override fun getArt(): LiveData<List<Art>> {
        return artDao.observeArts()
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {
            return try {
                val response=retrofitAPI.imageSearch(imageString)
                if(response.isSuccessful){
                    response.body()?.let {
                        return@let Resource.success(it)
                    } ?: Resource.error("Error",null)
                }else{
                    Resource.error("Error",null)
                }
            } catch (e : Exception){
                Resource.error("No data",null)
            }
    }
}