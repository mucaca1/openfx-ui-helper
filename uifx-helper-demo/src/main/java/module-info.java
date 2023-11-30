module org.bh.uifxhelperdemo {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.bh.uifxhelpercore;
    requires com.dlsc.formsfx;


    opens org.bh.uifxhelperdemo to javafx.fxml;
    exports org.bh.uifxhelperdemo;
}