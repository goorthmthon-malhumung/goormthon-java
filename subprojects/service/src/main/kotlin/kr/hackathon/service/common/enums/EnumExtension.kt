package kr.hackathon.service.common.enums

inline fun <reified T : Enum<T>> Enum<*>?.toEnum(defaultValue: T): T {
    return this?.let {
        try {
            enumValueOf<T>(name)
        } catch (e: IllegalArgumentException) {
            defaultValue
        }
    } ?: defaultValue
}

inline fun <reified T : Enum<T>> Enum<*>?.toEnum(): T? {
    return this?.let {
        try {
            enumValueOf<T>(name)
        } catch (e: IllegalArgumentException) {
            null
        }
    }
}
