package org.bh.uifxhelperdemo;

import org.bh.uifxhelpercore.table.TableColumn;
import org.bh.uifxhelpercore.table.TableObject;

@TableObject
public class Person {


    @TableColumn
    private String name;

    @TableColumn
    private Integer age;

    private boolean deleted;

    public Person() {}

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
        deleted = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
