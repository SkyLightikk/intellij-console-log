package org.igu.plugins.consolelog

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.LangDataKeys
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.psi.PsiDocumentManager
import com.intellij.util.ui.UIUtil


/**
 * Created by iguissouma on 30/07/2018.
 */
class CommentAllLogMessagesAction : CommonAction() {

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
            findAllLogMessages(file).asReversed().forEach {
                document.insertString(it.textOffset, "//")
            }
            val manager: PsiDocumentManager = PsiDocumentManager.getInstance(project);
            manager.commitDocument(document);
            UIUtil.invokeAndWaitIfNeeded(Runnable {
                //CodeStyleManager.getInstance(project).reformat(file)
            })
        })

    }
}

