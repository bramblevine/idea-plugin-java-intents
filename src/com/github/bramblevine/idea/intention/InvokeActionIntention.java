package com.github.bramblevine.idea.intention;

import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction;
import com.intellij.ide.DataManager;
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
import com.intellij.psi.PsiElement;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;

public abstract class InvokeActionIntention extends PsiElementBaseIntentionAction {

    private final String actionId;

    protected InvokeActionIntention(String actionId) {
        this.actionId = actionId;
    }

    public void invoke(@NotNull Project project, Editor editor, @NotNull PsiElement element) throws IncorrectOperationException {
        final AnAction action = ActionManager.getInstance().getAction(actionId);
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
}
