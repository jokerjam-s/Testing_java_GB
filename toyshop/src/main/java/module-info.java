module ru.gb.testing.toyshop {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens ru.gb.testing.toyshop to javafx.fxml;
    exports ru.gb.testing.toyshop;
    opens ru.gb.testing.toyshop.controller to javafx.fxml;
    exports ru.gb.testing.toyshop.controller;

    opens ru.gb.testing.toyshop.data to javafx.base;

}