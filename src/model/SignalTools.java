package model;

import java.util.AbstractList;
import java.util.Vector;
import java.util.List;

public class SignalTools{

	private SignalTools() {
		
	}
	static public double mean(Signal sig) {
		List<Double> values = sig.getEchs();
		return SignalTools.mean(values);
	}
	static public double mean(List<Double> echs) {
		List<Double> values = echs;
		double n = (new Integer(values.size())).doubleValue();
		double mean = values.get(0)/n;
		for(int i = 1;i<n;i++)
			mean += values.get(i)/n;
		return mean;
	}
	static public double variance(Signal sig) {
		List<Double> values = sig.getEchs();
		return SignalTools.variance(values);
	}
	static public double variance(List<Double> values) {
		double n = (new Integer(values.size())).doubleValue();
		double a = (1/((n-1)*values.get(0)));
		double var = a*a;
		for(int i = 1;i<values.size();i++) {
			a = (1/((n-1)*values.get(i)));
			var += a*a;
		}
		return (var-n/(n-1)*SignalTools.mean(values));
	}
	static public AbstractList<Double> DFTM(AbstractList<Double> sig)//Module
	{ 
		int n = sig.size();
		AbstractList<Double> values = new Vector<Double>(2*n);
		for(int i=0;i<n;i++) values.add(sig.get(i));
		
		double m = Math.floor(Math.log(n)/Math.log(2));
		//Signal must have 2^m echs
		if (m < Math.log(n)/Math.log(2))
		{
			m++;
			for (int i=n;i<(int)Math.pow(2, m);i++) values.add(new Double(0));
			n=(int) Math.pow(2,m);
		}
		for(int i =0; i<n; i++)
			values.add(new Double(0));//for imaginary part
		AbstractList<Double> complex_result = FFT(values, n);
		Vector<Double> mod = new Vector<Double>(n);
		for(int i=0;i<n;i++)
			mod.add(Module(complex_result.get(i),complex_result.get(i+n)));
		return mod;
	}
	static public AbstractList<Double> DFTP(AbstractList<Double> sig)//Phase
	{
		int n = sig.size();
		AbstractList<Double> values = new Vector<Double>(2*n);
		for(int i=0;i<n;i++) values.add(sig.get(i));
		
		double m = Math.floor(Math.log(n)/Math.log(2));
		//Signal must have 2^m echs
		if (m < Math.log(n)/Math.log(2))
		{
			m++;
			for (int i=n;i<(int)Math.pow(2, m);i++) values.add(new Double(0));
			n=(int) Math.pow(2,m);
		}
		for(int i =0; i<n; i++)
			values.add(new Double(0));//for imaginary part
		AbstractList<Double> complex_result = FFT(values, n);
		Vector<Double> p = new Vector<Double>(n);
		for(int i=0;i<n;i++)
			p.add(Phase(complex_result.get(i),complex_result.get(i+n)));
		return p;
	}
	static private AbstractList<Double> FFT(AbstractList<Double> values, int n)
	{//size of values is 2*n : 0->(n-1)= real part; n->(2n-1)=imaginary part
		if (n==1) return values;
		
		AbstractList<Double> pair=new Vector<Double>(2*n);
		AbstractList<Double> impair=new Vector<Double>(2*n);
		for (int i=0; i<2*n; i++)
		{
			if (i%2==0) pair.add(values.get(i));
			else impair.add(values.get(i));
		}
		//Add imaginary part to real part to call recursive function
		pair = FFT(pair,n/2);//X(0) to X(N/2-1) : size = N/2+N/2
		impair = FFT(impair,n/2);//X(N/2) to X(N-1) : size = N/2+N/2
		for(int i=0;i<n/2;i++)
		{
			Double tr=pair.get(i);
			Double ti=pair.get(i+n/2); //t = X(i)
			
			Double t2r=impair.get(i);
			Double t2i=impair.get(i+n/2); //t2=X(i+n/2)
			
			pair.set(i, tr+Math.cos(-2*Math.PI*i/n)*t2r);
			pair.set(i+n/2, ti+Math.sin(-2*Math.PI*i/n)*t2i);//X(i) = X(i)+exp(-2pi*j*i/n)
			
			impair.set(i, tr-Math.cos(-2*Math.PI*i/n)*t2r);
			impair.set(i+n/2, ti-Math.sin(-2*Math.PI*i/n)*t2i);//X(i+n/2)=X(i)-exp(-2pi*j*i/n)
		}
		pair.addAll(n/2, impair.subList(0, n/2));
		pair.addAll(impair.subList(n/2,n));
		return pair;
	}
	static private Double Module(Double re, Double im){return Math.sqrt(re*re+im*im);}
	static private Double Phase(Double re, Double im) {return Math.atan2(im, re);}
	/*{
		if (im > 0) return Math.acos(re/Module(re,im));
		else return -Math.acos(re/Module(re,im));
	}*/
}