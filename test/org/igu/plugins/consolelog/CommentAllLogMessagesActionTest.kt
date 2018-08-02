package org.igu.plugins.consolelog

import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase

/**
 * Created by iguissouma on 31/07/2018.
 */
class CommentAllLogMessagesActionTest : LightCodeInsightFixtureTestCase() {
    override fun getTestDataPath(): String {
        return "testData"
    }

    fun `test console comment all`() {
        runActionTest("console-log-commented-before.js", "console-log-commented-after.js", true)
    }


    private fun runActionTest(fileBefore: String, fileAfter: String, ignoreTrailingWhitespaces: Boolean) {
        myFixture.configureByFiles(fileBefore)
        myFixture.performEditorAction("org.igu.plugins.consolelog.CommentAllLogMessagesAction")
        myFixture.checkResultByFile(fileBefore, fileAfter, ignoreTrailingWhitespaces)
    }
}