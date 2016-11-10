package org.rebecalang.statespacetransformer.graphviz;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Set;

import org.rebecalang.statespacetransformer.AbstractStateSpaceXMLDefaultHandler;
import org.rebecalang.statespacetransformer.StateSpaceTransformingFeature;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class CoreRebecaStateSpaceGraphviz extends AbstractStateSpaceXMLDefaultHandler {
	
	public final static String STATE = "state";
	public final static String TRANSITION = "transition";
	public final static String MESSAGE_SERVER = "messageserver";
	public final RandomAccessFile outputFile;
	
	public CoreRebecaStateSpaceGraphviz(String output, Set<StateSpaceTransformingFeature> analysisFeatures) throws IOException {
		super(output, analysisFeatures);
		outputFile = new RandomAccessFile(output, "rw");
		outputFile.setLength(0);
	}
	
	public void startDocument() throws SAXException {
		try {
			outputFile.writeBytes("digraph html {\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void endDocument() throws SAXException {
		try {
			outputFile.writeBytes("}");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void startElement(String uri, String localName,String qName, 
            Attributes attributes) throws SAXException {
		if (qName.equalsIgnoreCase(STATE)) {
			String label = "";
			String aps = attributes.getValue("atomicpropositions").trim();
			for (String ap : aps.split(","))
				label += "\\n" + ap;
			try {
				String stateId = "S" + attributes.getValue("id");
				outputFile.writeBytes(stateId + "[label=\"" + "S" + attributes.getValue("id") + ":" + label + "\"];\r\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(!analysisFeatures.contains(StateSpaceTransformingFeature.SIMPLIFIED)) {
			} else {
				
			}
		} else if (qName.equalsIgnoreCase(TRANSITION)) {
			String label = "S" + attributes.getValue("source") + " -> S" + attributes.getValue("destination") +
					"[label=\"";
			try {
				outputFile.writeBytes(label);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (qName.equalsIgnoreCase(MESSAGE_SERVER)) {
			String label = attributes.getValue("owner") + "." + attributes.getValue("title");
			try {
				outputFile.writeBytes(label);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void endElement(String uri, String localName,
			String qName) throws SAXException {
		if (qName.equalsIgnoreCase(STATE)) {
			if(!analysisFeatures.contains(StateSpaceTransformingFeature.SIMPLIFIED)) {

			} else {
				
			}
		} else if (qName.equalsIgnoreCase(TRANSITION)) {
			try {
				outputFile.writeBytes("\"];\r\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void characters(char ch[], int start, int length) throws SAXException {
		
	}

}
