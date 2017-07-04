package org.vaadin.easyapp.ui;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.easyapp.util.VisitableView;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.GridLayout;

public class ToolBar extends HorizontalLayout {

	List<ButtonWithKey> listButtons = new ArrayList();
	private GridLayout gridBar;
	
	public ToolBar(GridLayout gridBar) {
		super();
		this.gridBar = gridBar;
		setDefaultComponentAlignment(Alignment.BOTTOM_LEFT);
	}
	
	//add a button
	public void addButton(String name, String tooltip, Resource imageResource, String key) {
		ButtonWithKey button = new ButtonWithKey(key);
		button.setCaption(name);
		button.setIcon(imageResource);
		addComponent(button);
		listButtons.add(button);
	}
	
	public void inspect(VisitableView visitableView) {
		removeAllComponents();
		for (ButtonWithKey buttonWithKey : listButtons) {
			buttonWithKey.getListeners(ClickEvent.class).clear();
			ClickListener clickListener = visitableView.getClickListener(buttonWithKey.getKey());
			if (clickListener != null) {
				addComponent(buttonWithKey);
				//gridBar.setComponentAlignment(this, Alignment.BOTTOM_LEFT);
				//setComponentAlignment(buttonWithKey, Alignment.BOTTOM_LEFT);
				buttonWithKey.addClickListener(clickListener);
				
			}
		}
	}
}
