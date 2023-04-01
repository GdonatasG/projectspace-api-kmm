package com.libraries.http.models

import kotlin.jvm.JvmName

class QueryParams {
    private val _parameters = mutableMapOf<String, String>()
    val parameters: Map<String, String>
        get() = _parameters

    fun put(key: String, value: Int?) {
        value?.let {
            _parameters[key] = it.toString()
        }
    }

    fun put(key: String, value: String?) {
        value?.let {
            _parameters[key] = it
        }
    }

    /** Creates comma-separated [QueryParams] from given [value] list
     * For example, when given [value] is ["value1", "value2"]
     * It will be converted into "value1,value2" String
     */
    @JvmName("queryParamsPutStringList")
    fun put(key: String, value: List<String>) {
        if (value.isNotEmpty()) {
            _parameters[key] = value.joinToString(",")
        }
    }

    /** Creates comma-separated [QueryParams] from given [value] list
     * For example, when given [value] is ["value1", "value2"]
     * It will be converted into "value1,value2" String
     */
    @JvmName("queryParamsPutIntList")
    fun put(key: String, value: List<Int>) {
        if (value.isNotEmpty()) {
            _parameters[key] = value.joinToString(",")
        }
    }

    operator fun plus(params: QueryParams): QueryParams {
        params.parameters.forEach { param ->
            _parameters[param.key] = param.value
        }

        return this
    }

    override fun equals(other: Any?): Boolean {
        return (other is QueryParams) && parameters == other.parameters
    }

    override fun hashCode(): Int {
        return _parameters.hashCode()
    }

    override fun toString(): String {
        return parameters.toString()
    }
}
