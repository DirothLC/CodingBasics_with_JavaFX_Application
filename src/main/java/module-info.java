module kz.kstu.kutsinas.course_project.coding_basics.codingbasicscourseproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    exports kz.kstu.kutsinas.course_project.coding_basics.codingbasicscourseproject.app;
    opens kz.kstu.kutsinas.course_project.coding_basics.codingbasicscourseproject.app to javafx.fxml;
    exports kz.kstu.kutsinas.course_project.coding_basics.codingbasicscourseproject.controllers;
    opens kz.kstu.kutsinas.course_project.coding_basics.codingbasicscourseproject.controllers to javafx.fxml;
}