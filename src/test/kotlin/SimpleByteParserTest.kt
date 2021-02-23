package ru.byteparser

import DeviceState
import DeviceStatus
import DeviceVersion
import SimpleParser
import org.junit.Assert
import org.junit.Test

class SimpleByteParserTest {
    @Test
    fun successTest() {
        val parser = SimpleParser()
        val source = DeviceState(
                id = 11,
                version = DeviceVersion.V_1_1,
                status = DeviceStatus.WORK_WITH_RESTRICTIONS
        )

        val encoded = parser.encode(source)
        Assert.assertNotNull(encoded)
        val decoded = parser.decode(encoded!!)
        Assert.assertNotNull(decoded)
        Assert.assertEquals(source, decoded)
    }

    @Test
    fun failureTest() {
        val parser = SimpleParser()
        val source = DeviceState(
                id = 111,
                version = DeviceVersion.V_1_1,
                status = DeviceStatus.WORK_WITH_RESTRICTIONS
        )

        val encoded = parser.encode(source)
        Assert.assertNull(encoded)
    }
}