package ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
		historyFilesListView.getSelectionModel().selectedItemProperty().addListener(
				new ChangeListener<File>() {
					public void changed(ObservableValue<? extends File> ov, 
							File old_val, File new_val) {
						if(new_val != null) {
							try {
								loadInfo(new_val);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				});
	}

	public void loadInfo(File history) throws IOException {
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
