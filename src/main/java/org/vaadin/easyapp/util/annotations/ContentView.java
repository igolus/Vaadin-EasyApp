package org.vaadin.easyapp.util.annotations;

import java.lang.annotation.Retention;

import org.vaadin.easyapp.EasyAppMainView;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
public @interface ContentView {
	/**
	 * Used to tell that the icon is not set
	 */
	public static final String NOT_SET = "NOT_SET";
	
	/**
	 * define the how the view should be displayed
	 */
	String viewName();
	
	/**
	 * icon to display in the navigation panel could FontAwesom can be used 
	 */
	String icon() default NOT_SET;
	
	/**
	 * int to define the sorting of the elements
	 */
	int sortingOrder() default 0;
	/**
	 * set to true if the view should be displayed as startup
	 */
	boolean homeView() default false;
	/**
	 * Root view where this view should be displayed
	 */
	Class<?> rootViewParent() default EasyAppMainView.class;

	/**
	 * Parent view 
	 */
	Class<?> componentParent() default EasyAppMainView.class;
}



