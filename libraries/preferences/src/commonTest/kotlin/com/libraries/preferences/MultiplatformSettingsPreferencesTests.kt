package com.libraries.preferences

import com.libraries.test.anyKey
import com.libraries.test.randomString
import com.russhwolf.settings.MockSettings
import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import kotlinx.serialization.Serializable
import kotlin.test.*

class MultiplatformSettingsPreferencesTests {

    @Test
    fun `contains succeeds when key exists`() {
        val (sut, _) = makeSUT()
        val key = anyKey()

        sut.setObject(key, TestDataObject(content = randomString()))

        assertTrue(sut.contains(key))
    }

    @Test
    fun `contains succeeds twice when key exists`() {
        val (sut, _) = makeSUT()
        val key = anyKey()

        sut.setObject(key, TestDataObject(content = randomString()))

        assertTrue(sut.contains(key))
        assertTrue(sut.contains(key))
    }

    @Test
    fun `contains fails when key does not exist`() {
        val (sut, _) = makeSUT()

        assertFalse(sut.contains(anyKey()))
    }

    @Test
    fun `remove removes an existing key`() {
        val (sut, _) = makeSUT()
        val key = anyKey()

        sut.setObject(key, TestDataObject(content = randomString()))
        sut.remove(key)

        assertFalse(sut.contains(key))
    }

    @Test
    fun `remove twice removes an existing key`() {
        val (sut, _) = makeSUT()
        val key = anyKey()

        sut.setObject(key, TestDataObject(content = randomString()))
        sut.remove(key)
        sut.remove(key)

        assertFalse(sut.contains(key))
    }

    @Test
    fun `remove removes an existing value`() {
        val (sut, _) = makeSUT()
        val key = anyKey()

        sut.setObject(key, TestDataObject(content = randomString()))
        sut.remove(key)
        val result: TestDataObject? = sut.getObject(key)

        assertNull(result)
    }

    @Test
    fun `remove does not throw on non-existent key removal`() {
        val (sut, _) = makeSUT()

        sut.remove(anyKey())
    }

    @Test
    fun `getObject returns null on non-existent key`() {
        val (sut, _) = makeSUT()

        assertNull(sut.getObject<TestDataObject>(anyKey()))
    }

    @Test
    fun `getObjet throws on decoding failure`() {
        val (sut, settings) = makeSUT()
        val key = anyKey()
        val data = TestDataObject(content = randomString())

        sut.setObject(key, data)
        settings[key] = "invalid json"

        assertFailsWith(FailedToDecodeFromString::class) {
            sut.getObject<TestDataObject>(key)
        }
    }

    @Test
    fun `setObject saves an object`() {
        val (sut, _) = makeSUT()
        val key = anyKey()

        val expectedResult = TestDataObject(content = randomString())
        sut.setObject(key, expectedResult)
        val result: TestDataObject? = sut.getObject(key)

        assertEquals(expected = expectedResult, actual = result)
    }

    @Test
    fun `setObject throws on non-serializable data`() {
        val (sut, _) = makeSUT()
        val key = anyKey()
        val notSerializableData = NotSerializableObject(content = randomString())

        assertFailsWith(FailedToEncodeToString::class) {
            sut.setObject(key, notSerializableData)
        }
    }

    // region HELPERS

    data class Container(
        val sut: Preferences, val settings: Settings
    )

    private fun makeSUT(): Container {
        val engine = MockSettings()
        return Container(MultiplatformSettingsPreferences(engine = engine), engine)
    }

    @Serializable
    private data class TestDataObject(
        val content: String
    )

    private data class NotSerializableObject(
        val content: String
    )

    // endregion
}
