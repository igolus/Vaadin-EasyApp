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

	public HorizontalLayout getLeftLayout() {
		return leftLayout;
	}

	public HorizontalLayout getRightLayout() {
		return rightLayout;
	}


	private HorizontalLayout rightLayout;
	private String contentStyle;
	private String actionContainerStlyle;


	private GridLayout gridLayout;


	private HorizontalLayout layoutSingleComponent;


	private Component actionConponent;

	public ViewWithToolBar() {
		super();
	}
	
	public void refreshViewToolBar() {
		buildComponents(innerComponent);
	}

	public ViewWithToolBar(EasyAppLayout innerComponent)   {
		this.innerComponent = innerComponent;
		buildComponents(innerComponent);
	}

	public void buildComponents(EasyAppLayout innerComponent) {
		this.innerComponent = innerComponent;
		removeComponent(this.innerComponent);
		if (this.actionConponent != null) {
			removeComponent(this.actionConponent);
		}
		
		
		ActionContainer actionContainer = innerComponent.buildActionContainer();
		innerComponent.setActionContainer(actionContainer);
		if (actionContainer != null) {
			actionConponent = buildActionGrid(actionContainer);
			addComponent(actionConponent);
		}

		if (contentStyle != null) {
			innerComponent.setStyleName(contentStyle);
		}
		
		innerComponent.setSizeFull();
		addComponent(innerComponent);
		setSizeFull();
		setExpandRatio(innerComponent, 1.0f);
		innerComponent.refreshClickable();

		innerComponent.setViewWithToolBarSource(this);
	}

	private Component buildActionGrid(ActionContainer actionContainer) {
		Component singleCompnent = actionContainer.getSingleComponent();

		if (singleCompnent != null) {
			layoutSingleComponent = new HorizontalLayout();
			layoutSingleComponent.addComponent(singleCompnent);
			//addComponent(layoutSingleComponent);
			layoutSingleComponent.setWidth("100%");
			singleCompnent.setWidth("100%");
			return singleCompnent;
		}

		else {	
			List<Component> leftListComponent = actionContainer.getLeftListComponent();
			List<Component> rightListComponent = actionContainer.getRightListComponent();
			if (leftListComponent.size() > 0 || rightListComponent.size() > 0) {
				gridLayout = new GridLayout(2, 1);
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

				//addComponent(gridLayout);
				return gridLayout;
			}
			return new HorizontalLayout();
		}
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
		if (innerComponent != null) {
			innerComponent.enter(event);
		}
	}
}