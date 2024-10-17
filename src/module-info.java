module Maze {
	requires javafx.graphics;
	requires javafx.fxml;
	requires javafx.controls;
	

    exports application to javafx.graphics;
    exports controllers to javafx.fxml;  //allows FXML files to access the controller
    opens controllers to javafx.fxml;  //open the package to the FXML module and allow access
    
    requires java.desktop;
	requires javafx.base;

}