package com.libraries.test

import kotlin.random.Random

fun randomInt() = Random.nextInt()
fun randomFloat() = Random.nextFloat()
fun randomDouble() = Random.nextDouble()
fun randomLong() = Random.nextLong()
fun randomBoolean() = Random.nextBoolean()
fun randomString() = generateUUID()
fun anyKey() = randomString()
