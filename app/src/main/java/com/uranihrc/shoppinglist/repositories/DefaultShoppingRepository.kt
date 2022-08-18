package com.uranihrc.shoppinglist.repositories

import androidx.lifecycle.LiveData
import com.uranihrc.shoppinglist.data.local.ShoppingDao
import com.uranihrc.shoppinglist.data.local.ShoppingItem
import com.uranihrc.shoppinglist.data.remote.PixabayAPI
import com.uranihrc.shoppinglist.data.remote.responses.ImageResponse
import com.uranihrc.shoppinglist.utils.Resource
import java.lang.Exception
import javax.inject.Inject


class DefaultShoppingRepository @Inject constructor(
    private val shoppingDao: ShoppingDao,
    private val pixabayAPI: PixabayAPI
) : ShoppingRepository {
    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.insertShoppingItem(shoppingItem)
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.deleteShoppingItem(shoppingItem)
    }

    override fun observeAllShoppingItem(): LiveData<List<ShoppingItem>> {
        return shoppingDao.observeAllShoppingItems()
    }

    override fun observeTotalPrice(): LiveData<Float> {
        return shoppingDao.observeTotalPrice()
    }

    override suspend fun searchForImage(query: String): Resource<ImageResponse> {
        return try {
           val response = pixabayAPI.searchImage(query)
            if (response.isSuccessful){
               response.body()?.let {imageResponse ->
                   return@let Resource.success(imageResponse)
               }?: Resource.error("An unknown error occurred",null)
            }else{
                Resource.error("An unknown error occurred",null)
            }
        }catch (e:Exception){
            return Resource.error("Something went wrong! $e",null)
        }
    }

}