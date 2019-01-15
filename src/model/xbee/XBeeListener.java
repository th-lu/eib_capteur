package model.xbee;

import java.util.AbstractList;
import java.util.Vector;

import com.rapplogic.xbee.api.ApiId;
import com.rapplogic.xbee.api.PacketListener;
import com.rapplogic.xbee.api.XBee;
import com.rapplogic.xbee.api.XBeeException;
import com.rapplogic.xbee.api.XBeeResponse;
import com.rapplogic.xbee.api.wpan.IoSample;
import com.rapplogic.xbee.api.wpan.RxResponseIoSample;

import model.Signal;

public class XBeeListener {

	Signal reception;

	public XBeeListener(Signal reception) throws XBeeException {
		this.reception = reception;
		reception.setUnits("Volts");

		XBee xbee = new XBee();
		xbee.open("COM3", 9600);

		xbee.addPacketListener(new PacketListener() {

			public void processResponse(XBeeResponse response) {
				//System.out.println("ReÃ§u");
				if (response.getApiId() == ApiId.RX_16_IO_RESPONSE || response.getApiId() == ApiId.RX_64_IO_RESPONSE) {
					RxResponseIoSample ioSample = (RxResponseIoSample) response;
					Vector<Double> samples = new Vector<Double>();
					for (IoSample sample : ioSample.getSamples()) {
						//System.out.println("Analog D0 reading is " + sample.getAnalog0());
						samples.add(new Double(sample.getAnalog0() * 3.3 / 1023));
					}
					int taille = reception.getEchs().size();
					//System.out.println(reception.getEchs().toString());
					if (taille > 10000) {// 10s de donnée max pour ne pas surcharger la mémoire.
						reception.removeEchsAt(0, samples.size());
						taille -= samples.size();
					}
					reception.addEchsAt(taille, samples);
				}
			}
		});
	}

	public Signal getSignal() {
		return reception;
	}
}