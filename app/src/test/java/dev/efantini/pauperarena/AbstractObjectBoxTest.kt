package dev.efantini.pauperarena

import dev.efantini.pauperarena.data.models.MyObjectBox
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
        // Delete any files in the test directory before each test to start with a clean database.
        BoxStore.deleteAllFiles(TEST_DIRECTORY)
        _store = MyObjectBox.builder()
            // Use a custom directory to store the database files in.
            .directory(TEST_DIRECTORY)
            // Optional: add debug flags for more detailed ObjectBox log output.
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
