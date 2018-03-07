package org.vaadin.easyapp.util;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import org.reflections.Reflections;
import org.vaadin.easyapp.event.NavigationTrigger;
import org.vaadin.easyapp.ui.DefaultView;
import org.vaadin.easyapp.util.annotations.ContentView;
import org.vaadin.easyapp.util.annotations.RootView;

import com.vaadin.ui.Tree.ItemClick;
import com.vaadin.ui.Tree.ItemClickListener;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.IconGenerator;
import com.vaadin.ui.ItemCaptionGenerator;
import com.vaadin.data.TreeData;
import com.vaadin.data.provider.TreeDataProvider;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.Resource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Tree;

/**
 * Class used to look in package to scan to build the navigation
 * @author Guillaume Rousseau
 *
 */
public class AnnotationScanner {

	private Map<String, CustomTreeItem> customTreeItemByClassName = new LinkedHashMap<>();
	private Navigator navigator;
	private ArrayList<NavigationTrigger> navigationTriggers;

	public AnnotationScanner(Navigator navigator, List<String> packageToScan, ArrayList<NavigationTrigger> navigationTriggers, String buttonLinkStyle) 
			throws InstantiationException, IllegalAccessException {
		super();
		this.navigationTriggers = navigationTriggers;
		this.navigator = navigator;
		this.buttonLinkStyle = buttonLinkStyle;
		init(packageToScan);
	}

	private Map<String, TreeWithIcon> treeMap = new LinkedHashMap<>();
	private Map<String, View> viewMap = new LinkedHashMap();
	private String buttonLinkStyle;

	public Map<String, TreeWithIcon> getTreeMap() {
		return treeMap;
	}


	public Map<String, View> getViewMap() {
		return viewMap;
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
				Annotation[] annotations = classTarget.getDeclaredAnnotations();
				for (Annotation annotation : annotations) {
					if (annotation instanceof RootView) {
						RootView rootView = (RootView) annotation;
						rootViews.add(rootView);
						classNameByRootView.put(rootView, classTarget.toString());
					}
				}
			}

			//sort it 
			rootViews.sort((r1, r2) -> r1.sortingOrder() - r2.sortingOrder());

			Map<String, TreeWithIcon> treeByClassName = new LinkedHashMap<>();
			Map<TreeWithIcon, TreeDataProvider<CustomTreeItem>> treeDataByTree = new LinkedHashMap<>();
			
			Map<String, String> rootViewNameByClassName = new LinkedHashMap<>();
			
			//build the treeMap
			
			//TreeData<CustomTreeItem> treeData = new TreeData<>();
			for (RootView rootView : rootViews) {
				if (treeMap.get(rootView.viewName()) == null) {
					Tree<CustomTreeItem> tree = new Tree<>();
					TreeWithIcon treeWithIcon = new TreeWithIcon(tree, rootView.icon());
					treeMap.put(rootView.viewName(), treeWithIcon);
					treeByClassName.put(classNameByRootView.get(rootView), treeWithIcon);
					
					TreeData<CustomTreeItem> treeData = new TreeData<>();
					TreeDataProvider<CustomTreeItem> dataProvider = new TreeDataProvider<>(treeData);
					treeWithIcon.getTree().setDataProvider(dataProvider);
					treeDataByTree.put(treeWithIcon, dataProvider);
					
					rootViewNameByClassName.put(classNameByRootView.get(rootView), rootView.viewName());
					tree.addItemClickListener(new ItemClickListener<CustomTreeItem>() {

						@Override
						public void itemClick(ItemClick<CustomTreeItem> event) {
							Class<?> targetClass = event.getItem().getTargetClass();
							navigator.navigateTo( targetClass.toString());
							updateBreadCrumbLinksList();
							for (NavigationTrigger navigationTrigger : navigationTriggers) {
								navigationTrigger.enter(targetClass);
							}
							
						}
					});
				}
			}


			List<ContentViewWithTargetClass> ContentViewWithTargetClasses = new ArrayList<>();

			Set<Class<?>> annotatedContentView = reflections.getTypesAnnotatedWith(ContentView.class);
			//first iteration to order items
			for (Class<?> classTarget : annotatedContentView) {
				//inteanciate the view
				//Object view = classTarget.newInstance();
				if (View.class.isAssignableFrom(classTarget)) {
					Annotation[] annotations = classTarget.getDeclaredAnnotations();
					for (Annotation annotation : annotations) {
						if (annotation instanceof ContentView) {
							ContentView contentView = (ContentView) annotation;
							ContentViewWithTargetClasses.add(new ContentViewWithTargetClass( classTarget, contentView));
						}
					}
				}
			}

			//sort it
			ContentViewWithTargetClasses.sort((p1, p2) -> p1.getContentView().sortingOrder() - p2.getContentView().sortingOrder());

			customTreeItemByClassName = new HashMap<>();

			boolean hasHomeView = false;
			
			
			
