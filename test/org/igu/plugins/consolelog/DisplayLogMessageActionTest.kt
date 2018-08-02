package org.igu.plugins.consolelog

import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase

/**
 * Created by iguissouma on 31/07/2018.
 */
class DisplayLogMessageActionTest : LightCodeInsightFixtureTestCase() {
    override fun getTestDataPath(): String {
        return "testData"
    }

    fun `test console log for const selected`() {
        runActionTest("console-log-const-selected-before.js", "console-log-const-after.js", true)
    }

    fun `test console log for caret on const`() {
        runActionTest("console-log-caret-on-const-before.js", "console-log-const-after.js", true)
    }

    private fun runActionTest(fileBefore: String, fileAfter: String, ignoreTrailingWhitespaces: Boolean) {
        myFixture.configureByFiles(fileBefore)
        myFixture.performEditorAction("org.igu.plugins.consolelog.DisplayLogMessageAction")
        myFixture.checkResultByFile(fileBefore, fileAfter, ignoreTrailingWhitespaces)
    }
}