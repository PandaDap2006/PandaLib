package me.pandamods.pandalib.client.screen;

import com.mojang.blaze3d.platform.Window;
import me.pandamods.pandalib.client.screen.impl.ElementImpl;
import me.pandamods.pandalib.client.screen.impl.PanelImpl;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public abstract class GUI implements PanelImpl {
	private final GUIScreen screen;
	private final List<ElementImpl> elements = new ArrayList<>();

	public final Minecraft minecraft;
	public final Font font;
	public final Window window;

	private ElementImpl hovered = null;
	private ElementImpl focused = null;

	public GUI() {
		this.screen = new GUIScreen(this);
		this.minecraft = Minecraft.getInstance();
		this.font = this.minecraft.font;
		this.window = this.minecraft.getWindow();
	}

	@Override
	public List<ElementImpl> elements() {
		return elements;
	}

	public Component title() {
		return Component.empty();
	}

	public Screen screen() {
		return screen;
	}

	public void renderBackground(GuiGraphics guiGraphics) {
		screen().renderBackground(guiGraphics);
	}

	public void renderDirtBackground(GuiGraphics guiGraphics) {
		screen().renderDirtBackground(guiGraphics);
	}

	public ElementImpl getHovered() {
		return hovered;
	}

	public void setHovered(ElementImpl hovered) {
		this.hovered = hovered;
	}

	public ElementImpl getFocused() {
		return focused;
	}

	public void setFocused(ElementImpl focused) {
		this.focused = focused;
	}

	@Override
	public void onMouseMove(double mouseX, double mouseY) {
		if (this.getHovered() == null || !this.getHovered().isAt(mouseX, mouseY))
			this.setHovered(this.getElementAt(mouseX, mouseY).orElse(null));
		PanelImpl.super.onMouseMove(mouseX, mouseY);
	}

	@Override
	public boolean onKeyPress(int keyCode, int scanCode, int modifiers) {
		return getFocused().onKeyPress(keyCode, scanCode, modifiers);
	}

	@Override
	public boolean onKeyRelease(int keyCode, int scanCode, int modifiers) {
		return getFocused().onKeyRelease(keyCode, scanCode, modifiers);
	}
}
