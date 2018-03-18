package org.vaadin.easyapp.util;

import com.vaadin.ui.Button;

public class ButtonWithCheck {
	private Button button;
	private ButtonClickable buttonClickable;
	
	public ButtonWithCheck(Button button, ButtonClickable buttonClickable) {
		super();
		this.button = button;
		this.buttonClickable = buttonClickable;
	}

	public Button getButton() {
		return button;
	}

	public ButtonClickable getButtonClickable() {
		return buttonClickable;
	}
	
}
