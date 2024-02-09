package me.pandamods.pandalib.client.screen.impl;

import com.mojang.blaze3d.platform.Window;
import me.pandamods.pandalib.client.screen.GUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;

import java.util.Optional;

public interface ElementImpl extends PanelImpl {
	@Override
	default void render(GuiGraphics guiGraphics, double mouseX, double mouseY, float partialTick) {
		if (isVisible()) {
			PanelImpl.super.render(guiGraphics, mouseX, mouseY, partialTick);
		}
	}

	default boolean hasParent() {
		return getParent() != null;
	}
	ElementImpl getParent();
	void setParent(ElementImpl parent);
	GUI getGUI();

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
	default boolean isVisible() {
		Window window = Minecraft.getInstance().getWindow();
		return getMaxX() > 0 && getMaxY() > 0 && getMinX() < window.getGuiScaledWidth() && getMinY() < window.getGuiScaledHeight();
	}

	default boolean isAt(double x, double y) {
		return getMinX() <= x && getMaxX() >= x && getMinY() <= y && getMaxY() >= y;
	}

	boolean isHovered();

	boolean isFocused();

	default boolean isHoveredOrFocused() {
		return isFocused() || isHovered();
	}

	default void setFocused() {
		this.getGUI().setFocused(this);
	}
}
