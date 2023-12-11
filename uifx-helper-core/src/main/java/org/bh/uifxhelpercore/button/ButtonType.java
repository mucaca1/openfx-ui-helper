package org.bh.uifxhelpercore.button;

public enum ButtonType implements IButtonType {
    OK("okBtn"),
    CANCEL("cancelBtn"),
    DELETE("deleteBtn"),
    CREATE("createBtn"),
    UPDATE("updateBtn"),
    ;

    private final String identifier;


    ButtonType(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public String getIdentifier() {
        return identifier;
    }
}
