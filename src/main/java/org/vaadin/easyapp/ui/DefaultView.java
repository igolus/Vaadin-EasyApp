package org.vaadin.easyapp.ui;


import org.vaadin.easyapp.util.annotations.ContentView;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * View used when nothing was found by the annotation scanner
 * @author igolus
 *
 */
@ContentView (sortingOrder=0, homeView=true, viewName = "Dashboard")
public class DefaultView extends VerticalLayout implements View  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DefaultView() {
		addComponent(new Label("No default view defined, create at least one view tagged like "
				+ "@ContentView (sortingOrder=0, homeView=true, viewName = \"Dashboard\","
				+ " accordeonParent = ContentView.NOT_SET)"));
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
	}

}
