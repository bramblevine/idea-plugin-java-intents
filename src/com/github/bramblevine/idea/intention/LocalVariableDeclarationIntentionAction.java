package com.github.bramblevine.idea.intention;

import com.intellij.lang.java.JavaLanguage;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiCompiledElement;
import com.intellij.psi.PsiDeclarationStatement;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLocalVariable;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;

public abstract class LocalVariableDeclarationIntentionAction extends InvokeActionIntention {

    protected LocalVariableDeclarationIntentionAction(String actionId) {
        super(actionId);
    }

    public boolean isAvailable(@NotNull Project project, Editor editor, @NotNull PsiElement element) {

        if (element instanceof PsiCompiledElement
                || !element.getManager().isInProject(element)
                || !element.getLanguage().isKindOf(JavaLanguage.INSTANCE)) {
            return false;
        }

        final PsiElement parentElement = PsiTreeUtil.getParentOfType(element, PsiDeclarationStatement.class);
        return parentElement != null && isAvailableOnDeclarationStatement((PsiDeclarationStatement) parentElement);
    }

    private boolean isAvailableOnDeclarationStatement(PsiDeclarationStatement declarationStatement) {
        PsiElement[] declaredElements = declarationStatement.getDeclaredElements();
        return declaredElements.length != 0 && declaredElements[0] instanceof PsiLocalVariable;
    }
}
