module org.bh.uifxhelpercore {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.dlsc.formsfx;
    requires java.desktop;


    opens org.bh.uifxhelpercore to javafx.fxml;
    exports org.bh.uifxhelpercore;
}