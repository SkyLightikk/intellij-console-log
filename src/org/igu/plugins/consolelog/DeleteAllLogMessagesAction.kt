package org.igu.plugins.consolelog

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.psi.PsiDocumentManager


/**
 * Created by iguissouma on 30/07/2018.
 */
class DeleteAllLogMessagesAction : CommonAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val project = getEventProject(e)
        val editor = e.getData(PlatformDataKeys.EDITOR)
        val file = e.getData(PlatformDataKeys.PSI_FILE)
        val caret = e.getData(PlatformDataKeys.CARET)
        if (project == null || editor == null || file == null || caret == null) {
            return
        }

        val document = editor.document

        runCommand(project, Runnable {
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
        })

    }

}

