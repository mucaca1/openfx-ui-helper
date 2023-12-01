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
}