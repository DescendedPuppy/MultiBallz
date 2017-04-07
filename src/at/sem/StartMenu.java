package at.sem;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class StartMenu {

	private int buttonPressed;
	
	public StartMenu() {}
	
	public void start(Stage primaryStage){
		BorderPane pane = new BorderPane();
		HBox hbox = new HBox();
		buttonPressed = 0;
		
		primaryStage.setTitle("Ballz");
		
		Button joinG = new Button();
		joinG.setText("Join Game");
		joinG.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				primaryStage.setTitle("Ballz-Join Game");
				buttonPressed = 1;
			}
		});
		
		Button createG = new Button();
		createG.setText("Create Game");
		createG.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				primaryStage.setTitle("Ballz-Create Game");
				buttonPressed = 2;
			}
		});
		
		
		Button options = new Button();
		options.setText("Options");
		options.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				primaryStage.setTitle("Ballz-Options");
				buttonPressed = 3;
			}
		});
		
		
		Button credits = new Button();
		credits.setText("Credits");
		credits.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				primaryStage.setTitle("Ballz-Credits");
				buttonPressed = 4;
			}
		});
		
		
		Button exit = new Button();
		exit.setText("Exit Game");
		exit.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				System.exit(0);
			}
		});
		
		switch(buttonPressed){
			case 1:
				JoinGame jG = new JoinGame();
				pane = jG.start();
				break;
			case 2:
				break;
			case 3:
				break;
			case 4:
				break;
			default:
				hbox.getChildren().add(joinG);
				hbox.getChildren().add(createG);
				hbox.getChildren().add(options);
				hbox.getChildren().add(credits);
				hbox.getChildren().add(exit);
				pane.setCenter(hbox);
		}
		
		Scene scene = new Scene(pane, 300, 350);
		primaryStage.setScene(scene);
	}
	
	public void setValue(Object target, Object value){
		
	}

}