			//create the view with order
			for (ContentViewWithTargetClass contentViewWithTargetClass : ContentViewWithTargetClasses) {
				ContentView contentView = contentViewWithTargetClass.getContentView();
				Class<?> targetClass = contentViewWithTargetClass.getTargetClass();

				Object view = targetClass.newInstance();

				if (contentView.homeView()) {
					navigator.addView("" , (View) view);
					hasHomeView = true;
				}

				navigator.addView(targetClass.toString(), (View) view);
				TreeWithIcon tree = treeByClassName.get(contentView.rootViewParent().toString());
				String rootViewName = rootViewNameByClassName.get(contentView.rootViewParent().toString());
				if (tree != null) {
										
					String  viewName = contentView.viewName();
					if (!contentView.bundle().equals(ContentView.NOT_SET) && 
							ResourceBundle.getBundle(contentView.bundle()) != null &&
							ResourceBundle.getBundle(contentView.bundle()).containsKey(viewName)) {
						viewName = ResourceBundle.getBundle(contentView.bundle()).getString(viewName);
					}
					
					CustomTreeItem customTreeItem = new CustomTreeItem(viewName, targetClass, rootViewName, 
							contentView.rootViewParent().toString());
					customTreeItemByClassName.put(targetClass.toString(), customTreeItem);
					
					TreeDataProvider<CustomTreeItem> treeDataProvider = treeDataByTree.get(tree);
					CustomTreeItem parentCustomTreeItem = customTreeItemByClassName.get(contentView.componentParent().toString());
					if (customTreeItemByClassName.get(contentView.componentParent().toString()) != null) {
						treeDataProvider.getTreeData().addItem(customTreeItemByClassName.get(contentView.componentParent().toString()), customTreeItem);
					}
					else {
						treeDataProvider.getTreeData().addItem(null, customTreeItem);
					}
					treeDataProvider.refreshAll();
					
//					Item treeItem = tree.getTree().addItem(customTreeItem);
//					tree.getTree().setChildrenAllowed(customTreeItem, false);

					Resource icon = org.vaadin.easyapp.EasyAppMainView.getIcon(contentView.icon());
					if (icon != null) {
						tree.getTree().setItemIconGenerator(new IconGenerator<CustomTreeItem>() {

							@Override
							public Resource apply(CustomTreeItem item) {
								// TODO Auto-generated method stub
								return icon;
							}
							
						});
						
						
						tree.getTree().setItemCaptionGenerator(new ItemCaptionGenerator<CustomTreeItem>() {

							@Override
							public String apply(CustomTreeItem item) {
								// TODO Auto-generated method stub
								return contentView.viewName();
							}
						});
						
//						tree.getTree().addContainerProperty("icon", Resource.class, null);
//						tree.getTree().addContainerProperty("caption", String.class, null);
//
//						tree.getTree().setItemIconPropertyId("icon");
//						tree.getTree().setItemCaptionPropertyId("caption");
//
//						treeItem.getItemProperty("icon").setValue(icon);
//						treeItem.getItemProperty("caption").setValue(contentView.viewName());
					}

//					if (customTreeItemByClassName.get(contentView.componentParent().toString()) != null) {
//						CustomTreeItem parentCustomTreeItem = customTreeItemByClassName.get(contentView.componentParent().toString());
//						treeData.addItem(parent, item)
//						tree.getTree().setChildrenAllowed(parentCustomTreeItem, true);
//						tree.getTree().setParent(customTreeItem, parentCustomTreeItem);
//						customTreeItem.setParent(parentCustomTreeItem);
//					}
					viewMap.put(targetClass.toString(), (View) view);
				}
			}

			if (!hasHomeView) {
				navigator.addView("" , new DefaultView());
				hasHomeView = true;
			}

		}
	}
	
	/**
	 * Navigate to a view
	 * @param clazz
	 */
	public void navigateTo(Class<?> clazz) {
		navigator.navigateTo(clazz.toString());
	}
	
	/**
	 * Build the list of links
	 * @param style
	 * @return
	 */
	public void updateBreadCrumbLinksList() {
		breadCrumbLinkList.clear();
		View currentView = navigator.getCurrentView();
		CustomTreeItem pathCustomTreeItem = customTreeItemByClassName.get(currentView.getClass().toString());
		
		breadCrumbLinkList.add(buildLinkButton(pathCustomTreeItem));
		
		while (pathCustomTreeItem.getParent() != null) {
			pathCustomTreeItem = pathCustomTreeItem.getParent();
			breadCrumbLinkList.add(buildLinkButton(pathCustomTreeItem));
		}
		
		Label rootLabel = new Label(pathCustomTreeItem.getRootViewName());
		breadCrumbLinkList.add(rootLabel);
		
		//invert the order
		Collections.reverse(breadCrumbLinkList);
	}


	private Button buildLinkButton(CustomTreeItem customTreeItem) {
		Button link = new Button(customTreeItem.toString());
		if (buttonLinkStyle != null) {
			link.setStyleName(buttonLinkStyle);
		}
		link.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				treeMap.get(customTreeItem.getRootViewName()).getTree().select(customTreeItem);
			}
		});
		return link;
	}
	
	
	
	private List<Component> breadCrumbLinkList = new ArrayList<>();

	public List<Component> getBreadCrumbLinkList() {
		return breadCrumbLinkList;
	} 
	
	
}
