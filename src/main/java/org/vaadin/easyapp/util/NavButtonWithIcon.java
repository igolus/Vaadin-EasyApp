package org.vaadin.easyapp.util;

import org.vaadin.easyapp.EasyAppMainView;
import org.vaadin.easyapp.util.annotations.ContentView;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button;

public class NavButtonWithIcon extends Button {
	
	private String caption;
	private EasyAppMainView easyAppMainView;
	private ContentView contentView;
	private Navigator navigator;


	public NavButtonWithIcon(ContentView contentView, EasyAppMainView easyAppMainView, Navigator navigator) {
		super(EasyAppMainView.getBundleValue(contentView.viewName()));
		this.contentView = contentView;
		this.navigator = navigator;
		this.easyAppMainView = easyAppMainView;
		setStyleName(easyAppMainView.getNavButtonStyle());
		this.caption = caption;
		addClickListener(this::navCliked);
	}
	
	public void navCliked(ClickEvent event) {
		navigator.navigateTo(contentView.getClass().toString());
		setStyleName(easyAppMainView.getSelectedStyle());
	}

	public ContentView getContentView() {
		return contentView;
	}
	
}
