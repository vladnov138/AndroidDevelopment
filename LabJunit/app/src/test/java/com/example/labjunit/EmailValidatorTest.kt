package com.example.labjunit

import org.junit.Assert.*
import org.junit.Test

class EmailValidatorTest {

    @Test
    fun `check email without @`() {
        val result = EmailValidator.validate("sfsjdkfdsjkfsd")
        assertFalse(result)
    }

    @Test
    fun `check email without part before @`() {
        val result = EmailValidator.validate("@ya.ru")
        assertFalse(result)
    }

    @Test
    fun `check valid email`() {
        val result = EmailValidator.validate("admin@admin.com")
        assertTrue(result)
    }
}