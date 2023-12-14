package org.bh.uifxhelpercore.button;

/**
 * Support interface for using {@link ButtonAdvancedBar}.
 * For new custom button types create new enum with this interface.
 * Identifier should be unique and will be used for translating values if resource bundle is defined in {@link ButtonAdvancedBar}.
 */
public interface IButtonType {


    /**
     * Return button identifier
     * @return unique string identifier
     */
    String getIdentifier();

}
