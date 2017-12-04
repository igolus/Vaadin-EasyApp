package org.vaadin.easyapp.ui;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.easyapp.util.ButtonDescriptor;
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
	private ButtonWithKey addButton(ButtonDescriptor buttonDescriptor) {
		ButtonWithKey button = new ButtonWithKey(buttonDescriptor.getKey());
		button.setCaption(buttonDescriptor.getName());
		button.setIcon(buttonDescriptor.getImageResource());
		//addComponent(button);
		listButtons.add(button);
		button.setEnabled(false);
		return button;
	}
	
	public void inspect(VisitableView visitableView) {
		removeAllComponents();
		if (visitableView.getButtons() != null) {
			for (ButtonDescriptor buttonDescriptor : visitableView.getButtons()) {
				ButtonWithKey buttonWithKey = addButton(buttonDescriptor);
				buttonWithKey.getListeners(ClickEvent.class).clear();
				ClickListener clickListener = visitableView.getClickListener(buttonWithKey.getKey());
				if (clickListener != null) {
					addComponent(buttonWithKey);
					buttonWithKey.addClickListener(clickListener);
				}
			}
		}
	}
	
	public void checkClickable (VisitableView visitableView) {
		for (ButtonWithKey buttonWithKey : listButtons) {
			if (visitableView.isClickable(buttonWithKey.getKey())) {
				buttonWithKey.setEnabled(true);
			}
			else {
				buttonWithKey.setEnabled(false);
			}
		}
	}
}
