package org.bh.uifxhelpercore.editor;

public interface EditorObjectEventHandler<T> {

    T handleEvent(ObjectEvent event, T object) throws Exception;
}
