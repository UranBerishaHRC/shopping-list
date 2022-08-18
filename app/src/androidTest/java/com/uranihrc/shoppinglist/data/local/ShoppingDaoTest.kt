package com.uranihrc.shoppinglist.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import com.uranihrc.shoppinglist.getOrAwaitValue
import com.uranihrc.shoppinglist.ui.ShoppingFragmentFactory
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@MediumTest
@HiltAndroidTest
class ShoppingDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    //This rule tells android test to run everything one by one , without this u will get a error saying cannot complete task
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var fragmentFactory: ShoppingFragmentFactory

    @Inject
    @Named("test_db") //need named annotation to tell hilt to where to inject from
    lateinit var database:ShoppingItemDatabase
    private lateinit var dao: ShoppingDao

    @Before
    fun setup(){
        //we use this to inject db reference
        hiltRule.inject()

        dao = database.shoppingDao()
    }

    @After
    fun teardown(){
        database.close()
    }

    @Test
    fun insertShoppingItem() = runBlockingTest{
        val shoppingItem = ShoppingItem("itemTest",10,1F,"url",1)
        dao.insertShoppingItem(shoppingItem)

        val allShoppingItem = dao.observeAllShoppingItems().getOrAwaitValue()

        assertThat(allShoppingItem).contains(shoppingItem)
    }

    @Test
    fun deleteShoppingItem() = runBlockingTest{
        val shoppingItem = ShoppingItem("itemTest",10,1F,"url",1)
        dao.insertShoppingItem(shoppingItem)
        dao.deleteShoppingItem(shoppingItem)
        val allShoppingItem = dao.observeAllShoppingItems().getOrAwaitValue()
        assertThat(allShoppingItem).doesNotContain(shoppingItem)
    }

    @Test
    fun observeAllShoppingItem() = runBlockingTest{
        val shoppingItem = ShoppingItem("itemTest",10,1F,"url",1)
        dao.insertShoppingItem(shoppingItem)
        val allShoppingItem = dao.observeAllShoppingItems().getOrAwaitValue()
        assertThat(allShoppingItem).hasSize(1)
    }

    @Test
    fun observeTotalPrice() = runBlockingTest{
        val shoppingItem1 = ShoppingItem("itemTest1",3,3F,"url",1)
        val shoppingItem2 = ShoppingItem("itemTest2",7,2F,"url",2)
        val shoppingItem3 = ShoppingItem("itemTest3",10,4F,"url",3)
        dao.insertShoppingItem(shoppingItem1)
        dao.insertShoppingItem(shoppingItem2)
        dao.insertShoppingItem(shoppingItem3)

        val totalPrice = dao.observeTotalPrice().getOrAwaitValue()
        assertThat(totalPrice).isEqualTo(3*3+7*2+10*4)
    }

}