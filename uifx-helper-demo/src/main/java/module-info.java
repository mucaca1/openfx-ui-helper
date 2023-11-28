module org.bh.uifxhelperdemo {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.bh.uifxhelperdemo to javafx.fxml;
    exports org.bh.uifxhelperdemo;
}