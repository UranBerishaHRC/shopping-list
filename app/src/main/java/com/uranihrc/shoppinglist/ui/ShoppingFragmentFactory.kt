package com.uranihrc.shoppinglist.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.uranihrc.shoppinglist.ui.adapters.ImageAdapter
import com.uranihrc.shoppinglist.ui.adapters.ShoppingItemAdapter
import javax.inject.Inject


class ShoppingFragmentFactory @Inject constructor(
    private val adapter: ImageAdapter,
    private val glide: RequestManager,
    private val shoppingItemAdapter: ShoppingItemAdapter
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            ImagePickerFragment::class.java.name -> ImagePickerFragment(adapter)
            AddShoppingFragment::class.java.name -> AddShoppingFragment(glide)
            ShoppingListFragment::class.java.name -> ShoppingListFragment(shoppingItemAdapter)
            else -> super.instantiate(classLoader, className)
        }

    }
}