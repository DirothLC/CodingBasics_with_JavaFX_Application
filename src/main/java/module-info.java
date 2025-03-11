module kz.kstu.kutsinas.course_project.coding_basics.codingbasicscourseproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires commons.math3;
    requires com.github.psambit9791.jdsp;


    exports kz.kstu.kutsinas.course_project.coding_basics.codingbasicscourseproject.app;
    opens kz.kstu.kutsinas.course_project.coding_basics.codingbasicscourseproject.app to javafx.fxml;
    exports kz.kstu.kutsinas.course_project.coding_basics.codingbasicscourseproject.controllers;
    opens kz.kstu.kutsinas.course_project.coding_basics.codingbasicscourseproject.controllers to javafx.fxml;
    exports kz.kstu.kutsinas.course_project.coding_basics.codingbasicscourseproject.controllers.lab2;
    opens kz.kstu.kutsinas.course_project.coding_basics.codingbasicscourseproject.controllers.lab2 to javafx.fxml;
    exports kz.kstu.kutsinas.course_project.coding_basics.codingbasicscourseproject.controllers.lab3;
    opens kz.kstu.kutsinas.course_project.coding_basics.codingbasicscourseproject.controllers.lab3 to javafx.fxml;
    exports kz.kstu.kutsinas.course_project.coding_basics.codingbasicscourseproject.controllers.lab4;
    opens kz.kstu.kutsinas.course_project.coding_basics.codingbasicscourseproject.controllers.lab4 to javafx.fxml;
}