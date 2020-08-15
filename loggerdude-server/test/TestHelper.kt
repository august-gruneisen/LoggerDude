package com.augustg

import java.io.FileReader
import kotlin.test.assertNotNull

object TestHelper {

    /**
     * reads JSON from test file
     *
     * @return json
     */
    internal fun loadTestJson(): String {
        val json = FileReader("./test/TestLogs.json").readText()
        assertNotNull(json)
        return json
    }
}