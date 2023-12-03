package org.bh.uifxhelpercore.editor;

public class SimpleObjectTranslator<T> implements ObjectTranslator<T, T> {
    @Override
    public T getFirstObject(T object) {
        return object;
    }

    @Override
    public T getSecondObject(T object) {
        return object;
    }
}
