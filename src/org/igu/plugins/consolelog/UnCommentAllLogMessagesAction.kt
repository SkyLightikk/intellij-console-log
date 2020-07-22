package org.igu.plugins.consolelog

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.psi.PsiDocumentManager


/**
 * Created by iguissouma on 30/07/2018.
 */
class UnCommentAllLogMessagesAction : CommonAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val project = AnAction.getEventProject(e)
        val editor = e.getData(PlatformDataKeys.EDITOR)
        val file = e.getData(PlatformDataKeys.PSI_FILE)
        val caret = e.getData(PlatformDataKeys.CARET)
        if (project == null || editor == null || file == null || caret == null) {
            return
        }

        val document = editor.document

        runCommand(project, Runnable {
            findAllCommentedLogMessages(file).asReversed().forEach {
                document.deleteString(it.textOffset, it.textOffset + 2)
            }
            val manager: PsiDocumentManager = PsiDocumentManager.getInstance(project);
            manager.commitDocument(document);
        })


    }
}

