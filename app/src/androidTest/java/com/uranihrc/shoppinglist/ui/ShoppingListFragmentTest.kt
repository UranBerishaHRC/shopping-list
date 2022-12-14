package com.uranihrc.shoppinglist.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import com.uranihrc.shoppinglist.R
import com.uranihrc.shoppinglist.getOrAwaitValue
import com.uranihrc.shoppinglist.launchFragmentInHiltContainer
import com.uranihrc.shoppinglist.ui.adapters.ShoppingItemAdapter
import com.uranihrc.shoppinglist.data.local.ShoppingItem
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class ShoppingListFragmentTest{

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var testFragmentFactoryAndroidTest: ShoppingFragmentFactoryAndroidTest

    @Before
    fun setup(){
        hiltRule.inject()
    }

    @Test
    fun swipeShoppingItem_deleteItemInDb(){
        val shoppingItem = ShoppingItem("test",1,1f,"url",1)
        var testViewModel:ShoppingViewModel?=null
        launchFragmentInHiltContainer<ShoppingListFragment>(
            fragmentFactory = testFragmentFactoryAndroidTest
        ) {
            testViewModel = shoppingViewModel
            shoppingViewModel?.insertShoppingItemInDB(shoppingItem)
        }

        onView(withId(R.id.rvShoppingItems)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ShoppingItemAdapter.ShoppingItemViewHolder>(
                0,
                swipeLeft()
            )
        )

        assertThat(testViewModel?.getShoppingItems?.getOrAwaitValue()).isEmpty()
    }

    @Test
    fun clickAddItemBtn_NavigateToAddShoppingFrag(){
        val findVanController = mock(NavController::class.java)

        launchFragmentInHiltContainer<ShoppingListFragment> (
                fragmentFactory = testFragmentFactoryAndroidTest
        ) {
            Navigation.setViewNavController(requireView(),findVanController)
        }

        onView(withId(R.id.fabAddShoppingItem)).perform(click())

        verify(findVanController).navigate(
            ShoppingListFragmentDirections.actionShoppingListFragmentToAddShoppingFragment()
        )
    }


}