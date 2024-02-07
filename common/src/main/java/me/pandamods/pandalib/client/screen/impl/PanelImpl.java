package me.pandamods.pandalib.client.screen.impl;

import net.minecraft.client.gui.GuiGraphics;

import java.util.List;

public interface PanelImpl {
	void initElement();
	default void init() {
		initElement();
		elements().forEach(PanelImpl::init);
	}

	void renderElement(GuiGraphics guiGraphics, double mouseX, double mouseY, float partialTick);
	default void render(GuiGraphics guiGraphics, double mouseX, double mouseY, float partialTick) {
		renderElement(guiGraphics, mouseX, mouseY, partialTick);
		elements().forEach(element -> element.render(guiGraphics, mouseX, mouseY, partialTick));
	}

	List<ElementImpl> elements();
	default <E extends ElementImpl> E register(E element) {
		elements().add(element);
		return element;
	}
	default void clearElements() {
		elements().clear();
	}

	default void rebuild() {
		clearElements();
		init();
	}
}
