package org.vaadin.easyapp.util;

import com.vaadin.server.Resource;
import com.vaadin.ui.Button;

public class ButtonWithCheck extends Button {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5788189783928567987L;
	private ButtonClickable buttonClickable;
	
	public ButtonWithCheck(String bundleName, String bundleValue, Resource icon, String description, 
			ButtonClickable buttonClickable, ClickListener listener) {
		super(AnnotationScanner.getBundleValue(bundleName, bundleValue));
		setDescription(AnnotationScanner.getBundleValue(bundleName, description));
		setIcon(icon);
		
		this.buttonClickable = buttonClickable;
		if (listener != null) {
			addClickListener( (event) -> {listener.buttonClick(event);});
		}
	}
	

	public ButtonClickable getButtonClickable() {
		return buttonClickable;
	}
	
}
