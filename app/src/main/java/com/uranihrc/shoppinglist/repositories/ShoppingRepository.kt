package com.uranihrc.shoppinglist.repositories

import androidx.lifecycle.LiveData
import com.uranihrc.shoppinglist.data.local.ShoppingItem
import com.uranihrc.shoppinglist.data.remote.responses.ImageResponse
import com.uranihrc.shoppinglist.utils.Resource

interface ShoppingRepository {

    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)

    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    fun observeAllShoppingItem():LiveData<List<ShoppingItem>>

    fun observeTotalPrice():LiveData<Float>

    suspend fun searchForImage(query:String):Resource<ImageResponse>
}