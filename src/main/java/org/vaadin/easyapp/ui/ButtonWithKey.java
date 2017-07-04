package org.vaadin.easyapp.ui;

import com.vaadin.ui.Button;

public class ButtonWithKey extends Button {
	private String key;

	public ButtonWithKey(String key) {
		super();
		this.key = key;
	}
	

	public String getKey() {
		return key;
	} 
	
}
