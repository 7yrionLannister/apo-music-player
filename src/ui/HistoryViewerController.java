package ui;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

public class HistoryViewerController {

    @FXML
    private ListView<File> historyFilesListView;

    @FXML
    private Text historyText;

    @FXML
    public void initialize() {
    	
    }
}
