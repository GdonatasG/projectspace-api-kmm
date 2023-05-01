package com.libraries.utils.observer

interface Observer<T> {
    fun update(value: T)
}
