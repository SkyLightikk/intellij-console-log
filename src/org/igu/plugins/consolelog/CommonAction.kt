package org.igu.plugins.consolelog

import com.intellij.codeInsight.hint.HintManager
import com.intellij.lang.javascript.psi.JSCallExpression
import com.intellij.lang.javascript.psi.JSExpressionStatement
import com.intellij.lang.javascript.psi.JSParameterList
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.command.CommandProcessor
import com.intellij.openapi.editor.Caret
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiTreeUtil
import org.jetbrains.annotations.Nullable

abstract class CommonAction : AnAction(){
    fun runCommand(project: Project, runnable: Runnable) {
        CommandProcessor.getInstance().executeCommand(project, { ApplicationManager.getApplication().runWriteAction(runnable) }, "console log", null)
    }

    @Nullable
    fun getSelectedText(caret: Caret?): String? {
        if (caret == null) {
            return null
        }

        if (caret.selectedText == null || caret.selectedText!!.trim().isEmpty()) {
            return null
        }
        return caret.selectedText!!.trim()
    }

    fun showHint(editor: Editor, s: String) {
        HintManager.getInstance().showErrorHint(editor, s)
    }

    @Nullable
    private fun <T : PsiElement> findElementsByTypeWithText(element: PsiFile, aClass: Class<out T>, text: String): List<T> {
        return PsiTreeUtil.collectElementsOfType(element, aClass)
                .filter { it.text.startsWith(text) }
    }


    protected fun findAllLogMessages(file: PsiFile): List<JSExpressionStatement> {
        return findElementsByTypeWithText(file, JSExpressionStatement::class.java, "console.log")
    }

    protected fun findAllCommentedLogMessages(file: PsiFile): List<PsiComment> {
        return findElementsByTypeWithText(file, PsiComment::class.java, "//console.log")
    }
}