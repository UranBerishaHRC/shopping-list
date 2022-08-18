package com.uranihrc.shoppinglist.di

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.uranihrc.shoppinglist.R
import com.uranihrc.shoppinglist.data.local.ShoppingDao
import com.uranihrc.shoppinglist.data.local.ShoppingItemDatabase
import com.uranihrc.shoppinglist.data.remote.PixabayAPI
import com.uranihrc.shoppinglist.repositories.DefaultShoppingRepository
import com.uranihrc.shoppinglist.repositories.ShoppingRepository
import com.uranihrc.shoppinglist.utils.Constants.BASE_URL
import com.uranihrc.shoppinglist.utils.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideSearchImageApi(): PixabayAPI {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PixabayAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideGlideInstance(
        @ApplicationContext context: Context
    ) = Glide.with(context).setDefaultRequestOptions(
        RequestOptions()
            .placeholder(R.drawable.ic_image)
            .error(R.drawable.ic_image)
    )

    @Singleton
    @Provides
    fun provideShoppingItemDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, ShoppingItemDatabase::class.java, DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideShoppingDao(
        database: ShoppingItemDatabase
    ) = database.shoppingDao()
}