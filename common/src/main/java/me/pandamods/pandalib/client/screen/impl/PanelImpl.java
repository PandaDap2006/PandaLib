package me.pandamods.pandalib.client.screen.impl;

import me.pandamods.pandalib.utils.ScreenUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;

import java.util.List;
import java.util.Optional;

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

	default Optional<ElementImpl> getElementAt(double x, double y) {
		for (ElementImpl element : elements()) {
			if (element.isAt(x, y)) {
				Optional<ElementImpl> child = element.getElementAt(x, y);
				if (child.isPresent())
					return child;
				return Optional.of(element);
			}
		}
		return Optional.empty();
	}

	default boolean onMousePress(double mouseX, double mouseY, int button) {
		return getElementAt(mouseX, mouseY).map(element -> element.onMousePress(mouseX, mouseY, button)).orElse(false);
	}

	default boolean onMouseRelease(double mouseX, double mouseY, int button) {
		return getElementAt(mouseX, mouseY).map(element -> element.onMouseRelease(mouseX, mouseY, button)).orElse(false);
	}

	default void onMouseMove(double mouseX, double mouseY) {
		getElementAt(mouseX, mouseY).ifPresent(element -> element.onMouseMove(mouseX, mouseY));
	}

	default boolean onMouseScroll(double mouseX, double mouseY, double delta) {
		return getElementAt(mouseX, mouseY).map(element -> element.onMouseScroll(mouseX, mouseY, delta)).orElse(false);
	}

	default boolean onMouseDrag(double mouseX, double mouseY, int button, double dragX, double dragY) {
		return getElementAt(mouseX, mouseY).map(element -> element.onMouseDrag(mouseX, mouseY, button, dragX, dragY)).orElse(false);
	}

	default boolean onKeyPress(int keyCode, int scanCode, int modifiers) {
		return false;
	}

	default boolean onKeyRelease(int keyCode, int scanCode, int modifiers) {
		return false;
	}
}
