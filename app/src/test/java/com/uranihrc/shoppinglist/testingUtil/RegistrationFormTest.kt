package com.uranihrc.shoppinglist.testingUtil

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class RegistrationFormTest{

    @Test
    fun `empty username returns false`() {
        val result = RegistrationForm.registerUser("",
        "12345678",
        "12345678")

        assertThat(result).isFalse()
    }

    @Test
    fun `valid username and password returns true`() {
        val result = RegistrationForm.registerUser("Dea",
            "12345678",
            "12345678")

        assertThat(result).isTrue()
    }

    @Test
    fun `username taken returns false`() {
        val result = RegistrationForm.registerUser("Uran",
            "12345678",
            "12345678")

        assertThat(result).isFalse()
    }

    @Test
    fun `password format incorrect returns false`() {
        val result = RegistrationForm.registerUser("Dea",
            "123",
            "123")

        assertThat(result).isFalse()
    }

    @Test
    fun `password should match returns false`() {
        val result = RegistrationForm.registerUser("Dea",
            "12345678",
            "00000000")

        assertThat(result).isFalse()
    }
}