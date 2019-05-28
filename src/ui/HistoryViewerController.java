package ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
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
		historyFilesListView.setItems(FXCollections.observableArrayList(loadHistoryFiles()));
		historyFilesListView.getSelectionModel().selectedItemProperty().addListener(
				new ChangeListener<File>() {
					public void changed(ObservableValue<? extends File> ov, 
							File old_val, File new_val) {
						if(new_val != null) {
							try {
								loadHistoryInfo(new_val);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				});
	}

	public ArrayList<File> loadHistoryFiles() {
		ArrayList<File> hst = new ArrayList<File>();
		File dir = new File("history");
		if(dir.exists()) {
			File[] items = dir.listFiles();
			for(File f:items) {
				hst.add(f);
			}
		}
		return hst;
	}
	
	public void loadHistoryInfo(File history) throws IOException {
		FileReader fr = new FileReader(history);
		BufferedReader br = new BufferedReader(fr);
		String line = "";
		historyText.setText(line);
		line = br.readLine();
		while(line != null) {
			historyText.setText(historyText.getText().concat(line+"\n"));
			line = br.readLine();
		}
		br.close();
		fr.close();
	}
}
