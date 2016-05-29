package com.github.bramblevine.idea.intention;

import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction;
import com.intellij.ide.DataManager;
import com.intellij.lang.java.JavaLanguage;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.ex.ActionUtil;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.ModalityState;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiCompiledElement;
import com.intellij.psi.PsiDeclarationStatement;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLocalVariable;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

@NonNls
public class LocalVariableToFieldConverter extends PsiElementBaseIntentionAction {
    public void invoke(@NotNull Project project, Editor editor, @NotNull PsiElement element) throws IncorrectOperationException {
        final AnAction action = ActionManager.getInstance().getAction("IntroduceField");
        ApplicationManager.getApplication().invokeLater(new Runnable() {
            @Override
            public void run() {
                DataManager instance = DataManager.getInstance();
                DataContext context = instance != null ? instance.getDataContext(editor.getContentComponent()) : DataContext.EMPTY_CONTEXT;
                AnActionEvent anActionEvent = AnActionEvent.createFromDataContext(ActionPlaces.ACTION_SEARCH, null, context);
                ActionUtil.performActionDumbAware(action, anActionEvent);
            }
        }, ModalityState.NON_MODAL);
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

    @Nls
    @NotNull
    public String getFamilyName() {
        return "Convert local variable to field";
    }

    @NotNull
    @Override
    public String getText() {
        return "Convert local variable to field";
    }
}
