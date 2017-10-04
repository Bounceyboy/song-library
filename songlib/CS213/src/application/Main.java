package application;
	


import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.beans.value.ChangeListener;
import javafx.collections.*;
public class Main extends Application {
	Scene scene;
	ListView<Song> listview;
	Label song = new Label();
	Label artist = new Label();
	Label album = new Label();
	Label year = new Label();
	ObservableList<Song> obs= FXCollections.observableArrayList();;
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		new Library("Songs");
		listview = new ListView<>();
		
		song.setLayoutX(250);
		song.setLayoutY(10);
		song.setFont(Font.font("Arial",12));
		
		artist.setLayoutX(250);
		artist.setLayoutY(25);
		artist.setFont(Font.font("Arial",12));
		
		album.setLayoutX(250);
		album.setLayoutY(40);
		album.setFont(Font.font("Arial",12));
		
		year.setLayoutX(250);
		year.setLayoutY(55);
		year.setFont(Font.font("Arial",12));
		
		for(int i=0;i<Library.lib.size();i++){
			obs.add(Library.lib.get(i));
		}
		listview.setItems(obs);
		
		listview.getSelectionModel().selectedItemProperty().addListener((obs,oldval,newval)->details(primaryStage));
		listview.getSelectionModel().select(0);
		
		primaryStage.setTitle("Playlist");
		AnchorPane anchorpane = new AnchorPane();
		
		anchorpane.getChildren().addAll(listview,song,artist,album,year);
		
		
		scene = new Scene(anchorpane,650,250);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	private void details(Stage a){
		song.setText("Song: "+listview.getSelectionModel().getSelectedItem().song);
		artist.setText("Artist: "+listview.getSelectionModel().getSelectedItem().artist);
		if(listview.getSelectionModel().getSelectedItem().album != null){
			album.setText("Artist: "+listview.getSelectionModel().getSelectedItem().album);
		}
		if(listview.getSelectionModel().getSelectedItem().year != null){
			year.setText("Artist: "+listview.getSelectionModel().getSelectedItem().year);
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
