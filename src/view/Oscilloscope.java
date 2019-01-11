package view;
	
import javax.swing.JButton;

import com.rapplogic.xbee.api.XBeeException;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import model.Signal;
import model.SignalTools;
import model.xbee.XBeeListener;
import model.SIGTYPE;

public class Oscilloscope extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			root.setCenter(createContent());
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
		try {
			XBeeListener test = new XBeeListener();
		} catch (XBeeException e) {
			//e.printStackTrace()
			System.out.println(e.getMessage());
		}
		launch(args);
	}
	private Node createContent() {
		Screen ecran = new Screen();
		Signal sig = new Signal(SIGTYPE.SINE);
		ecran.addSignal(sig);
		return ecran;
	}
	/*public void stop() {
		
	}*/
	
	/*public void init() {
		
	}*/
	Button bouton = new Button("A");
	
}