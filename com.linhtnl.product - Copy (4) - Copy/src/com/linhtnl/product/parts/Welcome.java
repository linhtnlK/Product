 
package com.linhtnl.product.parts;

import javax.inject.Inject;
import javax.annotation.PostConstruct;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class Welcome {
	private String username = "";
	Label lbl;

	
	@Inject
	public Welcome() {
		
	}
	
	@PostConstruct
	public void postConstruct(Composite parent) {
		lbl = new Label(parent, SWT.BORDER);
	}
	@Inject
	@Optional
	public void clearAll(@UIEventTopic("Clear") String message) {			
			lbl.setText("");		
	}
	@Inject
	@Optional
	public void getEvent(@UIEventTopic("Welcome") String message) {
		username = message;
		lbl.setText("Welcome "+username+"!");
	}
	
	
	
	
}