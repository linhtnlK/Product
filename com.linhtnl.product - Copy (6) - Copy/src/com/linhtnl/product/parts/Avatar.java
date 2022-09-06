
package com.linhtnl.product.parts;

import javax.inject.Inject;

import javax.annotation.PostConstruct;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class Avatar {
	private String path = "";
	Label lbl;
	Composite p;
	
	@Inject
	@Optional
	public void clearAll(@UIEventTopic("Clear") String message) {			
			lbl.setImage(null);		
	}
	@Inject
	@Optional
	public void getEvent(@UIEventTopic("Avatar") String message) {
		path = message;
		//System.out.println(path);

		if (path == "") {
			lbl.setImage(null);	
			
		} else {
			Image img = new Image(p.getDisplay(), path);
			lbl.setImage(img);
		}
	}
	
	
	@PostConstruct
	public void postConstruct(Composite parent) {
		p = parent;
		lbl = new Label(parent, SWT.BORDER);

	}

}