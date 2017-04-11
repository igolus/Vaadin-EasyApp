package org.vaadin.easyapp.util;

import com.vaadin.ui.Tree;

public class TreeWithIcon {
	private Tree tree;
	private String icon;

	public TreeWithIcon(Tree tree, String icon) {
		super();
		this.tree = tree;
		this.icon = icon;

	}
	public Tree getTree() {
		return tree;
	}
	public String getIcon() {
		return icon;
	}
}
