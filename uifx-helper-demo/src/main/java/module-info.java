module org.bh.uifxhelperdemo {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.bh.uifxhelpercore;


    opens org.bh.uifxhelperdemo to javafx.fxml;
    exports org.bh.uifxhelperdemo;
}