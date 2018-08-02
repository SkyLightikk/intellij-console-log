package org.igu.plugins.consolelog

import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase

/**
 * Created by iguissouma on 31/07/2018.
 */
class DeleteAllLogMessagesActionTest : LightCodeInsightFixtureTestCase() {
    override fun getTestDataPath(): String {
        return "testData"
    }

    fun `test console log delte all`() {
        runActionTest("console-log-to-delete-before.js", "console-log-to-delete-after.js", true)
    }


    private fun runActionTest(fileBefore: String, fileAfter: String, ignoreTrailingWhitespaces: Boolean) {
        myFixture.configureByFiles(fileBefore)
        myFixture.performEditorAction("org.igu.plugins.consolelog.DeleteAllLogMessagesAction")
        myFixture.checkResultByFile(fileBefore, fileAfter, ignoreTrailingWhitespaces)
    }
}