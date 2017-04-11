package org.vaadin.easyapp.util.annotations;

import java.lang.annotation.Retention;

import org.vaadin.easyapp.EasyAppMainView;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
public @interface ContentView {

	public static final String NOT_SET = "NOT_SET";
	//public static final Class<?>  = ContentView.
	String viewName();
	String icon() default NOT_SET;
	int sortingOrder() default 0;
	boolean homeView() default false;
	Class<?> rootViewParent() default EasyAppMainView.class;
	//default means sot set
	Class<?> componentParent() default EasyAppMainView.class;
}



