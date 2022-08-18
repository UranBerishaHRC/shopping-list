package com.uranihrc.shoppinglist.testingUtil

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import com.uranihrc.shoppinglist.R
import org.junit.After
import org.junit.Before
import org.junit.Test

class CompareStringsTest{
    private lateinit var compareStrings:CompareStrings
    private lateinit var context:Context

    @Before
    fun setup(){
        compareStrings = CompareStrings()
        context =  ApplicationProvider.getApplicationContext<Context>()
    }

    @After
    fun tearDown(){

    }

    @Test
    fun ifBothStringMatches_returnTrue(){
        val result = compareStrings.compareStrings(context, R.string.app_name,"Shopping List")
        assertThat(result).isTrue()
    }

    @Test
    fun ifBothStringDontMatches_returnFalse(){
        val result = compareStrings.compareStrings(context, R.string.app_name,"Dea")
        assertThat(result).isFalse()
    }
}