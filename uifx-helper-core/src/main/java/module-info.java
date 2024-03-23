module org.bh.uifxhelpercore {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.dlsc.formsfx;
    requires java.desktop;


    opens org.bh.uifxhelpercore to javafx.fxml;
    exports org.bh.uifxhelpercore;
    exports org.bh.uifxhelpercore.table;
    exports org.bh.uifxhelpercore.editor;
    exports org.bh.uifxhelpercore.form;
    opens org.bh.uifxhelpercore.form to javafx.fxml;
    exports org.bh.uifxhelpercore.editor.builder;
    opens org.bh.uifxhelpercore.editor to javafx.fxml;
    exports org.bh.uifxhelpercore.button;
    opens org.bh.uifxhelpercore.button to javafx.fxml;
    exports org.bh.uifxhelpercore.locale;
    exports org.bh.uifxhelpercore.editor.entityselector;
    exports org.bh.uifxhelpercore.field;
    exports org.bh.uifxhelpercore.field.entity;
    exports org.bh.uifxhelpercore.table.pagination;
    exports org.bh.uifxhelpercore.form.builder;
    exports org.bh.uifxhelpercore.table.builder;
    exports org.bh.uifxhelpercore.datarequest;
    opens org.bh.uifxhelpercore.datarequest to javafx.fxml;
    exports org.bh.uifxhelpercore.listcomponent;
}