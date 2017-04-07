package at.sem;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import at.sem.server.Client;

public class JoinGame {

	public JoinGame m;
	public JoinGame() {m=this;}
	
	public BorderPane start(){
		BorderPane pane = new BorderPane();
		
		TextField ip = new TextField();
		ip.setPromptText("Ip-Address");
		
		Button enterIp = new Button();
		enterIp.setText("Enter Ip");
		enterIp.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				Client client = new Client(ip.getText(), m);
			}
		});
		
		return pane;
	}
	
	public void outcome(String outcome, String error){
		switch(outcome){
			case "success":
				break;
			case "fail":
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Ballz-Connection Error");
				alert.setContentText(error);
				alert.showAndWait();
		}
	}

}
