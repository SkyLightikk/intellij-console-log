package org.igu.plugins.consolelog

import com.intellij.diff.comparison.expandWhitespacesBackward
import com.intellij.lang.javascript.psi.JSExpressionStatement
import com.intellij.lang.javascript.psi.JSVariable
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.LangDataKeys
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.Editor
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.PsiFile
import com.intellij.psi.codeStyle.CodeStyleManager
import com.intellij.util.ui.UIUtil
import com.intellij.util.DocumentUtil.getLineEndOffset
import com.intellij.util.DocumentUtil.getLineStartOffset


/**
 * Created by iguissouma on 30/07/2018.
 */
class DeleteAllLogMessagesAction : CommonAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val project = AnAction.getEventProject(e)
        val editor = e.getData(PlatformDataKeys.EDITOR)
        val data = e.getData(LangDataKeys.PSI_ELEMENT)
        val file = e.getData(PlatformDataKeys.PSI_FILE)
        val caret = e.getData(PlatformDataKeys.CARET)
        if (project == null || editor == null || file == null || caret == null || data == null) {
            return
        }

        val document = editor.document

        runCommand(project, Runnable {
            //document.insertString(lineOfSelectedVar, "console.log($selectedText);\n")
            val findAllCommentedLogMessages = findAllCommentedLogMessages(file)
            val findAllLogMessages = findAllLogMessages(file)
            val allMessages = findAllCommentedLogMessages.union(findAllLogMessages)
            allMessages.sortedByDescending { it.textOffset }.forEach {
                val lineNumber = document.getLineNumber(it.textOffset)
                val lineStartOffset = document.getLineStartOffset(lineNumber)
                val lineEndOffset = document.getLineEndOffset(lineNumber)
                val endIndex = if (document.text.length > lineEndOffset) lineEndOffset + 1 else lineEndOffset
                document.deleteString(lineStartOffset, endIndex)
            }
            val manager: PsiDocumentManager = PsiDocumentManager.getInstance(project);
            manager.commitDocument(document);
            UIUtil.invokeAndWaitIfNeeded(Runnable {
                //CodeStyleManager.getInstance(project).reformat(file)
            })
        })

    }

}

