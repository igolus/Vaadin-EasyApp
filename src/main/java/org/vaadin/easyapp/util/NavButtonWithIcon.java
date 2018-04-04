package org.vaadin.easyapp.util;

import org.vaadin.easyapp.EasyAppMainView;
import org.vaadin.easyapp.util.annotations.ContentView;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button;

public class NavButtonWithIcon extends Button {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1797024704407653616L;
	
	private EasyAppMainView easyAppMainView;
	private transient ContentView contentView;
	private Navigator navigator;
	private transient AnnotationScanner scanner;
	private Class<?> targetClass;


	public NavButtonWithIcon(Class<?> targetClass, ContentView contentView, EasyAppMainView easyAppMainView, 
			Navigator navigator, AnnotationScanner scanner) {
		super(EasyAppMainView.getBundleValue(contentView.viewName()));
		this.scanner = scanner;
		this.targetClass = targetClass;
		this.contentView = contentView;
		this.navigator = navigator;
		this.easyAppMainView = easyAppMainView;
		setIcon(EasyAppMainView.getIcon(contentView.icon()));
		setStyleNav();
		addClickListener(this::navCliked);
	}
	
	public void navCliked(ClickEvent event) {
		navigator.navigateTo(targetClass.toString());
		setStyleSelected();
		if (scanner.getSelectedNav() != null) {
			scanner.getSelectedNav().setStyleNav();
		}
		scanner.setSelectedNav(this);
	}

	public ContentView getContentView() {
		return contentView;
	}
	
	public void setStyleNav() {
		setStyleName(easyAppMainView.getMainNavigationButtonStyle());
	}
	
	public void setStyleSelected() {
		setStyleName(EasyAppMainView.getSelectedNavigationButtonStyle());
	}
	
}
