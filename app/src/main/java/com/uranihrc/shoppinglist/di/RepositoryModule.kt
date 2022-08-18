package com.uranihrc.shoppinglist.di

import com.uranihrc.shoppinglist.repositories.DefaultShoppingRepository
import com.uranihrc.shoppinglist.repositories.ShoppingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindShoppingRepository(
        defaultShoppingRepository: DefaultShoppingRepository
    ): ShoppingRepository

}