package org.vaadin.easyapp.util;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.reflections.Reflections;
import org.vaadin.easyapp.ui.DefaultView;
import org.vaadin.easyapp.util.annotations.ContentView;
import org.vaadin.easyapp.util.annotations.RootView;

import com.vaadin.data.Item;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.Resource;
import com.vaadin.ui.Tree;

/**
 * Class used to look in package to scan to build the navigation
 * @author Guillaume Rousseau
 *
 */
public class AnnotationScanner {

	private Map<String, Item> customTreeItemByClassName = new LinkedHashMap<>();

	public AnnotationScanner(Navigator navigator, List<String> packageToScan) throws InstantiationException, IllegalAccessException {
		super();
		init(navigator, packageToScan);
	}

	/**
	 * To protect bad instanciate
	 */
	private AnnotationScanner() {
	}

	private Map<String, TreeWithIcon> treeMap = new LinkedHashMap<>();
	private Map<String, View> viewMap = new LinkedHashMap();



	public Map<String, TreeWithIcon> getTreeMap() {
		return treeMap;
	}


	public Map<String, View> getViewMap() {
		return viewMap;
	}

	public void init(Navigator navigator, List<String> packagesToScan)
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
			//build the treeMap
			for (RootView rootView : rootViews) {
				if (treeMap.get(rootView.viewName()) == null) {
					Tree tree = new Tree();
					TreeWithIcon treeWithIcon = new TreeWithIcon(tree, rootView.icon());
					treeMap.put(rootView.viewName(), treeWithIcon);
					treeByClassName.put(classNameByRootView.get(rootView), treeWithIcon);
					tree.addItemClickListener(new ItemClickListener() {
						@Override
						public void itemClick(ItemClickEvent event) {
							//navigate the the selected view
							navigator.navigateTo(((CustomTreeItem)event.getItemId()).getTargetClass().toString());
						}
					});
				}
			}


			List<ContentViewWithTargetClass> ContentViewWithTargetClasses = new ArrayList<>();

			Set<Class<?>> annotatedContentView = reflections.getTypesAnnotatedWith(ContentView.class);
			//first iteration to order items
			for (Class<?> classTarget : annotatedContentView) {
				//inteanciate the view
				Object view = classTarget.newInstance();
				if (view instanceof View) {
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

			Map<String, CustomTreeItem> customTreeItemByClassName = new HashMap<>();

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
				if (tree != null) {

					CustomTreeItem customTreeItem = new CustomTreeItem(contentView.viewName(), targetClass);
					customTreeItemByClassName.put(targetClass.toString(), customTreeItem);

					Item treeItem = tree.getTree().addItem(customTreeItem);
					tree.getTree().setChildrenAllowed(customTreeItem, false);

					Resource icon = org.vaadin.easyapp.EasyAppMainView.getIcon(contentView.icon());
					if (icon != null) {

						tree.getTree().addContainerProperty("icon", Resource.class, null);
						tree.getTree().addContainerProperty("caption", String.class, null);

						tree.getTree().setItemIconPropertyId("icon");
						tree.getTree().setItemCaptionPropertyId("caption");

						treeItem.getItemProperty("icon").setValue(icon);
						treeItem.getItemProperty("caption").setValue(contentView.viewName());
					}

					if (customTreeItemByClassName.get(contentView.componentParent().toString()) != null) {
						CustomTreeItem parentCustomTreeItem = customTreeItemByClassName.get(contentView.componentParent().toString());
						tree.getTree().setChildrenAllowed(parentCustomTreeItem, true);
						tree.getTree().setParent(customTreeItem, parentCustomTreeItem);
					}
					viewMap.put(targetClass.toString(), (View) view);
				}
			}

			if (!hasHomeView) {
				navigator.addView("" , new DefaultView());
				hasHomeView = true;
			}

		}
	}
}
