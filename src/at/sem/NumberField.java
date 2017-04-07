package at.sem;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class NumberField{
	
	int hits;
	Group walls;
	
	public NumberField(int hits) {
		this.hits = hits;
		
		walls=new Group();
		Label l=new Label(hits+"");
		l.translateXProperty().set(15);
		l.translateYProperty().set(12.5);
		Rectangle body=new Rectangle(50,50);
		body.setFill(Color.NAVY);
		l.setTextFill(Color.ANTIQUEWHITE);
		walls.getChildren().add(body);
		walls.getChildren().add(l);
	}
	
	public int getHits() {
		return hits;
	}
	public void setHits(int hits) {
		this.hits = hits;
	}
	public Group getWalls() {
		return walls;
	}
	public void setWalls(Group walls) {
		this.walls = walls;
	}
	
	public void reduce()
	{
		hits--;
	}
}
