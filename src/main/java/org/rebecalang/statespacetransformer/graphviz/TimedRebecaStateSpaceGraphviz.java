package org.rebecalang.statespacetransformer.graphviz;

import java.io.IOException;
import java.util.Set;

import org.rebecalang.statespacetransformer.StateSpaceTransformingFeature;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class TimedRebecaStateSpaceGraphviz extends
	CoreRebecaStateSpaceGraphviz {
	
	public final static String TIME = "time";

	public TimedRebecaStateSpaceGraphviz(String output,
			Set<StateSpaceTransformingFeature> analysisFeatures) throws IOException {
		super(output, analysisFeatures);
	}
	
	int shiftTime;
	int execTime;
	boolean timedTR;
	public void startElement(String uri, String localName,String qName, 
            Attributes attributes) throws SAXException {
		if (qName.equalsIgnoreCase(TIME)) {
			timedTR = true;
			String label = "time +=" + attributes.getValue("value");
			try {
				outputFile.writeBytes(label);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			if (qName.equalsIgnoreCase(TRANSITION)) {
				shiftTime = Integer.parseInt(attributes.getValue("shift"));
				execTime = Integer.parseInt(attributes.getValue("executionTime"));
			}
			super.startElement(uri, localName, qName, attributes);
		}
	}
	
	public void endElement(String uri, String localName,
			String qName) throws SAXException {
		if (qName.equalsIgnoreCase(TRANSITION)) {
			try {
				String label = " \\n @" + execTime + (shiftTime == 0 ? "" : (" -> shift(+" + shiftTime + ")"));
				if (timedTR) {
					label += "\", style=\"bold\", color=\"red";
					timedTR = false;
				}
				outputFile.writeBytes(label);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} 
		super.endElement(uri, localName, qName);
	}
}
