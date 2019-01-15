package view;

import java.util.AbstractList;
import java.util.Vector;

import javax.swing.event.EventListenerList;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import model.Signal;
import model.SignalListener;
import model.SignalTools;

public class Screen extends LineChart<Number, Number> implements SignalListener {

	private Grid grille;
	int fe;// To know the time axis.

	public Screen() {
		super(new NumberAxis(), new NumberAxis());
		grille = new Grid();
		this.getXAxis().setAutoRanging(false);
		this.getYAxis().setAutoRanging(false);
		this.getXAxis().setUpperBound(5);
		this.getYAxis().setLowerBound(2.4);
		this.getYAxis().setUpperBound(2.7);
		this.setLegendVisible(false);
		this.setCreateSymbols(false);
	}

	public void addSignal(Signal sig) {
		AbstractList<Double> values = sig.getEchs();
		int n = values.size();
		fe = sig.getFe();
		AbstractList<Double> t = new Vector<Double>(n);
		for (int i = 0; i < n; i++)
			t.add(new Double((double) i / fe));

		XYChart.Series<Number, Number> curve = new XYChart.Series<Number, Number>();
		for (int i = 0; i < n; i++)
			curve.getData().add(new XYChart.Data<Number, Number>(t.get(i), values.get(i)));
		this.getData().add(curve);
	}

	public void addDFT(Signal sig) {
		AbstractList<Double> dft = SignalTools.DFTM(sig.getEchs());
		int n = dft.size();
		int fe = sig.getFe();
		AbstractList<Double> freq = new Vector<Double>(n);
		for (int i = 0; i < n; i++)
			freq.add(new Double((double) (i * fe / n)));

		XYChart.Series<Number, Number> spectrum = new XYChart.Series<Number, Number>();
		for (int i = 0; i < n; i++)
			spectrum.getData().add(new XYChart.Data<Number, Number>(freq.get(i), dft.get(i)));
		this.getData().add(spectrum);
	}

	public void removeSignal(int index) {
		this.getData().remove(index);
	}

	public NumberAxis getXAxis() {
		return (NumberAxis) super.getXAxis();
	}

	public NumberAxis getYAxis() {
		return (NumberAxis) super.getYAxis();
	}

	@Override
	public void EchsAdded(AbstractList<Double> echsAdd) {
		ObservableList<Data<Number, Number>> data = this.getData().get(0).getData();
		int n = data.size();
		//Vector<Double> t = new Vector<Double>();
		Vector<XYChart.Data<Number, Number>> d = new Vector<XYChart.Data<Number, Number>>();
		for (int i = n; i < n + echsAdd.size(); i++)
			d.add(new XYChart.Data<Number, Number>(new Double((double) i / fe),echsAdd.get(i-n)));
		Platform.runLater(new Runnable() {
		    @Override
		    public void run() {
		        data.addAll(d);
		    }
		});
	}

	@Override
	public void SignalCleared() {
		ObservableList<Data<Number, Number>> data = this.getData().get(0).getData();
		Platform.runLater(new Runnable() {
		    @Override
		    public void run() {
		        data.clear();
		    }
		});
	}
}