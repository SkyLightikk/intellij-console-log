package org.igu.plugins.consolelog

import com.intellij.lang.javascript.psi.JSVariable
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.LangDataKeys
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.codeStyle.CodeStyleManager
import com.intellij.util.ui.UIUtil


/**
 * Created by iguissouma on 30/07/2018.
 */
class DisplayLogMessageAction : CommonAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val project = AnAction.getEventProject(e)
        val editor = e.getData(PlatformDataKeys.EDITOR)
        val data = e.getData(LangDataKeys.PSI_ELEMENT)
        val file = e.getData(PlatformDataKeys.PSI_FILE)
        val caret = e.getData(PlatformDataKeys.CARET)
        if (project == null || editor == null || file == null || caret == null || data == null) {
            return
        }

        var selectedText = getSelectedText(caret)

        if (selectedText == null) {
            selectedText = when (data) {
                is JSVariable -> data.name
                else -> {
                    null
                }
            }
        }

        if (selectedText == null) {
            showHint(editor, "Please select a var to log")
            return
        }

        val document = editor.document
        val lineOfSelectedVar = caret.visualLineEnd

        runCommand(project, Runnable {
            document.insertString(lineOfSelectedVar, "console.log($selectedText);\n")
            val manager: PsiDocumentManager = PsiDocumentManager.getInstance(project);
            manager.commitDocument(document);
            UIUtil.invokeAndWaitIfNeeded(Runnable {
                CodeStyleManager.getInstance(project).reformat(file)
            })
        })

    }

}

