package ui;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import model.paint.ListOfImages;
import model.paint.TreeOfImages;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class PaintController {
	
	public final static String EXPORTED_IMGS_PATH = "img";  
	public final static String ICON = EXPORTED_IMGS_PATH + File.separator + "icon.PNG";
	@FXML
	private BorderPane borderPane;

	@FXML
	private Canvas canvas;

    @FXML
    private CheckBox eraser;
    
    @FXML
    private ColorPicker colorPicker;
    
    @FXML
    private ComboBox<String> brushSize;
    
    @FXML
    private CheckBox shape;

    @FXML
    private ComboBox<String> shapes;
    
    @FXML
    private ComboBox<String> pencilType;

    private TreeOfImages treeOfImages;
    private ListOfImages listOfImages;
    
    /**
     * This method initializes the options to choose the pencil size and sets 
     * the pane to enable the user to paint or erase over it.
     */
    @FXML
    public void initialize() {
    	brushSize.setValue("12");
    	brushSize.getItems().add("2");
    	brushSize.getItems().add("4");
    	brushSize.getItems().add("6");
        brushSize.getItems().add("8");
        brushSize.getItems().add("9");
        brushSize.getItems().add("10");
        brushSize.getItems().add("11");
        brushSize.getItems().add("12");
        brushSize.getItems().add("14");
        brushSize.getItems().add("16");
        brushSize.getItems().add("18");
        brushSize.getItems().add("20");
        brushSize.getItems().add("22");
        brushSize.getItems().add("24");
        brushSize.getItems().add("26");
        brushSize.getItems().add("28");
        brushSize.getItems().add("32");
        brushSize.getItems().add("36");
        brushSize.getItems().add("40");
        brushSize.getItems().add("44");
        brushSize.getItems().add("48");
        brushSize.getItems().add("52");
        brushSize.getItems().add("60");
        brushSize.getItems().add("68");
        brushSize.getItems().add("72");
        brushSize.getItems().add("78");
        brushSize.getItems().add("82");
        pencilType.setValue("1");
        pencilType.getItems().add("1");
        pencilType.getItems().add("2");
        pencilType.getItems().add("3");
        shapes.getItems().add("Round rectangle stroke");
		shapes.getItems().add("Oval stroke");
		shapes.getItems().add("Rectangle stroke");
		shapes.getItems().add("Round rectangle magic");
		shapes.getItems().add("Oval magic");
		shapes.getItems().add("Rectangle magic");
    	GraphicsContext g = canvas.getGraphicsContext2D();
		canvas.setOnMouseDragged(e -> {
			double size = Double.parseDouble(brushSize.getValue());
			double x = e.getX() - size / 2;
			double y = e.getY() - size / 2;

			if (eraser.isSelected()) {
				g.clearRect(x, y, size, size);
			} else {
				if (shape.isSelected()) {
					if(shapes.getValue() != null){
						if (shapes.getValue().equals("Rectangle stroke")) {
							g.setStroke(colorPicker.getValue());
							g.strokeRect(x, y, size, size);
						} else if (shapes.getValue().equals("Oval stroke")) {
							g.setStroke(colorPicker.getValue());
							g.strokeOval(x, y, size, size);
						} else if (shapes.getValue().equals("Round rectangle stroke")) {
							g.setStroke(colorPicker.getValue());
							g.strokeRoundRect(x, y, size, size, 20, 20);
						} else if (shapes.getValue().equals("Rectangle magic")) {
							g.setStroke(colorPicker.getValue());
							g.strokeRect(x, y, x+size, y+size);
						} else if (shapes.getValue().equals("Oval magic")) {
							g.setStroke(colorPicker.getValue());
							g.strokeOval(x, y, x+size, y+size);
						} else if (shapes.getValue().equals("Round rectangle magic")) {
							g.setStroke(colorPicker.getValue());
							g.strokeRoundRect(x, y, x+size, y+size, 80, 80);
						}
					}
				} else {
					g.setFill(colorPicker.getValue());
					if (pencilType.getValue().equalsIgnoreCase("1")) {
						g.fillRect(x, y, size, size);
					} else if (pencilType.getValue().equalsIgnoreCase("2")) {
						g.fillOval(x, y, size, size);
					} else if (pencilType.getValue().equalsIgnoreCase("3")) {
						g.fillRoundRect(x, y, size, size, 10, 10);
					} else {
						g.fillRect(x, y, size, size);
					}
				}
			}
		});
    	
    	treeOfImages = new TreeOfImages();
    	treeOfImages.addNode(10);
    	treeOfImages.addNode(15);
    	treeOfImages.addNode(12);
    	treeOfImages.addNode(17);
    	treeOfImages.addNode(11);
    	treeOfImages.addNode(13);
    	treeOfImages.addNode(14);
    	treeOfImages.addNode(16);
    	treeOfImages.addNode(19);
    	treeOfImages.addNode(18);
    	treeOfImages.addNode(20);
    	treeOfImages.addNode(5);
    	treeOfImages.addNode(2);
    	treeOfImages.addNode(7);
    	treeOfImages.addNode(1);
    	treeOfImages.addNode(3);
    	treeOfImages.addNode(4);
    	treeOfImages.addNode(6);
    	treeOfImages.addNode(8);
    	treeOfImages.addNode(9);
    	listOfImages = new ListOfImages();
    	for (int i = 1; i <= 20; i++) {
			listOfImages.addNode(i);
		}
	}


    
    /**
     * This method allows to save the drawn image in the specified path.
     * pre: the specified path has to exist in the file system.
     * post: The image will be saved successfully.
     */
    public void onSave() {
        try {
            Image snapshot = canvas.snapshot(null, null);
            File dir = new File(EXPORTED_IMGS_PATH);
    		if(!dir.exists()) {
    			dir.mkdir();
    		}
            ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", new File(ICON));
        } catch (IOException e) {
        	e.printStackTrace();
        }
    }
    
    /**
	 * This method uses the doubly linked list from the model package to select the next node (image) from it, then an image 
	 * from the ui/img package is selected and displayed in the canvas.
	 * pre: listOfImages != null && the list cannot be empty -> list.size() != 0. 
	 * post: the next selected image from the list will be selected and it's correspondent representation is 
	 * displayed on the canvas.
	 * @see #listOfImages
	 */
    @FXML
    public void prevImg(ActionEvent event) {
    	canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    	listOfImages.selectPrevious();
    	String imageFile = listOfImages.lastSelected().getValue()+".jpg";
    	String path = System.getProperty("user.dir").replace(File.separator, "/") + "/img/" + imageFile;
    	Image image = new Image("file:///"+path, canvas.getWidth(), canvas.getHeight(), false, false);
    	canvas.getGraphicsContext2D().drawImage(image, 0, 0);
    }
    
    /**
	 * This method uses the doubly linked list from the model package to select the previous node (image) from it, then an image 
	 * from the ui/img package is selected and displayed in the canvas.
	 * pre: listOfImages != null && the list cannot be empty -> listOfImages.size() != 0. 
	 * post: the previous selected image from the list will be selected and it's correspondent representation is 
	 * displayed on the canvas.
	 * @see #listOfImages
	 */
    @FXML
    public void nextImg(ActionEvent event) {
    	canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    	listOfImages.selectNext();
    	String imageFile = listOfImages.lastSelected().getValue()+".jpg";
    	String path = System.getProperty("user.dir").replace(File.separator, "/") + "/img/" + imageFile;
    	Image image = new Image("file:///"+path, canvas.getWidth(), canvas.getHeight(), false, false);
    	canvas.getGraphicsContext2D().drawImage(image, 0, 0);
    }

	/**
	 * This method allows the user to choose an image from file system and add it to
	 * the canvas, where it's possible to draw over it. Pre: an image with .jgp or
	 * .png format has to be selected and the <<OK>> button in the file chooser has
	 * to be pressed, otherwise the image isn't going to be loaded. Post: the image
	 * is going to be displayed in the canvas.
	 * @param event the event received after clicking on the <<Load image>> button.
	 */
	@FXML
	public void loadImage(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.JPG)", "*.JPG");
		FileChooser.ExtensionFilter extFilterjpg = new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg");
		FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.PNG)", "*.PNG");
		FileChooser.ExtensionFilter extFilterpng = new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
		fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterjpg, extFilterPNG, extFilterpng);
		File file = fileChooser.showOpenDialog(null);

		try {
			BufferedImage bufferedImage = ImageIO.read(file);
			Image image = SwingFXUtils.toFXImage(bufferedImage, null);
			canvas.getGraphicsContext2D().drawImage(image, 0, 0, 962, 624);
		} catch (IOException e) {
		} catch (IllegalArgumentException e) {
		}
	}

	/**
	 * This method uses the binary tree from the model package to select a random node (image) from it, then an image 
	 * from the ui/img package is selected and displayed in the canvas.
	 * pre: tree != null && the tree cannot be empty -> tree.size() != 0. 
	 * post: a random selected image from the tree will be selected and it's correspondent representation is 
	 * displayed on the canvas.
	 */
    @FXML
    public void randomImg(ActionEvent event) throws IOException {
    	canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    	String imageFile = treeOfImages.selectRandomNode().getValue()+".jpg";
    	String path = System.getProperty("user.dir").replace(File.separator, "/") + "/img/" + imageFile;
    	Image image = new Image("file:///"+path, canvas.getWidth(), canvas.getHeight(), false, false);
    	canvas.getGraphicsContext2D().drawImage(image, 0, 0);
    }
    
    /**
     * This method allows to close the program when the exit button is pressed.
     */
    public void onExit() {
        Platform.exit();
    }

}