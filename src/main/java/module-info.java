module  autotestingframework {
    requires javafx.fxml;
    requires javafx.controls;
    requires java.desktop;
    requires org.apache.commons.io;

    opens autotestingframework to javafx.fxml;
    exports autotestingframework;
}