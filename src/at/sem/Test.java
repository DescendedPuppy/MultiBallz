package at.sem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Labeled;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Test extends Application {

	public ArrayList<Group> del;
	public static Pane pane;
	Canvas tracker = new Canvas();
	boolean trace = true;
	public ArrayList<Circle> balls=new ArrayList<Circle>();
	int round = 0;
	public Group fields = new Group();
	HashMap <Group,NumberField> hitCounter=new HashMap<Group,NumberField>();
	private Timeline loop;
	int trackerX;
	Stage prim;
	public Pane trackerPane=new Pane();
	Scene scene;
	
	double deltaX;
	double deltaY;

	@Override
	public void start(final Stage primaryStage) {
		pane = new Pane();
		scene = new Scene(pane, 500, 800);
		primaryStage.setTitle("Game");
		primaryStage.setScene(scene);
		primaryStage.show();
		prim=primaryStage;
		
		Circle ball = new Circle(5, Color.BLUE);
		ball.relocate((scene.getWidth()/2)-5, scene.getHeight()-20);
		balls.add(ball);
		trackerX=(int) ((scene.getWidth()/2));
		
		trackerPane.getChildren().addAll(tracker);
		pane.getChildren().addAll(ball);
		pane.getChildren().addAll(fields);
		pane.getChildren().addAll(trackerPane);
		shoot();
	}

	public double getDeltaX() {
		return deltaX;
	}

	public void setDeltaX(double deltaX) {
		this.deltaX = deltaX;
	}

	public double getDeltaY() {
		return deltaY;
	}

	public void setDeltaY(double deltaY) {
		this.deltaY = deltaY;
	}

	public void reset() {
		balls.get(0).relocate((balls.get(0).getBoundsInParent().getMaxX() + balls.get(0).getBoundsInParent().getMinX()) / 2, scene.getHeight()-15);
		generateFields();
//		Circle ball1 = new Circle(5, Color.BLUE);
//		ball1.relocate((balls.get(0).getBoundsInParent().getMaxX() + balls.get(0).getBoundsInParent().getMinX()) / 2, 330);
//		balls.add(ball1);
		
		trackerX = (int) ((balls.get(0).getBoundsInParent().getMaxX() + balls.get(0).getBoundsInParent().getMinX()) / 2);
		trace = true;
	}

	public void generateFields() {
		fields.getChildren().forEach(n -> {
			n.translateYProperty().set(n.getTranslateY() + 50);
		});
		if (fields.getBoundsInParent().getMaxY() >= 700) {
			prim.close();
			System.out.println("exiting");
		}
		round++;
		Random r = new Random();
		for (int i = 0; i < scene.getWidth()/50; i++) {
			if (r.nextBoolean()) {
				NumberField n1 = new NumberField(r.nextInt((round * 2) + 5));
				n1.getWalls().translateXProperty().set(i * 50);
				fields.getChildren().addAll(n1.getWalls());
				hitCounter.put(n1.getWalls(), n1);
			}
		}
	}

	public void hit(Circle c) {
		del=new ArrayList<Group>();
		for (Node n : fields.getChildren()) {
			Bounds f = n.getBoundsInParent();
			if (c.getBoundsInParent().intersects(f.getMinX(), f.getMinY() - 1, 52, 50)
					|| c.getBoundsInParent().intersects(f.getMinX() - 1, f.getMinY(), 50, 52)) {
				bounce(((Group) n), c);
			}
		}
		fields.getChildren().removeAll(del);
	}

	public void shoot() {
		pane.setOnMouseMoved(e -> {
			if (trace) {
				tracker = new Canvas(500, 800);
				GraphicsContext context = tracker.getGraphicsContext2D();
				context.clearRect(0, 0, tracker.getWidth(), tracker.getHeight());
				context.strokeLine(trackerX, scene.getHeight()-12.5, e.getSceneX()-2.5, e.getSceneY());
				trackerPane.getChildren().clear();
				trackerPane.getChildren().add(tracker);
			}
		});

		pane.setOnMouseClicked(event1 -> {
			if (trace == true) {
				trackerPane.getChildren().remove(tracker);
				trace = false;
				for (int p = 0; p < balls.size(); p++) {
					int i = p;

					loop = new Timeline(new KeyFrame(Duration.millis(10), new EventHandler<ActionEvent>() {
						{
							setDeltaY(((event1.getSceneY() - (scene.getHeight()-15)) / 200));
							setDeltaX(((event1.getSceneX() - trackerX) / 200));
						}
						@Override
						public void handle(final ActionEvent t) {
							balls.get(i).setLayoutX(balls.get(i).getLayoutX() + deltaX);
							balls.get(i).setLayoutY(balls.get(i).getLayoutY() + deltaY);
							hit(balls.get(i));
							final Bounds bounds = pane.getBoundsInLocal();
							final boolean atRightBorder = balls.get(i)
									.getLayoutX() >= (bounds.getMaxX() - balls.get(i).getRadius());
							final boolean atLeftBorder = balls.get(i)
									.getLayoutX() <= (bounds.getMinX() + balls.get(i).getRadius());
							final boolean atBottomBorder = balls.get(i)
									.getLayoutY() >= (bounds.getMaxY() - balls.get(i).getRadius());
							final boolean atTopBorder = balls.get(i)
									.getLayoutY() <= (bounds.getMinY() + balls.get(i).getRadius());
							if (atRightBorder || atLeftBorder) {
								deltaX *= -1;
							}
							if (atTopBorder) {
								deltaY *= -1;
							}
							if (atBottomBorder) {
								deltaX = 0;
								deltaY = 0;
								reset();
								loop.stop();
							}
						}
					}));
					loop.setCycleCount(Timeline.INDEFINITE);
					loop.play();
				}
				try {
					TimeUnit.MILLISECONDS.sleep(100);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		});
	}

	public void bounce(Group n, Circle c) {
		if (n.getBoundsInParent().getMaxY()-1 <= c.getBoundsInParent().getMinY()
				|| n.getBoundsInParent().getMinY() >= c.getBoundsInParent().getMaxY()-1)
			setDeltaY(deltaY*-1);
		else
			setDeltaX(deltaX*-1);
		
		((Labeled) n.getChildren().get(1)).setText(Integer.parseInt(((Labeled) n.getChildren().get(1)).getText())-1+"");
		if(Integer.parseInt(((Labeled) n.getChildren().get(1)).getText())<=0)
			del.add(n);
	}

	public static void main(final String[] args) {
		launch(args);
	}
}