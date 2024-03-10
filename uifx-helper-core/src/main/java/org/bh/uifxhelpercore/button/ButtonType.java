package org.bh.uifxhelpercore.button;

/**
 * Basic button types. For custom button type create new enum witch inmplement {@link IButtonType}
 */
public enum ButtonType implements IButtonType {
    OK("okBtn"),
    CANCEL("cancelBtn"),
    DELETE("deleteBtn"),
    CREATE("createBtn"),
    UPDATE("updateBtn"),
    SEARCH("searchBtn"),
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
