package view;

import java.util.AbstractList;
import javafx.scene.paint.Color;

public class Grid {
	private String xlabel;
	private String ylabel;
	private double xstep;
	private double ystep;
	private Color gridcolor;
	boolean graduation;
	private AbstractList<Double> xgrad;
	private AbstractList<String> xgrad_label;
	private AbstractList<Double> ygrad;
	private AbstractList<String> ygrad_label;
	
	public void setXLabel(String xlabel) {
		this.xlabel = xlabel;
	}
	public String getXLabel() {
		return xlabel;
	}
	public void setYLabel(String ylabel) {
		this.ylabel = ylabel;
	}
	public String getYLabel() {
		return ylabel;
	}
	public double getXStep() {
		return xstep;
	}
	public void setXStep(double xstep) {
		this.xstep = xstep;
	}
	public double getYStep() {
		return ystep;
	}
	public void setYStep(double ystep) {
		this.ystep = ystep;
	}
	public Color getGridColor() {
		return gridcolor;
	}
	public void setGridColor(Color c) {
		gridcolor = c;
	}
	public boolean showGraduation() {
		return graduation;
	}
	public void setShowGraduation(boolean b) {
		graduation = b;
	}
	
	public AbstractList<Double> getXGrad(){
		return xgrad;
	}
	public AbstractList<String> getXGradLabel(){
		return xgrad_label;
	}
	public void setXGrad(AbstractList<Double> xgrad){
		this.xgrad =  xgrad;
	}
	public void setXGradLabel(AbstractList<String> xgrad_label){
		this.xgrad_label = xgrad_label;
	}
	public AbstractList<Double> getYGrad(){
		return ygrad;
	}
	public AbstractList<String> getYGradLabel(){
		return ygrad_label;
	}
	public void setYGrad(AbstractList<Double> ygrad){
		this.ygrad =  ygrad;
	}
	public void setYGradLabel(AbstractList<String> ygrad_label){
		this.ygrad_label = ygrad_label;
	}
}