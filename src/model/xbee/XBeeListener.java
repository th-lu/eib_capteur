package model.xbee;

import java.util.AbstractList;
import java.util.Vector;

import javax.swing.event.EventListenerList;

import com.rapplogic.xbee.api.ApiId;
import com.rapplogic.xbee.api.PacketListener;
import com.rapplogic.xbee.api.XBee;
import com.rapplogic.xbee.api.XBeeException;
import com.rapplogic.xbee.api.XBeeResponse;
import com.rapplogic.xbee.api.wpan.IoSample;
import com.rapplogic.xbee.api.wpan.RxResponseIoSample;

import model.Signal;
import model.SignalListener;

public class XBeeListener {

	Signal reception;

	public XBeeListener(Signal reception) throws XBeeException {
		this.reception = reception;
		reception.setUnits("Volts");

		XBee xbee = new XBee();
		xbee.open("COM3", 9600);

		xbee.addPacketListener(new PacketListener() {

			public void processResponse(XBeeResponse response) {
				// System.out.println("Reçu");
				if (response.getApiId() == ApiId.RX_16_IO_RESPONSE || response.getApiId() == ApiId.RX_64_IO_RESPONSE) {
					RxResponseIoSample ioSample = (RxResponseIoSample) response;
					Vector<Double> samples = new Vector<Double>();
					for (IoSample sample : ioSample.getSamples()) {
						// System.out.println("Analog D0 reading is " + sample.getAnalog0());
						samples.add(new Double(sample.getAnalog0() * 3.3 / 1023));
					}
					int taille = reception.getEchs().size();
					// System.out.println(reception.getEchs().toString());
					if (taille > 5000)// 10s de donn�e max pour ne pas surcharger la m�moire.
						reception.clear();
					reception.addEchsAtEnd(samples);
				}
			}
		});
	}

	public Signal getSignal() {
		return reception;
	}

	public void setSignal(Signal sig) {
		this.reception = sig;
	}
}