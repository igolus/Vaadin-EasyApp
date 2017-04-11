package org.vaadin.easyapp.util.annotations;

import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
public @interface RootView {
	public static final String NOT_SET = "NOT_SET";
	String viewName();
	String icon() default NOT_SET;
	int sortingOrder() default 0;
}
