package ui;

import java.io.File;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**<b>Critical note:</b><br>
 * <ul>
 * <li><div>Icons made by <a href="https://www.flaticon.com/authors/chanut" title="Chanut">Chanut</a> from <a href="https://www.flaticon.com/" 		   
 * title="Flaticon">www.flaticon.com</a> is licensed by <a href="http://creativecommons.org/licenses/by/3.0/" 		    
 * title="Creative Commons BY 3.0" target="_blank">CC 3.0 BY</a></div></li>
 * <li><div>Icons made by <a href="https://www.flaticon.com/authors/smashicons" title="Smashicons">Smashicons</a> from <a href="https://www.flaticon.com/" 			    
 * title="Flaticon">www.flaticon.com</a> is licensed by <a href="http://creativecommons.org/licenses/by/3.0/" 			    
 * title="Creative Commons BY 3.0" target="_blank">CC 3.0 BY</a></div></li>
 * <li><div>Icons made by <a href="https://www.flaticon.com/authors/daniel-bruce" title="Daniel Bruce">Daniel Bruce</a> from <a href="https://www.flaticon.com/" 			    
 * title="Flaticon">www.flaticon.com</a> is licensed by <a href="http://creativecommons.org/licenses/by/3.0/" 			    
 * title="Creative Commons BY 3.0" target="_blank">CC 3.0 BY</a></div></li>
 * <li><div>Icons made by <a href="https://www.freepik.com/" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" 			    
 * title="Flaticon">www.flaticon.com</a> is licensed by <a href="http://creativecommons.org/licenses/by/3.0/" 			    
 * title="Creative Commons BY 3.0" target="_blank">CC 3.0 BY</a></div></li>
 * <li><div>Icons made by <a href="https://www.flaticon.com/authors/eleonor-wang" title="Eleonor Wang">Eleonor Wang</a> from <a href="https://www.flaticon.com/" 			    
 * title="Flaticon">www.flaticon.com</a> is licensed by <a href="http://creativecommons.org/licenses/by/3.0/" 			    
 * title="Creative Commons BY 3.0" target="_blank">CC 3.0 BY</a></div></li>
 * </ul>
 * */
public class Main extends Application {	
	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("PrimaryStage.fxml"));
		Parent root = loader.load();
		PrimaryStageController psc = loader.getController();
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("APO Music Player");
		primaryStage.setResizable(false);
		primaryStage.setOnCloseRequest(psc::save);
		primaryStage.getIcons().add(new Image(new File("imgs"+File.separator+"cd.png").toURI().toString()));
		primaryStage.show();
	}

}
