package me.pandamods.pandalib.client.screen.impl;

public interface ElementImpl extends PanelImpl {
	default boolean hasParent() {
		return getParent() != null;
	}
	ElementImpl getParent();
	void setParent(ElementImpl parent);

	double getX();
	double getY();
	double getWidth();
	double getHeight();

	void setX(double x);
	void setY(double y);
	void setWidth(double width);
	void setHeight(double height);

	default double getMinX() {
		return getX();
	}
	default double getMaxX() {
		return getX() + getWidth();
	}
	default double getMinY() {
		return getY();
	}
	default double getMaxY() {
		return getY() + getHeight();
	}

	default void setMinX(double minX) {
		setX(minX);
	}
	default void setMaxX(double maxX) {
		setWidth(maxX - getMinX());
	}
	default void setMinY(double minY) {
		setY(minY);
	}
	default void setMaxY(double maxY) {
		setHeight(maxY - getMinY());
	}

	default void setPosition(double x, double y) {
		setX(x);
		setY(y);
	}
	default void setSize(double width, double height) {
		setWidth(width);
		setHeight(height);
	}
	default void setBounds(double minX, double minY, double maxX, double maxY) {
		setMinX(minX);
		setMinY(minY);
		setMaxX(maxX);
		setMaxY(maxY);
	}

	boolean isActive();
	void setActive(boolean active);
}
