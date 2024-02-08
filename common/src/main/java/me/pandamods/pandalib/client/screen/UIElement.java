package me.pandamods.pandalib.client.screen;

import com.mojang.blaze3d.platform.Window;
import me.pandamods.pandalib.client.screen.impl.ElementImpl;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;

import java.util.ArrayList;
import java.util.List;

public abstract class UIElement implements ElementImpl {
	private final List<ElementImpl> elements = new ArrayList<>();

	public final Minecraft minecraft;
	public final Font font;
	public final Window window;
	private ElementImpl parent;
	private double x;
	private double y;
	private double width;
	private double height;
	private boolean active = true;
	private boolean hovered = false;

	protected UIElement(ElementImpl parent) {
		this.parent = parent;
		this.minecraft = Minecraft.getInstance();
		this.font = this.minecraft.font;
		this.window = this.minecraft.getWindow();
	}

	@Override
	public void render(GuiGraphics guiGraphics, double mouseX, double mouseY, float partialTick) {
		ElementImpl.super.render(guiGraphics, mouseX, mouseY, partialTick);
		hovered = this.isAt(mouseX, mouseY) && getElementAt(mouseX, mouseY).isEmpty();
	}

	@Override
	public ElementImpl getParent() {
		return parent;
	}
	@Override
	public void setParent(ElementImpl parent) {
		this.parent = parent;
	}

	@Override
	public List<ElementImpl> elements() {
		return elements;
	}

	@Override
	public double getX() {
		if (hasParent()) return getParent().getX() + x;
		return x;
	}

	@Override
	public double getY() {
		if (hasParent()) return getParent().getY() + y;
		return y;
	}

	@Override
	public double getWidth() {
		return width;
	}

	@Override
	public double getHeight() {
		return height;
	}

	@Override
	public void setX(double x) {
		this.x = x;
	}

	@Override
	public void setY(double y) {
		this.y = y;
	}

	@Override
	public void setWidth(double width) {
		this.width = width;
	}

	@Override
	public void setHeight(double height) {
		this.height = height;
	}

	@Override
	public boolean isActive() {
		return active;
	}

	@Override
	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isHovered() {
		return hovered;
	}
}
