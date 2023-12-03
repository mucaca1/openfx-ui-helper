package org.bh.uifxhelpercore.editor;

public interface ObjectTranslator<T, I> {

    T getFirstObject(I object);

    I getSecondObject(T object);
}
