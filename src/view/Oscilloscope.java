package view;

import java.util.AbstractList;
import java.util.Vector;

import com.rapplogic.xbee.api.XBeeException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.Signal;
import model.SignalTools;
import model.xbee.XBeeListener;
import model.SIGTYPE;

public class Oscilloscope extends Application {

	Screen ecran;
	Signal reception;

	@Override
	public void start(Stage primaryStage) {
		//reception = new Signal(new Vector<Double>(), 1000, 10);
		reception = new Signal("data.txt", 1000, "Volts", 0);
		try {
			XBeeListener listener = new XBeeListener(reception);
		} catch (XBeeException e) {
			// e.printStackTrace()
			System.out.println(e.getMessage());
		}
		try {
			BorderPane root = new BorderPane();
			root.setCenter(createContent());
			// ecran.addSignal(new Signal(SIGTYPE.SINE));
			ecran.addSignal(reception);
			ecran.getYAxis().setUpperBound(2.6);
			ecran.getYAxis().setLowerBound(2.3);
			ecran.getYAxis().setUpperBound(1.65);
			ecran.getYAxis().setLowerBound(1.55);
			// reception.addSignalListener(ecran);

			Scene scene = new Scene(root, 1600, 500);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("TP Java Oscilloscope");
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	private Node createContent() {
		ecran = new Screen();
		Text bpm_print = new Text("?? bpm");
		Button computeBPM_button = new Button("Calcul bpm");
		computeBPM_button.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				AbstractList<Double> fft = SignalTools.DFTM(reception.getEchs());
				if (fft == null)
					return;
				int n = fft.size();

				int fe = reception.getFe();
				int dep = ((int) (0.7* (double) n)) / fe;
				int stop = ((int) (4 * (double) n)) / fe;
				// We have to avoid the first pic and human can't have more than 240bpm
				double mmax = fft.get(dep);
				int imax = dep;
				for (int i = dep + 1; i < stop; i++)
					if (mmax < fft.get(i)) {
						mmax = i * ((double) n) / ((double) fe);
						imax = i;
					}
				double bpm = 60 *imax*fe/n;
				String bpm_str = Double.toString(bpm) + "bpm";
				Platform.runLater(new Runnable() {

					public void run() {
						bpm_print.setText(bpm_str);
					}
				});
			}
		});
		VBox main_vbox = new VBox();
		HBox bpm_hbox = new HBox();

		main_vbox.getChildren().add(ecran);
		bpm_hbox.getChildren().add(computeBPM_button);
		bpm_hbox.getChildren().add(bpm_print);
		main_vbox.getChildren().add(bpm_hbox);
		return main_vbox;
	}
}