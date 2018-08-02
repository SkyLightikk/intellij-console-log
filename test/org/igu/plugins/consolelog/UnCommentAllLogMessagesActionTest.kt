package org.igu.plugins.consolelog

import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase

/**
 * Created by iguissouma on 31/07/2018.
 */
class UnCommentAllLogMessagesActionTest : LightCodeInsightFixtureTestCase() {
    override fun getTestDataPath(): String {
        return "testData"
    }

    fun `test console log uncomment all`() {
        runActionTest("console-log-commented-after.js", "console-log-commented-before.js", true)
    }


    private fun runActionTest(fileBefore: String, fileAfter: String, ignoreTrailingWhitespaces: Boolean) {
        myFixture.configureByFiles(fileBefore)
        myFixture.performEditorAction("org.igu.plugins.consolelog.UnCommentAllLogMessagesAction")
        myFixture.checkResultByFile(fileBefore, fileAfter, ignoreTrailingWhitespaces)
    }
}