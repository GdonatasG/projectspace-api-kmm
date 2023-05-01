package com.libraries.utils.observer

interface Observable<T> {
    val observers: MutableSet<Observer<T>>

    fun add(observer: Observer<T>) {
        observers.add(observer)
    }

    fun remove(observer: Observer<T>) {
        observers.remove(observer)
    }

    fun notifyObservers(value: T) {
        observers.forEach { it.update(value) }
    }
}
