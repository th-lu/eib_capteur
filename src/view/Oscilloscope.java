package view;
	
import java.util.ArrayList;

import com.rapplogic.xbee.api.XBeeException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.Signal;
import model.SignalTools;
import model.xbee.XBeeListener;
import model.SIGTYPE;

public class Oscilloscope extends Application {
	
	Screen ecran;
	Signal reception;
	@Override
	public void start(Stage primaryStage) {
		reception = new Signal(new ArrayList<Double>(), 1000, 10);
		try {
			XBeeListener listener = new XBeeListener(reception);
		} catch (XBeeException e) {
			//e.printStackTrace()
			System.out.println(e.getMessage());
		}
		try {
			BorderPane root = new BorderPane();
			root.setCenter(createContent());
			//ecran.addSignal(reception);
			ecran.addSignal(new Signal("data.txt", 1000, "Volts", 0));
			ecran.getYAxis().setLowerBound(1.45);
			ecran.getYAxis().setUpperBound(1.7);
			
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("TP Java Oscilloscope");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	private Node createContent() {
		ecran = new Screen();
		Button update = new Button("Actualiser");
		update.setOnMouseClicked(
				new EventHandler<MouseEvent>() {
					public void handle(MouseEvent e) {
						ecran.removeSignal(0);
						ecran.addSignal(reception);
					}
				});
		GridPane pane = new GridPane();
		
		pane.add(ecran, 0, 0);
		pane.add(update, 0,1);
		return pane;
	}
}