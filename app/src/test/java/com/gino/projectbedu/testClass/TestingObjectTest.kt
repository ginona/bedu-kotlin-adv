package com.gino.projectbedu.testClass

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class TestingObjectTest {
    @Test
    fun `correctly parentheses number`(){
        val result = TestingObject.checkBraces("()()()(123)")
        assertThat(result).isTrue()
    }

    @Test
    fun `parentheses with sentences`(){
        val result = TestingObject.checkBraces("(hello there)")
        assertThat(result).isTrue()
    }

    @Test
    fun `parentheses with parentheses inside`(){
        val result = TestingObject.checkBraces("(())")
        assertThat(result).isTrue()
    }

    @Test
    fun `incorrectly parentheses number`(){
        val result = TestingObject.checkBraces("()()()(123)(")
        assertThat(result).isFalse()
    }

    @Test
    fun `empty string`(){
        val result = TestingObject.checkBraces("")
        assertThat(result).isFalse()
    }

    @Test
    fun `no parentheses`(){
        val result = TestingObject.checkBraces("asd")
        assertThat(result).isFalse()
    }
}