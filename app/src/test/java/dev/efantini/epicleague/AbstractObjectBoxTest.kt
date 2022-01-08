package dev.efantini.epicleague

import dev.efantini.epicleague.data.models.MyObjectBox
import io.objectbox.BoxStore
import io.objectbox.DebugFlags
import java.io.File
import org.junit.After
import org.junit.Before

open class AbstractObjectBoxTest {

    private var _store: BoxStore? = null
    protected val store: BoxStore
        get() = _store!!

    @Before
    open fun setUp() {
        BoxStore.deleteAllFiles(TEST_DIRECTORY)
        _store = MyObjectBox.builder()
            .directory(TEST_DIRECTORY)
            .debugFlags(DebugFlags.LOG_QUERIES or DebugFlags.LOG_QUERY_PARAMETERS)
            .build()
    }

    @After
    open fun tearDown() {
        _store?.close()
        _store = null
        BoxStore.deleteAllFiles(TEST_DIRECTORY)
    }

    companion object {
        private val TEST_DIRECTORY = File("objectbox-example/test-db")
    }
}
