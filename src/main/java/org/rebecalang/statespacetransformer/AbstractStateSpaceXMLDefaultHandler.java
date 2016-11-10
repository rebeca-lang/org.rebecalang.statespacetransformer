package org.rebecalang.statespacetransformer;

import java.util.Set;

import org.xml.sax.helpers.DefaultHandler;

public abstract class AbstractStateSpaceXMLDefaultHandler extends DefaultHandler {
	
	protected String output;
	protected Set<StateSpaceTransformingFeature> analysisFeatures;
	
	public AbstractStateSpaceXMLDefaultHandler(String output, Set<StateSpaceTransformingFeature> analysisFeatures) {
		this.output = output;
		this.analysisFeatures = analysisFeatures;
	}
	
	
	
}
