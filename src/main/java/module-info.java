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
    requires com.almasb.fxgl.all;

    opens kz.kstu.kutsinas.course_project.coding_basics.codingbasicscourseproject to javafx.fxml;
    exports kz.kstu.kutsinas.course_project.coding_basics.codingbasicscourseproject;
}