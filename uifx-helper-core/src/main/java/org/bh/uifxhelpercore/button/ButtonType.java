package org.bh.uifxhelpercore.button;

public enum ButtonType {
    OK("okBtn"),
    CANCEL("cancelBtn"),
    DELETE("deleteBtn"),
    CREATE("createBtn"),
    UPDATE("updateBtn")

    ;

    private String identifier;


    ButtonType(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }
}
