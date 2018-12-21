package model;

import java.util.ArrayList;
import java.util.Random;
import java.util.AbstractList;

public class Signal{
	
	private String unit;
	private AbstractList<Double> echs;
	private int nb_bits_quant;
	private int fe;//in Hz;
	
	public Signal(SIGTYPE type) {
		unit = "Volts";
		fe = 5000;
		int f=100;
		echs = new ArrayList<Double>();
		if (type == SIGTYPE.SINE) {
			for(int i = 0;i<512;i++)
				echs.add(new Double(Math.sin(2*Math.PI*i*f/fe)));		
		}
		if (type == SIGTYPE.SQUARE) {
			for(int i = 0; i<25; i++)
				echs.add(new Double(0));
			for(int i = 25; i<50; i++)
				echs.add(new Double(1));
			for (int i=50;i<75;i++)
				echs.add(new Double(0));
			for (int i=75;i<100;i++)
				echs.add(new Double(1));
		}
		if (type == SIGTYPE.NOISE) {
			Random rand = new Random();
			for(int i = 0; i<100;i++)
				echs.add(new Double(rand.nextGaussian()));
		}
		if (type == SIGTYPE.TEST)
		{
				for(int i = 0;i<4;i++)
					echs.add(new Double(Math.sin(2*Math.PI*i*f/fe)));
		}
	}
	public Signal(String fname) {
		//...
	}
	public Signal(AbstractList<Double> values,int fe, int nb_bits_quant) {
		this.echs = values;
		this.fe = fe;
		this.nb_bits_quant = nb_bits_quant;
	}
	public String getUnits() {
		return unit;
	}
	public void setUnits(String unit) {
		this.unit = unit;
	}
	public int getNbBitsQuant() {
		return nb_bits_quant;
	}
	public void setNbBitsQuant(int quant) {
		this.nb_bits_quant = quant;
	}
	public int getFe() {
		return fe;
	}
	public void setFe(int fe) {
		this.fe=fe;
	}
	public AbstractList<Double> getEchs(){
		return echs;
	}
	public void setEchs(AbstractList<Double> values){
		echs = values;
	}
	public void addEchAt(int index, double value) {
		echs.add(index,new Double(value));
	}
	public void removeEchAt(int index) {
		echs.remove(index);
	}
	public void addEchsAt(int index, AbstractList<Double> values) {
		echs.addAll(index, values);
	}
	public void removeEchsAt(int findex,int lindex) {
		for(int i = findex; i < lindex+1; i++)
			echs.remove(i);
	}
	//public static void main(String[] args) {
	//	Signal sig = new Signal(SIGTYPE.SINE);
	//	System.out.println(SignalTools.mean(sig));
	//	System.out.println(sig.getEchs());
	//}
}