package model.xbee;

import com.rapplogic.xbee.api.ApiId;
import com.rapplogic.xbee.api.PacketListener;
import com.rapplogic.xbee.api.XBee;
import com.rapplogic.xbee.api.XBeeException;
import com.rapplogic.xbee.api.XBeeResponse;
import com.rapplogic.xbee.api.wpan.IoSample;
import com.rapplogic.xbee.api.wpan.RxResponseIoSample;

public class XBeeListener {

	public XBeeListener() throws XBeeException {
		XBee xbee = new XBee();
		xbee.open("/dev/ttyUSB1", 9600);

		xbee.addPacketListener(new PacketListener() {

			public void processResponse(XBeeResponse response) {
				System.out.println("Reçu");
				if (response.getApiId() == ApiId.RX_16_IO_RESPONSE || response.getApiId() == ApiId.RX_64_IO_RESPONSE) {
					RxResponseIoSample ioSample = (RxResponseIoSample) response;
					for (IoSample sample : ioSample.getSamples())
						System.out.println("Analog D0 reading is " + sample.getAnalog0());
				}
			}
		});
	}
}