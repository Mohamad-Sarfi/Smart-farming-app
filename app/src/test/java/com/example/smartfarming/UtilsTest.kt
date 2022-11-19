package com.example.smartfarming

import com.example.smartfarming.utils.getDateDifferenceWithToday
import org.junit.Test
import org.junit.Assert.assertEquals

class UtilsTest {

    @Test
    fun dateDifferenceTest(){
        var result = getDateDifferenceWithToday(mapOf("year" to "1401","month" to "09","day" to "28",))
        assertEquals(30, result)

//        result = getDateDifferenceWithToday(mapOf("year" to "1401","month" to "09","day" to "05",))
//        assertEquals(10, result)
//
//        result = getDateDifferenceWithToday(mapOf("year" to "1401","month" to "10","day" to "28",))
//        assertEquals(63, result)
    }
}