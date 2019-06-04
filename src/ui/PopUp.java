package ui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class PopUp {
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField width;

    @FXML
    private TextField posX;

    @FXML
    private TextField height;

    @FXML
    private TextField posY;

    @FXML
    private ComboBox<String> shape;

    @FXML
	public void initialize() {
		shape.getItems().add("Rectangle");
		shape.getItems().add("Oval");
	}

	public ResourceBundle getResources() {
		return resources;
	}

	public URL getLocation() {
		return location;
	}

	public TextField getWidth() {
		return width;
	}

	public TextField getPosX() {
		return posX;
	}

	public TextField getHeight() {
		return height;
	}

	public TextField getPosY() {
		return posY;
	}

	public ComboBox<String> getShape() {
		return shape;
	}
   
}
