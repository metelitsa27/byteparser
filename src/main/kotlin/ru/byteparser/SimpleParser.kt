import org.springframework.stereotype.Component

@Component
class SimpleParser {
    fun encode(device: DeviceState): ByteArray? {
        // simple format: "byte" : "bit(s)" : "description"
        // 1 : 0 - 3 : device status
        // 1 : 4 - 7 : device version
        // 2 : 0 - 7 : device id, permissible  range - from 1 to 100

        val id = encodeDeviceId(device.id) ?: return null

        return byteArrayOf((device.status.code or (device.version.code shl VERSION_SHIFT)).toByte(), id)
    }

    fun decode(state: ByteArray): DeviceState? {
        if (state.size != 2) {
            println("Unexpected size=${state.size} of decoded array=${state}")
            return null
        }

        return try {
            val id = decodeDeviceId(state[1]) ?: return null
            val status = decodeDeviceStatus(state[0]) ?: return null
            val version = decodeDeviceVersion(state[0]) ?: return null

            DeviceState(
                    id = id,
                    version = version,
                    status = status
            )
        } catch (e: Exception) {
            println("Can't parse byte array=$state, $e")
            null
        }
    }

    private fun encodeDeviceId(id: Int): Byte? = when {
        id >= DeviceState.ID_MIN_VALUE && id <= DeviceState.ID_MAX_VALUE -> id.toByte()
        else -> {
            println("Unexpected device id=$id")
            null
        }
    }

    private fun decodeDeviceId(id: Byte): Int? {
        val newId = id.toInt()

        return if (newId >= DeviceState.ID_MIN_VALUE && newId <= DeviceState.ID_MAX_VALUE) newId
        else {
            println("Unexpected decoded device id=$id")
            null
        }
    }

    private fun decodeDeviceStatus(status: Byte): DeviceStatus? =
            when (status.toInt() and BIT_MASK) {
                DeviceStatus.WORK_AND_ON.code -> DeviceStatus.WORK_AND_ON
                DeviceStatus.WORK_WITH_RESTRICTIONS.code -> DeviceStatus.WORK_WITH_RESTRICTIONS
                DeviceStatus.OFF_AND_BLOCKED.code -> DeviceStatus.OFF_AND_BLOCKED
                DeviceStatus.CRITICAL_FAILURE.code -> DeviceStatus.CRITICAL_FAILURE
                else -> {
                    println("Unexpected decoded device status=$status")
                    null
                }
            }

    private fun decodeDeviceVersion(version: Byte): DeviceVersion? =
            when ((version.toInt() ushr VERSION_SHIFT) and BIT_MASK) {
                DeviceVersion.V_1.code -> DeviceVersion.V_1
                DeviceVersion.V_1_1.code -> DeviceVersion.V_1_1
                DeviceVersion.V_2.code -> DeviceVersion.V_2
                else -> {
                    println("Unexpected decoded device version=$version")
                    null
                }
            }

    companion object {
        const val VERSION_SHIFT = 4
        const val BIT_MASK = 0b00001111
    }
}