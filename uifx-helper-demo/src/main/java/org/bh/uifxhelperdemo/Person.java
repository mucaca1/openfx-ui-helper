package org.bh.uifxhelperdemo;

import org.bh.uifxhelpercore.form.FieldType;
import org.bh.uifxhelpercore.form.FormField;
import org.bh.uifxhelpercore.form.FormObject;
import org.bh.uifxhelpercore.table.TableColumn;
import org.bh.uifxhelpercore.table.TableObject;
import org.bh.uifxhelpercore.table.ViewType;

import java.time.LocalDate;
import java.util.Date;

@TableObject
@FormObject(formTitle = "form_title")
public class Person {


    @TableColumn(viewType = {ViewType.Default, ViewType.Chooser})
    @FormField(type = FieldType.STRING, section = "personal_data")
    private String name;

    @TableColumn
    @FormField(type = FieldType.INTEGER, section = "personal_data")
    private Integer age;

    @FormField(type = FieldType.BOOLEAN, getter = "isDeleted")
    private boolean deleted;

    @FormField(type = FieldType.DATE)
    private LocalDate validFor;

    @FormField(type = FieldType.USER_DEFINED, fieldName = "parent")
    private Person parent;

    public Person() {
    }

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

    public LocalDate getValidFor() {
        return validFor;
    }

    public void setValidFor(LocalDate validFor) {
        this.validFor = validFor;
    }

    public Person getParent() {
        return parent;
    }

    public void setParent(Person parent) {
        this.parent = parent;
    }
}
