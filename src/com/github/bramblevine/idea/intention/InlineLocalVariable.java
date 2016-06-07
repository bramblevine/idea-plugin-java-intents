package com.github.bramblevine.idea.intention;

import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

@NonNls
public class InlineLocalVariable extends LocalVariableDeclarationIntentionAction {

    public InlineLocalVariable() {
        super("Inline");
    }

    @Nls
    @NotNull
    public String getFamilyName() {
        return "Inline local variable";
    }

    @NotNull
    @Override
    public String getText() {
        return "Inline local variable";
    }
}
