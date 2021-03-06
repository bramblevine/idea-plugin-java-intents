package com.github.bramblevine.idea.intention;

import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

@NonNls
public class LocalVariableToFieldConverter extends LocalVariableDeclarationIntentionAction {

    public LocalVariableToFieldConverter() {
        super("IntroduceField");
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
