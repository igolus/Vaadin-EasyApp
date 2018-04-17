package org.vaadin.easyapp;

import java.util.List;
import java.util.ResourceBundle;

import org.vaadin.easyapp.ui.ViewWithToolBar;
import org.vaadin.easyapp.util.ActionContainer;
import org.vaadin.easyapp.util.ActionContainer.InsertPosition;
import org.vaadin.easyapp.util.ActionContainer.Position;
import org.vaadin.easyapp.util.ButtonWithCheck;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;

public class EasyAppBuilder {
	
	public EasyAppBuilder(List<String> packages) {
		EasyAppMainView easyAppMainView = new EasyAppMainView(packages);
		this.mainView = easyAppMainView;
		this.viewWithToolBar = new ViewWithToolBar();
	}
	
	private ViewWithToolBar viewWithToolBar;
	private EasyAppMainView mainView;
	
	public EasyAppMainView getMainView() {
		return mainView;
	}

	public ViewWithToolBar build(UI targetUI) {
		mainView.build(targetUI);
		viewWithToolBar.setActionContainerStlyle(mainView.getTopBarStyle());
		
		
		viewWithToolBar.buildComponents(mainView);
		return viewWithToolBar;
	}
	
	public EasyAppBuilder withActionContainer(ActionContainer actionContainer) {
		if (mainView.isMenuCollapsable()) {
			ButtonWithCheck buttonCollapse = new ButtonWithCheck(
				null,
				null,
				VaadinIcons.MENU,
				null,
				() -> { return true;},
				(event) -> {mainView.switchNavigatorPanelDisplay();}		
			);
			actionContainer.addButtonWithCheck(buttonCollapse, Position.LEFT, InsertPosition.BEFORE);
		}
		
		if (mainView.getApplicationTitle() != null) {
			actionContainer.addComponent(new Label(mainView.getApplicationTitle()), Position.LEFT, InsertPosition.AFTER);
		}
		
		mainView.setActionContainer(actionContainer);
		return this;
	}
	
	public EasyAppBuilder withTopBarIcon(Image topBarIcon) {
		mainView.setTopBarIcon(topBarIcon);
		return this;
	}
	
	public EasyAppBuilder withNavigationIcon(Image topBarIcon) {
		mainView.setNavigationIcon(topBarIcon);
		return this;
	}
	
	public EasyAppBuilder withNavigatorStyle(String style) {
		mainView.setNavigatorStyleName(style);
		return this;
	}
	
	public EasyAppBuilder withNavigationButtonSelectedStyle(String selectedStyle) {
		//mainView.setMainNavigationButtonStyle(mainStyle);
		EasyAppMainView.setSelectedNavigationButtonStyle(selectedStyle);
		return this;
	}

	public EasyAppBuilder withRessourceBundle(ResourceBundle bundle) {
		EasyAppMainView.setResourceBundle(bundle);
		return this;
	}

	public EasyAppBuilder withContentStyle(String contentStyle) {
		EasyAppMainView.setContentStyle(contentStyle);
		return this;
	}
	
	public EasyAppBuilder withNavigatorSplitPosition(int splitPosition) {
		EasyAppMainView.setNavigatorSplitPosition(splitPosition);
		return this;
	}

	public EasyAppBuilder withMenuCollapsable() {
		EasyAppMainView.setMenuCollapsable(true);
		return this;
	}

	public EasyAppBuilder withActionContainerStyle(String style) {
		EasyAppMainView.setActionContainerStyle(style);
		return this;
	}

	public EasyAppBuilder withTopBarStyle(String style) {
		EasyAppMainView.setTopBarStyle(style);
		return this;
	}

	public EasyAppBuilder withApplicationTitle(String title) {
		EasyAppMainView.setApplicationTitle(title);
		return this;
		
	}

	public void withContextualTextLabelStyle(String style) {
		EasyAppMainView.setContextualTextLabelStyle(style);
	}

}
