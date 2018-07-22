package org.vaadin.easyapp.util;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.log4j.Logger;
import org.reflections.Reflections;
import org.vaadin.easyapp.EasyAppMainView;
import org.vaadin.easyapp.ui.DefaultView;
import org.vaadin.easyapp.ui.ViewWithToolBar;
import org.vaadin.easyapp.util.annotations.ContentView;
import org.vaadin.easyapp.util.annotations.RootView;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.ui.Component;

/**
 * Class used to look in package to scan to build the navigation
 * @author Guillaume Rousseau
 *
 */
public class AnnotationScanner {

	private Navigator navigator;
	private EasyAppMainView easyAppMainView;
	private NavButtonWithIcon selectedNav = null;
	private static Logger logger = Logger.getLogger(AnnotationScanner.class);

	public AnnotationScanner(Navigator navigator, List<String> packageToScan, EasyAppMainView easyAppMainView) 
			throws InstantiationException, IllegalAccessException {
		super();
		this.navigator = navigator;
		this.easyAppMainView = easyAppMainView;
		init(packageToScan);
	}

	private Map<RootView, List<NavButtonWithIcon>> navButtonMap = new LinkedHashMap<>();

	public Map<RootView, List<NavButtonWithIcon>> getNavButtonMap() {
		return navButtonMap;
	}

	private Map<String, NavButtonWithIcon> navButtonByViewName = new HashMap<>();

	private Map<String, Component> viewMap = new LinkedHashMap<>();

	public Map<String, Component> getViewMap() {
		return viewMap;
	}

	public NavButtonWithIcon getSelectedNav() {
		return selectedNav;
	}

	public void setSelectedNav(NavButtonWithIcon selectedNav) {
		this.selectedNav = selectedNav;
	}

	public RootView getRootViewFromClass (Class<?> classTarget) 
	{
		Annotation[] annotations = classTarget.getDeclaredAnnotations();
		for (Annotation annotation : annotations) {
			if (annotation instanceof RootView) {
				return (RootView) annotation;
			}
		}
		return null;
	}


	public void init(List<String> packagesToScan)
			throws InstantiationException, IllegalAccessException {
		for (String packageToScan : packagesToScan) {
			Reflections reflections = new Reflections(packageToScan);
			List<RootView> rootViews = new ArrayList<>();

			Map<RootView, String> classNameByRootView = new LinkedHashMap<>();
			//first iteration to gather root Views
			Set<Class<?>> annotatedRootView = reflections.getTypesAnnotatedWith(RootView.class);
			for (Class<?> classTarget : annotatedRootView) {
				RootView rootView = getRootViewFromClass(classTarget);
				if (rootView != null) {
					rootViews.add(rootView);
					classNameByRootView.put(rootView, classTarget.toString());
				}
			}

			//sort it 
			rootViews.sort((r1, r2) -> r1.sortingOrder() - r2.sortingOrder());


			for (RootView rootView : rootViews) {
				List<NavButtonWithIcon> listnavButtonMap = new LinkedList<>();
				navButtonMap.put(rootView, listnavButtonMap);
			}

			boolean hasHomeViewDefined = false;

			Set<Class<?>> annotatedContentView = reflections.getTypesAnnotatedWith(ContentView.class);
			
			View viewToAdd = null;
			
			//first iteration to order items
			for (Class<?> classTarget : annotatedContentView) {
				if (EasyAppLayout.class.isAssignableFrom(classTarget) || View.class.isAssignableFrom(classTarget)) {
					Annotation[] annotations = classTarget.getDeclaredAnnotations();
					for (Annotation annotation : annotations) {
						if (annotation instanceof ContentView) {
							ContentView contentView = (ContentView) annotation;
							Class<?> rootViewParent = contentView.rootViewParent();
							RootView rootView = getRootViewFromClass(rootViewParent);

							List<NavButtonWithIcon> listNavButton = navButtonMap.get(rootView);

							boolean emptyConstructorExist = false;

							try {
								classTarget.getConstructor(null);
								emptyConstructorExist = true;
							} catch (NoSuchMethodException | SecurityException e1) {
								logger.warn("Please define at least one empty constructor for you view: " + classTarget.toString());
							}

							if (listNavButton!=null && emptyConstructorExist) 
							{
								Object view = classTarget.newInstance();
								viewMap.put(view.getClass().toString(), (Component) view);
								if (EasyAppLayout.class.isAssignableFrom(classTarget)) {
									ViewWithToolBar viewWithToolBar = new ViewWithToolBar();
									viewWithToolBar.setActionContainerStlyle(EasyAppMainView.getActionContainerStyle());
									viewWithToolBar.setContentStyle(EasyAppMainView.getContentStyle());
									viewWithToolBar.buildComponents((EasyAppLayout) view);
									viewToAdd = viewWithToolBar;
								}
								else if(View.class.isAssignableFrom(classTarget)) {
									viewToAdd = (View) view;
									
								}
								NavButtonWithIcon navButton = new NavButtonWithIcon(classTarget, contentView,  easyAppMainView, navigator, this);
								navButtonByViewName.put(classTarget.toString(), navButton);
								listNavButton.add(navButton);

								navigator.addView(classTarget.toString(), viewToAdd);
								
								if (contentView.homeView()) {
									navigator.addView("" , viewToAdd);
									if (hasHomeViewDefined && contentView.homeView()) {
										logger.warn("You did defined several home view ! " + classTarget.toString() + " will be used as default view ");
									}
									hasHomeViewDefined = true;
								}
							}
						}
					}
				}
				else {
					logger.warn("Your component should inherit from View or EasyAppLayout: " + classTarget.toString());
				}
			}
			
			//if no view defined; define the last one
			if (!hasHomeViewDefined && viewToAdd!= null) {
				navigator.addView("" , viewToAdd);
			}

			for (Map.Entry<RootView, List<NavButtonWithIcon>> entry : navButtonMap.entrySet())
			{
				//sort nav button 
				entry.getValue().sort((p1, p2) -> p1.getContentView().sortingOrder() - p2.getContentView().sortingOrder());
			}

		}
	}


	public static String getBundleValue(String bundleName, String bundleValue) {
		if (bundleValue != null && bundleName != null && !bundleName.equals(ContentView.NOT_SET) && 
				ResourceBundle.getBundle(bundleName) != null &&
				ResourceBundle.getBundle(bundleName).containsKey(bundleValue)) {
			return ResourceBundle.getBundle(bundleName).getString(bundleValue);
		}
		return bundleValue;
	}

	/**
	 * Navigate to a view
	 * @param clazz
	 */
	public void navigateTo(Class<?> clazz) {
		if (navButtonByViewName.containsKey(clazz.toString())) {
			NavButtonWithIcon selectedNavButtonWithIcon = navButtonByViewName.get(clazz.toString());
			selectedNavButtonWithIcon.setStyleSelected();
			NavButtonWithIcon oldSelectedNav = getSelectedNav();
			if (oldSelectedNav != null) {
				oldSelectedNav.setStyleNav();
			}
			selectedNavButtonWithIcon.setStyleSelected();
		}
		navigator.navigateTo(clazz.toString());
	}

}
