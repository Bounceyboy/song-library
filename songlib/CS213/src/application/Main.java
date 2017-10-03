package application;
	
import java.io.*;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;


public class Main extends Application {
	Scene scene;
	ListView<String> listview;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		/*try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}*/
		new Library("Songs");
		listview = new ListView<>();
		for(int i=0;i<Library.lib.size();i++){
			listview.getItems().add(Library.lib.get(i).song +"\t" +Library.lib.get(i).artist + "\n");
		}
		listview.getSelectionModel().select(0);
		/*listview.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>()){
			@override
			public void change(Observable<? extends String>, String a, String b){
				
			}
		}*/
		primaryStage.setTitle("Playlist");
		VBox layout = new VBox(10);
		layout.getChildren().addAll(listview);
		scene = new Scene(layout,300,250);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
