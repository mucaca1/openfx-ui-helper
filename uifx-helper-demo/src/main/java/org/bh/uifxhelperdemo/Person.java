package org.bh.uifxhelperdemo;

import org.bh.uifxhelpercore.form.FieldType;
import org.bh.uifxhelpercore.form.FormField;
import org.bh.uifxhelpercore.form.FormObject;
import org.bh.uifxhelpercore.table.TableColumn;
import org.bh.uifxhelpercore.table.TableObject;

@TableObject
@FormObject(formTitle = "form_title")
public class Person {


    @TableColumn
    @FormField(type = FieldType.STRING)
    private String name;

    @TableColumn
    @FormField(type = FieldType.INTEGER)
    private Integer age;

    private boolean deleted;

    public Person() {}

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
        deleted = false;
    }

    @Override
    public String toString() {
        return "Person: [name=" + name + ", age=" + age + ", deleted=" + deleted + "]";
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
