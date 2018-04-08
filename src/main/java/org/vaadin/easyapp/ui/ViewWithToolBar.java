package org.vaadin.easyapp.ui;

import java.util.List;

import org.vaadin.easyapp.EasyAppMainView;
import org.vaadin.easyapp.util.ActionContainer;
import org.vaadin.easyapp.util.EasyAppLayout;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

public class ViewWithToolBar extends VerticalLayout implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 56492767134988853L;


	private EasyAppLayout innerComponent;
	private HorizontalLayout leftLayout;
	private HorizontalLayout rightLayout;
	private String contentStyle;
	private String actionContainerStlyle;

	public ViewWithToolBar() {
		super();
	}

	public ViewWithToolBar(EasyAppLayout innerComponent)   {
		buildComponents(innerComponent);
	}

	public void buildComponents(EasyAppLayout innerComponent) {
		this.innerComponent = innerComponent;

		ActionContainer actionContainer = innerComponent.buildActionContainer();
		innerComponent.setActionContainer(actionContainer);

		if (actionContainer != null) {
			List<Component> leftListComponent = actionContainer.getLeftListComponent();
			List<Component> rightListComponent = actionContainer.getRightListComponent();

			if (leftListComponent.size() > 0 || rightListComponent.size() > 0) {
				GridLayout gridLayout = new GridLayout(2, 1);
				gridLayout.setWidth(100,Unit.PERCENTAGE);

				leftLayout = new HorizontalLayout();
				for (Component component : leftListComponent) {
					leftLayout.addComponent(component);
				}

				rightLayout = new HorizontalLayout();
				for (Component component : rightListComponent) {
					rightLayout.addComponent(component);
				}
				gridLayout.addComponent(leftLayout, 0, 0);
				gridLayout.addComponent(rightLayout, 1, 0);
				if (actionContainerStlyle != null) {
					gridLayout.setStyleName(actionContainerStlyle);
				}
				
			    gridLayout.setComponentAlignment(leftLayout, Alignment.MIDDLE_LEFT);
				gridLayout.setComponentAlignment(rightLayout, Alignment.MIDDLE_RIGHT);
				
				addComponent(gridLayout);
			}
		}

		if (contentStyle != null) {
			innerComponent.setStyleName(contentStyle);
		}
		innerComponent.setSizeFull();
		addComponent(innerComponent);
		setSizeFull();
		setExpandRatio(innerComponent, 1.0f);

	}

	public String getActionContainerStlyle() {
		return actionContainerStlyle;
	}

	public void setActionContainerStlyle(String actionContainerStlyle) {
		this.actionContainerStlyle = actionContainerStlyle;
	}

	

//	private void fillButtons(ActionContainer actionContainer, List<ButtonWithCheck> listButtonWithCheck) {
//		if (listButtonWithCheck != null && !listButtonWithCheck.isEmpty()) {
//			for (ButtonWithCheck buttonWithCheck : listButtonWithCheck) {
//				buttonWithCheck.getButton().setStyleName(actionContainer.getStyleName());
//				buttonWithCheck.getButton().setHeight("100%");
//				leftLayout.addComponent(buttonWithCheck.getButton());
//			}
//		}
//	}
//
//	private void fillImages(List<Image> listImages) {
//		if (listImages != null && !listImages.isEmpty()) {
//			for (Image image : listImages) {
//				leftLayout.addComponent(image);
//			}
//		}
//	}

	public EasyAppLayout getInnerComponent() {
		return innerComponent;
	}

//	public void setToolBarStyle(String style) {
//		leftLayout.setStyleName(style);
//		searchLayout.setStyleName(style);
//	}

	public void setContentStyle(String contentStyle) {
		this.contentStyle = contentStyle;
	}
	

	@Override
	public void enter(ViewChangeEvent event) {
		innerComponent.enter(event);
	}
}