data class DeviceState(
        val id: Int,
        val version: DeviceVersion,
        val status: DeviceStatus
) {
    companion object {
        const val ID_MIN_VALUE = 0
        const val ID_MAX_VALUE = 15
    }
}

enum class DeviceStatus(val code: Int) {
    WORK_AND_ON(0),
    WORK_WITH_RESTRICTIONS(1),
    OFF_AND_BLOCKED(2),
    CRITICAL_FAILURE(3)
}

enum class DeviceVersion(val code: Int) {
    V_1(7),
    V_1_1(9),
    V_2(14)
}