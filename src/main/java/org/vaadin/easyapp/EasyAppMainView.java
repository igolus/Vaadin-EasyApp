package org.vaadin.easyapp;

import java.lang.reflect.Field;
import java.util.Map;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.vaadin.easyapp.util.AnnotationScanner;
import org.vaadin.easyapp.util.TreeWithIcon;
import org.vaadin.easyapp.util.annotations.RootView;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Image;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;


/**
 * Main class to embed navigation
 * @author igolus
 *
 */
public class EasyAppMainView extends VerticalSplitPanel  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private static Logger logger = Logger.getLogger(EasyAppMainView.class);
	
	private Navigator navigator;

	private Accordion accordion;

	private VerticalLayout navigationLayout;

	private HorizontalSplitPanel downSplitPanel;


	private Component topBar;

	/**
	 * return top bar as a component
	 * @return
	 */
	public Component getTopBar() {
		return topBar;
	}

	public ComponentContainer getMainArea() {
		return mainArea;
	}

	public ComponentContainer getNavigationPanel() {
		return navigationPanel;
	}

	private ComponentContainer mainArea;


	private ComponentContainer navigationPanel;


	private AnnotationScanner scanner;


	private String packageToScan;
	
	
	public Navigator getNavigator() {
		return navigator;
	}
	
	/**
	 * Constructor to use
	 * @param packageToScan the package where your view are stored
	 */
	public EasyAppMainView(String packageToScan) {
		super();
		BasicConfigurator.configure();
		this.packageToScan = packageToScan;
		downSplitPanel = new HorizontalSplitPanel();
		topBar = buildTopBar();
		mainArea = buildMainArea();
		navigationPanel = buildNavigation(mainArea);
		
		downSplitPanel.setSecondComponent(mainArea);
		downSplitPanel.setFirstComponent(navigationPanel);
		downSplitPanel.setSplitPosition(300, Unit.PIXELS);
		downSplitPanel.setSizeFull();
		downSplitPanel.setLocked(true);
		
		setSecondComponent(downSplitPanel);
		setFirstComponent(topBar);
		setSplitPosition(55, Unit.PIXELS);

		
		setSizeFull();
		setLocked(true);
	}
	
	/**
	 * Get the HorizontalSplitPanel usefull if you want to call setSplitPosition
	 * @return
	 */
	public HorizontalSplitPanel getDownSplitPanel() {
		return downSplitPanel;
	}

	private void scanPackage() {
		try {
			scanner = new AnnotationScanner(getNavigator(), packageToScan);
		} catch (InstantiationException | IllegalAccessException e) {
			logger.error("Unable to create the AnnotationScanner");
		}
	}

	private Component buildTopBar() {
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		Image image = getTopBarImage();
		horizontalLayout.addComponent(image);
		horizontalLayout.setSizeFull();
		horizontalLayout.setStyleName("topBannerBackGround");
		return horizontalLayout;
		
	}

	private ComponentContainer buildMainArea() {
		
		VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setSizeFull();
        verticalLayout.setStyleName("mainBackGround");
        return verticalLayout;
	}

	private ComponentContainer buildNavigation(ComponentContainer target)  {
		VerticalLayout parentLayout = new VerticalLayout();
		navigationLayout = new VerticalLayout();
		navigationLayout.setImmediate(true);
        setFirstComponent(navigationLayout);
        ViewDisplay viewDisplay = new Navigator.ComponentContainerViewDisplay(target);
		navigator = new Navigator(UI.getCurrent(), viewDisplay);
		
        parentLayout.setSizeFull();
        parentLayout.addComponent(navigationLayout);
        buildAccordion();
        return parentLayout;
	}

	public void buildAccordion() {
		scanPackage();
		Map<String, TreeWithIcon> treeByAccordeonItem = scanner.getTreeMap();
		accordion = new Accordion();
		treeByAccordeonItem.forEach((name, treeWithIcon)-> {
			Resource icon = EasyAppMainView.getIcon(treeWithIcon.getIcon());
			accordion.addTab(treeWithIcon.getTree(), name, icon);
		});
		
        accordion.setSizeFull();
        navigationLayout.addComponent(accordion);
	}
	
	/**
	 * get the resource based on definitions
	 * @param iconName
	 * @param fontAwasomeField
	 * @return
	 * @throws IllegalAccessException
	 */
	public static Resource getIcon(String iconName) {
		Resource icon = null;
		Field fontAwasomeField;
		try {
			fontAwasomeField = FontAwesome.class.getDeclaredField(iconName);
			if (fontAwasomeField != null && !iconName.equals(RootView.NOT_SET)) {
				icon = (Resource) fontAwasomeField.get(null);
			}
		
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			 if (!iconName.equals(RootView.NOT_SET)){
				icon = new ThemeResource(iconName);
			 }
			 else {
				 logger.error("Unable to find the icon in font awesome, Check the Fiels Name", e);
			 }
			
		}
		
		return icon;
	}
	
	/**
	 * Override this to display icon in the top bar
	 * @return
	 */
	protected Image getTopBarImage() {
		return null;
	}
}
