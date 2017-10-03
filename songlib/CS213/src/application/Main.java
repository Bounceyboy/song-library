package application;
	
import java.awt.ScrollPane;
import java.io.*;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.beans.value.ChangeListener;
import javafx.collections.*;
import javafx.geometry.HPos;
public class Main extends Application {
	Scene scene;
	ListView<Song> listview;
	Label label = new Label();
	ObservableList<Song> obs= FXCollections.observableArrayList();;
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		new Library("Songs");
		listview = new ListView<>();
		
		label.setLayoutX(10);
		label.setLayoutY(100);
		label.setFont(Font.font("Arial",12));
		
		for(int i=0;i<Library.lib.size();i++){
			obs.add(Library.lib.get(i));
		}
		listview.setItems(obs);
		
		listview.getSelectionModel().selectedItemProperty().addListener((obs,oldval,newval)->details(primaryStage));
		listview.getSelectionModel().select(0);
		
		primaryStage.setTitle("Playlist");
		GridPane gridpane = new GridPane();
		//GridPane.setHalignment(label, HPos.CENTER);
		gridpane.add(label, 0, 0);
		//ScrollPane scroll = new ScrollPane();
		
		VBox layout = new VBox(10);
		layout.getChildren().addAll(listview, gridpane);
		scene = new Scene(layout,300,250);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	private void details(Stage a){
		label.setText(listview.getSelectionModel().getSelectedItem().detail());
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
