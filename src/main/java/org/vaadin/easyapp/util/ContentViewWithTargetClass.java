package org.vaadin.easyapp.util;

import org.vaadin.easyapp.util.annotations.ContentView;

public class ContentViewWithTargetClass {
	private Class<?> targetClass;
	private ContentView contentView;
	public ContentViewWithTargetClass(Class<?> targetClass, ContentView contentView) {
		super();
		this.targetClass = targetClass;
		this.contentView = contentView;
	}
	public Class<?> getTargetClass() {
		return targetClass;
	}
	public ContentView getContentView() {
		return contentView;
	}
	
	
}
