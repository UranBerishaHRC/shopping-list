package com.uranihrc.shoppinglist.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.uranihrc.shoppinglist.MainCoroutineRule
import com.uranihrc.shoppinglist.getOrAwaitValueTest
import com.uranihrc.shoppinglist.repositories.MockShoppingRepository
import com.uranihrc.shoppinglist.utils.Constants
import com.uranihrc.shoppinglist.utils.Status
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class ShoppingViewModelTest {

    lateinit var shoppingViewModel: ShoppingViewModel

    //we need this to tel test case to run one by one
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    /** need this because test cases cannot run in main dispatcher
     * will generate Exception in thread "main" java.lang.IllegalStateException:
     * Module with the Main dispatcher had failed to initialize.
     * For tests Dispatchers.setMain from kotlinx-coroutines-test module can be used
     */
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup(){
        shoppingViewModel = ShoppingViewModel(MockShoppingRepository())
    }

    @Test
    fun `test if any data is missing return error`(){
       shoppingViewModel.validateShoppingItem("name","","3.0")

        val result = shoppingViewModel.insertShoppingItem.getOrAwaitValueTest()

        assertThat(result.contentIfHandled()?.status).isEqualTo(Status.ERROR)

    }

    @Test
    fun `test incorrect string length return error`(){
        /**
            we do this to test string
            this wil dynamically generate a string based on the constant value
            no need to worry about changing test case
            if we create string "abcdefghijklmnopqrstuvwxyz" we have to change every time test changes
         */

        var stringBuild = buildString{
            for (i in 1..Constants.MAX_ITEM_NAME_LENGTH+1){
                append(1)
            }
        }

        shoppingViewModel.validateShoppingItem(stringBuild,"100","3.0")

        val result = shoppingViewModel.insertShoppingItem.getOrAwaitValueTest()

        assertThat(result.contentIfHandled()?.status).isEqualTo(Status.ERROR)

    }

    @Test
    fun `test incorrect price string length return error`(){
        var stringPriceBuild = buildString{
            for (i in 1..Constants.MAX_ITEM_PRICE_LENGTH+1){
                append(1)
            }
        }

        shoppingViewModel.validateShoppingItem("name","100",stringPriceBuild)

        val result = shoppingViewModel.insertShoppingItem.getOrAwaitValueTest()

        assertThat(result.contentIfHandled()?.status).isEqualTo(Status.ERROR)

    }

    @Test
    fun `test incorrect high amount return error`(){

        shoppingViewModel.validateShoppingItem("name","9999999999999999999","3.0")

        val result = shoppingViewModel.insertShoppingItem.getOrAwaitValueTest()

        assertThat(result.contentIfHandled()?.status).isEqualTo(Status.ERROR)

    }

    @Test
    fun `test correct details input return success`(){

        shoppingViewModel.validateShoppingItem("name","100","3.0")

        val result = shoppingViewModel.insertShoppingItem.getOrAwaitValueTest()

        assertThat(result.contentIfHandled()?.status).isEqualTo(Status.SUCCESS)

    }


}