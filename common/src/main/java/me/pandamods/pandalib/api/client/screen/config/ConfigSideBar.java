package me.pandamods.pandalib.api.client.screen.config;

import me.pandamods.pandalib.api.client.screen.component.UIComponentHolder;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.layouts.FrameLayout;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.CommonComponents;

import java.awt.*;
import java.util.*;

public class ConfigSideBar extends UIComponentHolder {
	public static final int COLLAPSED_SIZE = 24;
	public static final int OPEN_SIZE = 100;

	private static final int MENU_DRAW_EDGE_COLOR = new Color(0, 0, 0, 150).getRGB();
	private static final int MENU_DRAW_EDGE_HIGHLIGHT_COLOR = new Color(100, 100, 100, 150).getRGB();

	private final ConfigMenu<?> configMenu;
	public final Set<AbstractConfigCategory> categories = new HashSet<>();

	public ConfigSideBar(ConfigMenu<?> configMenu) {
		this.configMenu = configMenu;
	}

	@Override
	protected void init() {
		GridLayout categoryGrid = new GridLayout();
		categoryGrid.defaultCellSetting().alignHorizontallyCenter();

		int i = 0;
		for (AbstractConfigCategory category : categories) {
			categoryGrid.addChild(new CategoryButton(0, 0, this.width - 2, 20, category), i++, 0);
		}

		categoryGrid.arrangeElements();
		FrameLayout.alignInRectangle(categoryGrid, 0, 0, this.getWidth(), this.getHeight() - 30, 0, 0);
		categoryGrid.visitChildren(this::addElement);

		GridLayout actionGrid = new GridLayout();
		actionGrid.spacing(4).defaultCellSetting().alignVerticallyMiddle().alignHorizontallyCenter();

		actionGrid.addChild(Button.builder(CommonComponents.GUI_DONE, button -> this.configMenu.save())
				.size(45, 20).build(), 0, 0);
		actionGrid.addChild(Button.builder(CommonComponents.GUI_CANCEL, button -> this.configMenu.onClose())
				.size(45, 20).build(), 0, 1);
		actionGrid.addChild(Button.builder(CommonComponents.GUI_CANCEL, button -> this.configMenu.reset())
				.size(45, 20).build(), 1, 0, 2, 1);

		actionGrid.arrangeElements();
		FrameLayout.alignInRectangle(actionGrid, 0, this.getHeight() - 30, this.getWidth(), 30, 0.5f, 0);
		actionGrid.visitChildren(this::addElement);
		super.init();
	}

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		guiGraphics.fill(maxX(), minY(), maxX() + 1, maxY(), MENU_DRAW_EDGE_COLOR);
		guiGraphics.fill(maxX() + 1, minY(), maxX() + 2, maxY(), MENU_DRAW_EDGE_HIGHLIGHT_COLOR);
		super.render(guiGraphics, mouseX, mouseY, partialTick);
	}

	@Override
	public int getWidth() {
		return OPEN_SIZE;
	}

	@Override
	public int getHeight() {
		return this.configMenu.height;
	}

	public class CategoryButton extends AbstractButton {
		private final AbstractConfigCategory category;

		public CategoryButton(int x, int y, int width, int height, AbstractConfigCategory category) {
			super(x, y, width, height, category.getName());
			this.category = category;
		}

		@Override
		public void onPress() {
			ConfigSideBar.this.configMenu.setCategory(category);
		}

		@Override
		protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
			this.defaultButtonNarrationText(narrationElementOutput);
		}

		@Override
		public boolean isActive() {
			return Objects.equals(ConfigSideBar.this.configMenu.getCategory(), category);
		}
	}
}