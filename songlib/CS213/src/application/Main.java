/*
 * Written by Jason Holley and Rituraj Kumar
 */

package application;



import java.io.IOException;
import java.util.Optional;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.beans.value.ChangeListener;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;


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

		Library songlib = new Library("Songs");
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


		VBox textFields = new VBox(10.0);
		TextField songField = new TextField();
		TextField artistField = new TextField();
		TextField albumField = new TextField();
		TextField yearField = new TextField();

		VBox textText = new VBox(20.5);
		Text songText = new Text("*Song Name:");
		Text artistText = new Text("*Artist Name:");
		Text albumText = new Text("Album Name:");
		Text yearText = new Text("Release Year:\n\n");
		Text required = new Text("* fields required to add new song.");

		textFields.getChildren().addAll(songField, artistField, albumField, yearField);
		textText.getChildren().addAll(songText, artistText, albumText, yearText,required);

		HBox addEdit = new HBox(10.0);
		HBox deleter = new HBox();



		Button addButton = new Button("Add");
		Button editButton = new Button("Edit currently selected song");
		Button deleteButton = new Button("Delete");

		addButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e){
				
				Alert youSure = new Alert(AlertType.CONFIRMATION);
				youSure.setTitle("Delete Song");
				youSure.setHeaderText(null);
				youSure.setContentText("Are you sure you want to delete the currently selected song?");

				Optional<ButtonType> result = youSure.showAndWait();
				if(result.get() == ButtonType.OK){
				if(songField.getText().isEmpty() || artistField.getText().isEmpty()){
					Alert emptyFields = new Alert(AlertType.INFORMATION);
					emptyFields.setTitle("Error");
					emptyFields.setHeaderText(null);
					emptyFields.setContentText("Could not add song. Neither the \"Song name\" nor \"Artist name\" field can be empty.");

					emptyFields.showAndWait();
				}
				else{
					try {
						songlib.addSong(songField.getText(),artistField.getText(),albumField.getText(),yearField.getText());
						songField.clear();
						artistField.clear();
						albumField.clear();
						yearField.clear();
					} catch (IOException e1) {
						e1.printStackTrace();
					}

					while(!obs.isEmpty()){
						obs.remove(0);
					}
					for(int a=0;a<Library.lib.size();a++){
						obs.add(Library.lib.get(a));
					}

				}
			}
		});

		deleteButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e){
				Alert youSure = new Alert(AlertType.CONFIRMATION);
				youSure.setTitle("Delete Song");
				youSure.setHeaderText(null);
				youSure.setContentText("Are you sure you want to delete the currently selected song?");

				Optional<ButtonType> result = youSure.showAndWait();
				if(result.get() == ButtonType.OK){
					int p = listview.getSelectionModel().getSelectedIndex();
					try {
						songlib.deleteSong(p, listview.getSelectionModel().getSelectedItem().song, listview.getSelectionModel().getSelectedItem().artist);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					listview.getSelectionModel().select(p);
					while(!obs.isEmpty()){
						obs.remove(0);
					}
					for(int a=0;a<Library.lib.size();a++){
						obs.add(Library.lib.get(a));
					}
				}
			}
			}
		});

		editButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e){
				if(songField.getText().isEmpty() && artistField.getText().isEmpty() && albumField.getText().isEmpty() && yearField.getText().isEmpty()){
					Alert emptyFields = new Alert(AlertType.INFORMATION);
					emptyFields.setTitle("Error");
					emptyFields.setHeaderText(null);
					emptyFields.setContentText("You must fill in at least one field to change song information.");

					emptyFields.showAndWait();
				}
				else{
					Alert youSure = new Alert(AlertType.CONFIRMATION);
					youSure.setTitle("Delete Song");
					youSure.setHeaderText(null);
					youSure.setContentText("Are you sure you want to edit the currently selected song with the entered details?");

					Optional<ButtonType> result = youSure.showAndWait();
					if(result.get() == ButtonType.OK){
						int p = listview.getSelectionModel().getSelectedIndex();
						if(!songField.getText().isEmpty() && !artistField.getText().isEmpty()){
							if(!songField.getText().equalsIgnoreCase(song.toString()) && !artistField.getText().equalsIgnoreCase(artist.toString())){
								try {
									songlib.editSongNameAndArtistName(listview.getSelectionModel().getSelectedItem(), songField.getText(), artistField.getText());
								} catch (IOException e1) {
									e1.printStackTrace();
								}
							}
							else{
								if(!songField.getText().equalsIgnoreCase(song.toString())){
									try {
										songlib.editSongName(listview.getSelectionModel().getSelectedItem(), songField.getText());
									} catch (IOException e1) {
										e1.printStackTrace();
									}
									}
								else{
									try {
										songlib.editArtist(listview.getSelectionModel().getSelectedItem(), artistField.getText());
									} catch (IOException e1) {
										e1.printStackTrace();
									}
									}

								}

							}
						if(!albumField.getText().isEmpty()){
							try {
								songlib.editAlbum(p, listview.getSelectionModel().getSelectedItem(), albumField.getText());
							} catch (IOException e1) {

								e1.printStackTrace();
							}
						}
						if(!yearField.getText().isEmpty()){
							try {
								songlib.editAlbum(p, listview.getSelectionModel().getSelectedItem(), yearField.getText());
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}

						listview.getSelectionModel().select(p);
						while(!obs.isEmpty()){
							obs.remove(0);
						}
						for(int a=0;a<Library.lib.size();a++){
							obs.add(Library.lib.get(a));
						}
					}
				}
			}
		});


		addEdit.getChildren().addAll(addButton,editButton);
		deleter.getChildren().add(deleteButton);

		anchorpane.getChildren().addAll(listview,song,artist,album,year,textText,textFields,addEdit,deleter);

		songText.setFont(Font.font("Arial",12));
		artistText.setFont(Font.font("Arial",12));
		albumText.setFont(Font.font("Arial",12));
		yearText.setFont(Font.font("Arial",12));
		required.setFont(Font.font("Arial",12));

		AnchorPane.setTopAnchor(addEdit, 270.0);
		AnchorPane.setLeftAnchor(addEdit, 250.0);
		AnchorPane.setTopAnchor(textFields, 130.0);
		AnchorPane.setLeftAnchor(textFields, 330.0);
		AnchorPane.setTopAnchor(textText, 136.0);
		AnchorPane.setLeftAnchor(textText, 250.0);
		AnchorPane.setTopAnchor(deleter, 74.0);
		AnchorPane.setLeftAnchor(deleter, 250.0);


		scene = new Scene(anchorpane,650,400);

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void details(Stage a){
		if(obs.contains(listview.getSelectionModel().getSelectedItem())){
		song.setText("Song: "+listview.getSelectionModel().getSelectedItem().song);
		artist.setText("Artist: "+listview.getSelectionModel().getSelectedItem().artist);
		if(listview.getSelectionModel().getSelectedItem().album != null){
			album.setText("Album: "+listview.getSelectionModel().getSelectedItem().album);
			year.setLayoutY(55);
		}
		else{
			album.setText(null);
			year.setLayoutY(40);
		}
		if(listview.getSelectionModel().getSelectedItem().year != null){

			year.setText("Year: "+listview.getSelectionModel().getSelectedItem().year);
		}
		else{
			year.setText(null);
		}
		}
		else{
			song.setText(null);
			artist.setText(null);
			album.setText(null);
			year.setText(null);
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
