package model;

import java.util.AbstractList;
import java.util.EventListener;

public interface SignalListener extends EventListener{
	public void EchsAdded(AbstractList<Double> echsAdd);
	public void SignalCleared();
}