package org.vaadin.easyapp.util;

public class CustomTreeItem {
	private String label;
	private Class<?> targetClass;
	private CustomTreeItem parent;
	private String rootViewName = "Undefined";
	private String rootClassName;
	

	public String getRootClassName() {
		return rootClassName;
	}

	public String getRootViewName() {
		return rootViewName;
	}

	public CustomTreeItem getParent() {
		return parent;
	}

	public void setParent(CustomTreeItem parent) {
		this.parent = parent;
	}

	public CustomTreeItem(String label, Class<?> targetClass, String rootViewName, String rootClassName) {
		super();
		this.label = label;
		this.targetClass = targetClass;
		this.rootViewName = rootViewName;
		this.rootClassName = rootClassName;
		
	}

	public String getLabel() {
		return label;
	}

	public Class<?> getTargetClass() {
		return targetClass;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result + ((targetClass == null) ? 0 : targetClass.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomTreeItem other = (CustomTreeItem) obj;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		if (targetClass == null) {
			if (other.targetClass != null)
				return false;
		} else if (!targetClass.equals(other.targetClass))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return label;
	}
	
	
}
